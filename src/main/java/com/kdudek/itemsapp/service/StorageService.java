package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.StorageMapper;
import com.kdudek.itemsapp.dto.request.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.StorageResponseDTO;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageMapper storageMapper;

    public List<StorageResponseDTO> getAll() {
        List<Storage> storages = storageRepository.findAll();
        return storageMapper.mapToDTO(storages);
    }

    public StorageResponseDTO getById(Long id) {
        return storageRepository.findById(id)
                .map(storageMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
    }

    public StorageResponseDTO create(StorageCreateDTO storageCreateDTO) {
        Storage storage = storageMapper.mapToStorage(storageCreateDTO);
        storageRepository.save(storage);
        return storageMapper.mapToDTO(storage);
    }

    public StorageResponseDTO update(Long id, StorageUpdateDTO storageUpdateDTO) {
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
        storageMapper.updateStorageFromDTO(storageUpdateDTO, storage);
        storageRepository.save(storage);
        return storageMapper.mapToDTO(storage);
    }

    public void delete(Long id) {
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
        storageRepository.delete(storage);
    }
}
