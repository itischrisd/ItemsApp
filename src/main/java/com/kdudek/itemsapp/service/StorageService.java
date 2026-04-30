package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.StorageMapper;
import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
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

    public List<StorageSummaryDTO> getAll() {
        return storageRepository.findAll().stream()
                .map(storageMapper::mapToSummaryDTO)
                .toList();
    }

    public StorageDetailsDTO getById(Long id) {
        return storageRepository.findByIdWithChildren(id)
                .map(storageMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
    }

    public StorageDetailsDTO create(StorageCreateDTO storageCreateDTO) {
        Storage storage = storageMapper.mapToStorage(storageCreateDTO);
        storageRepository.save(storage);
        return storageMapper.mapToDetailsDTO(storage);
    }

    public StorageDetailsDTO update(Long id, StorageUpdateDTO storageUpdateDTO) {
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
        storageMapper.updateStorageFromDTO(storageUpdateDTO, storage);
        storageRepository.save(storage);
        return storageMapper.mapToDetailsDTO(storage);
    }

    public void delete(Long id) {
        Storage storage = storageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
        storageRepository.delete(storage);
    }

    public void addToParent(Long parentId, Long childId) {
        Storage parent = storageRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, parentId));
        Storage child = storageRepository.findById(childId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, childId));
        child.setParent(parent);
        storageRepository.save(child);
    }

    public void removeFromParent(Long parentId, Long childId) {
        if (!storageRepository.existsById(parentId)) {
            throw new ResourceNotFoundException(Storage.class, parentId);
        }
        Storage child = storageRepository.findById_AndParent_Id(childId, parentId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, parentId, Storage.class, childId));
        child.setParent(null);
        storageRepository.save(child);
    }

    public List<StorageSummaryDTO> getChildStorages(Long id) {
        return storageRepository.findAllByParent_Id(id).stream()
                .map(storageMapper::mapToSummaryDTO)
                .toList();
    }
}
