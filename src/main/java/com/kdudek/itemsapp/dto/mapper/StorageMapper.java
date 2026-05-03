package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.config.MapStructConfig;
import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.entity.Storage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = MapStructConfig.class,
        uses = ReferenceTranslator.class
)
public interface StorageMapper {

    StorageSummaryDTO mapToSummaryDTO(Storage storage);

    @Mapping(target = "parentStorage", source = "parent")
    StorageDetailsDTO mapToDetailsDTO(Storage storage);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "internalStorages", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "idToReference")
    Storage mapToStorage(StorageCreateDTO storageCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "internalStorages", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateStorageFromDTO(StorageUpdateDTO storageUpdateDTO, @MappingTarget Storage storage);
}
