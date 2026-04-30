package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<StorageSummaryDTO> getAll() {
        return storageService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageDetailsDTO getById(@PathVariable Long id) {
        return storageService.getById(id);
    }

    @PostMapping
    public ResponseEntity<StorageDetailsDTO> create(@RequestBody StorageCreateDTO storageCreateDTO) {
        StorageDetailsDTO storageDetailsDTO = storageService.create(storageCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storageDetailsDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(storageDetailsDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageDetailsDTO update(@PathVariable Long id, @RequestBody StorageUpdateDTO storageUpdateDTO) {
        return storageService.update(id, storageUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storageService.delete(id);
    }

    @PostMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToParent(@PathVariable Long parentId, @PathVariable Long childId) {
        storageService.addToParent(parentId, childId);
    }

    @DeleteMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromParent(@PathVariable Long parentId, @PathVariable Long childId) {
        storageService.removeFromParent(parentId, childId);
    }

    @GetMapping("/{id}/storages")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<StorageSummaryDTO> getChildStorages(@PathVariable Long id) {
        return storageService.getChildStorages(id);
    }
}
