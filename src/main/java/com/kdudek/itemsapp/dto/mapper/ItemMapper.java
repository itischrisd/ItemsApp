package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.dto.request.item.ItemCreateDTO;
import com.kdudek.itemsapp.dto.request.item.ItemUpdateDTO;
import com.kdudek.itemsapp.dto.response.item.ItemDetailsDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = ReferenceTranslator.class,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface ItemMapper {

    ItemSummaryDTO mapToSummaryDTO(Item item);

    ItemDetailsDTO mapToDetailsDTO(Item item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "categories", source = "categoriesIds", qualifiedByName = "idToReference")
    @Mapping(target = "storage", source = "storageId", qualifiedByName = "idToReference")
    Item mapToItem(ItemCreateDTO itemCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "storage", ignore = true)
    void updateItemFromDTO(ItemUpdateDTO itemUpdateDTO, @MappingTarget Item item);
}
