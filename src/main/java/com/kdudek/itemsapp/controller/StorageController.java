package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.StorageResponseDTO;
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

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<StorageResponseDTO> getAll() {
        return storageService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageResponseDTO getById(@PathVariable Long id) {
        return storageService.getById(id);
    }

    @PostMapping
    public ResponseEntity<StorageResponseDTO> create(@RequestBody StorageCreateDTO storageCreateDTO) {
        StorageResponseDTO storageResponseDTO = storageService.create(storageCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storageResponseDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(storageResponseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageResponseDTO update(@PathVariable Long id, @RequestBody StorageUpdateDTO storageUpdateDTO) {
        return storageService.update(id, storageUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storageService.delete(id);
    }
}
