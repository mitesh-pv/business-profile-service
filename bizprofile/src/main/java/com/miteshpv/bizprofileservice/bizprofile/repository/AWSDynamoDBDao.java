package com.miteshpv.bizprofileservice.bizprofile.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Repository
public class AWSDynamoDBDao <T> {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public T save(T entity) {
        Optional<Field> idField = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DynamoDBHashKey.class)).findFirst();
        if(!idField.isPresent()) {
            throw new AmazonDynamoDBException("No Partition Key annotation found in {}" + entity.getClass().getName());
        }
        String idFieldName = idField.get().getName();
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        saveExpression.setExpected(Collections.singletonMap(idFieldName, new ExpectedAttributeValue().withExists(false)));
        dynamoDBMapper.save(entity, saveExpression);
        return entity;
    }

    public void saveOrUpdate(T entity) {
        dynamoDBMapper.save(entity);
    }

    public T findById(T partitionKeyValue, Class<T> clazz) {
        return dynamoDBMapper.load(clazz, partitionKeyValue);
    }

    public List<T> findAllByAttribute(String fieldName, List<T> fieldValues, Class<T> clazz) {
        final List<AttributeValue> attList = fieldValues.stream()
                .map(t -> new AttributeValue(t.toString())).collect(Collectors.toList());
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterConditionEntry(fieldName, new Condition()
                .withComparisonOperator(ComparisonOperator.IN)
                .withAttributeValueList(attList));
        return dynamoDBMapper.scan(clazz, dynamoDBScanExpression);
    }


}
