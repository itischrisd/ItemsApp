package com.kdudek.itemsapp.dto.mapper;

import com.kdudek.itemsapp.dto.request.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.StorageResponseDTO;
import com.kdudek.itemsapp.entity.Storage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StorageMapper {

    @Mapping(source = "parent.id", target = "parentId")
    StorageResponseDTO mapToDTO(Storage storage);

    List<StorageResponseDTO> mapToDTO(List<Storage> storages);

    Storage mapToStorage(StorageCreateDTO storageCreateDTO);

    void updateStorageFromDTO(StorageUpdateDTO storageUpdateDTO, @MappingTarget Storage storage);
}
