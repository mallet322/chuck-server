package io.elias.server.filter.common;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.elias.server.filter.base.StringFilter;

@Component
public class CommonBooleanBuilder {

    public void andMatchStringFilter(BooleanBuilder booleanBuilder, StringFilter stringFilter, StringPath stringPath) {
        if (stringFilter != null) {
            if (stringFilter.getEqual() != null) {
                booleanBuilder.and(stringPath.equalsIgnoreCase(stringFilter.getEqual()));
            }
            if (stringFilter.getNotEqual() != null) {
                booleanBuilder.and(stringPath.notEqualsIgnoreCase(stringFilter.getNotEqual()));
            }
            if (Boolean.TRUE.equals(stringFilter.getEmpty())) {
                booleanBuilder.and(stringPath.isEmpty().or(stringPath.isNull()));
            }
            if (Boolean.TRUE.equals(stringFilter.getNonEmpty())) {
                booleanBuilder.and(stringPath.isNotEmpty().or(stringPath.isNotNull()));
            }
            if (!CollectionUtils.isEmpty(stringFilter.getIn())) {
                booleanBuilder.and(stringPath.in(stringFilter.getIn()));
            }
            if (stringFilter.getContains() != null) {
                booleanBuilder.and(stringPath.containsIgnoreCase(stringFilter.getContains()));
            }
            if (stringFilter.getDoesntContains() != null) {
                booleanBuilder.and(stringPath.containsIgnoreCase(stringFilter.getDoesntContains()));
            }
            if (stringFilter.getStartWith() != null) {
                booleanBuilder.and(stringPath.startsWith(stringFilter.getStartWith()));
            }
            if (stringFilter.getEndWith() != null) {
                booleanBuilder.and(stringPath.endsWith(stringFilter.getEndWith()));
            }
        }
    }

}
