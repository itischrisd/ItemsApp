package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.dto.request.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.StorageResponseDTO;
import com.kdudek.itemsapp.entity.Storage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = ReferenceTranslator.class,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface StorageMapper {

    @Mapping(target = "parentStorage", source = "parent")
    StorageResponseDTO mapToDTO(Storage storage);

    List<StorageResponseDTO> mapToDTO(List<Storage> storages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "internalStorages", ignore = true)
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "idToReference")
    Storage mapToStorage(StorageCreateDTO storageCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "internalStorages", ignore = true)
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "idToReference")
    void updateStorageFromDTO(StorageUpdateDTO storageUpdateDTO, @MappingTarget Storage storage);
}
