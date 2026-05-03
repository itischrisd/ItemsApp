package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.config.MapStructConfig;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherDetailsDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherSummaryDTO;
import com.kdudek.itemsapp.entity.book.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface PublisherMapper {

    PublisherSummaryDTO mapToSummaryDTO(Publisher publisher);

    PublisherDetailsDTO mapToDetailsDTO(Publisher publisher);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "books", ignore = true)
    Publisher mapToPublisher(PublisherCreateDTO publisherCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updatePublisherFromDTO(PublisherUpdateDTO publisherUpdateDTO, @MappingTarget Publisher publisher);
}
