package com.miteshpv.bizprofileservice.bizprofile.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class FieldCopier {

    private static final FieldCopier instance = new FieldCopier();

    private final Map<Map.Entry<Class<?>, Class<?>>, Map<Field, Field>> PAIRED_FIELDS = new ConcurrentHashMap<>();
    private final Map<Class<?>, Field[]> FIELDS = new ConcurrentHashMap<>();

    public static FieldCopier instance() {
        return instance;
    }

    private FieldCopier() {
    }

    public <S, T> T copyFields(S source, T target) {
        Map<Field, Field> pairedFields = getPairedFields(source, target);
        for (Field sourceField : pairedFields.keySet()) {
            Field targetField = pairedFields.get(sourceField);
            try {
                Object value = getValue(source, sourceField);
                setValue(target, targetField, value);
            } catch(Throwable t) {
                throw new RuntimeException("Failed to copy field value", t);
            }
        }
        return target;
    }

    private <S, T> Map<Field, Field> getPairedFields(S source, T target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Map.Entry<Class<?>, Class<?>> sourceToTarget = new AbstractMap.SimpleImmutableEntry<>(sourceClass, targetClass);
        PAIRED_FIELDS.computeIfAbsent(sourceToTarget, st -> mapSourceFieldsToTargetFields(sourceClass, targetClass));
        Map<Field, Field> pairedFields = PAIRED_FIELDS.get(sourceToTarget);
        return pairedFields;
    }

    private Map<Field, Field> mapSourceFieldsToTargetFields(Class<?> sourceClass, Class<?> targetClass) {
        Map<Field, Field> sourceFieldsToTargetFields = new HashMap<>();
        Field[] sourceFields = getDeclaredFields(sourceClass);
        Field[] targetFields = getDeclaredFields(targetClass);
        for (Field sourceField : sourceFields) {
            if (sourceField.getName().equals("serialVersionUID")) {
                continue;
            }
            Field targetField = findCorrespondingField(targetFields, sourceField);
            if (targetField == null) {
                log.warn("No target field found for " + sourceField.getName());
                continue;
            }
            if (Modifier.isFinal(targetField.getModifiers())) {
                log.warn("The target field " + targetField.getName() + " is final, and so cannot be written to");
                continue;
            }
            sourceFieldsToTargetFields.put(sourceField, targetField);
        }
        return Collections.unmodifiableMap(sourceFieldsToTargetFields);
    }

    private Field[] getDeclaredFields(Class<?> clazz) {
        FIELDS.computeIfAbsent(clazz, Class::getDeclaredFields);
        return FIELDS.get(clazz);
    }

    private <S> Object getValue(S source, Field sourceField) throws IllegalArgumentException, IllegalAccessException {
        sourceField.setAccessible(true);
        return sourceField.get(source);
    }

    private <T> void setValue(T target, Field targetField, Object value) throws IllegalArgumentException, IllegalAccessException {
        targetField.setAccessible(true);
        targetField.set(target, value);
    }

    private Field findCorrespondingField(Field[] targetFields, Field sourceField) {
        for (Field targetField : targetFields) {
            if (sourceField.getName().equals(targetField.getName())) {
                if (sourceField.getType().equals(targetField.getType())) {
                    return targetField;
                } else {
                    log.warn("Different types for field " +  sourceField.getName()
                            + " source " + sourceField.getType() + " and target " + targetField.getType());
                    return null;
                }
            }
        }
        return null;
    }
}