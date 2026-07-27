package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.config.MapStructConfig;
import org.mapstruct.Mapper;

import java.time.Year;

@Mapper(config = MapStructConfig.class)
public interface YearMapper {

    default Year mapToYear(Integer year) {
        if (year == null) {
            return null;
        }
        return Year.of(year);
    }

    default Integer mapToInteger(Year year) {
        if (year == null) {
            return null;
        }
        return year.getValue();
    }
}
