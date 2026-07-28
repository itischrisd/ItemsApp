package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.service.StorageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<StorageSummaryDTO> getAll(
            Specification<Storage> specification,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(storageService.getAll(specification, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageDetailsDTO getById(@PathVariable Long id) {
        return storageService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StorageDetailsDTO create(
            @RequestBody @Valid StorageCreateDTO storageCreateDTO,
            HttpServletResponse response
    ) {
        StorageDetailsDTO created = storageService.create(storageCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        response.setHeader(HttpHeaders.LOCATION, location.toString());
        return created;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StorageDetailsDTO update(
            @PathVariable Long id,
            @RequestBody @Valid StorageUpdateDTO storageUpdateDTO
    ) {
        return storageService.update(id, storageUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storageService.delete(id);
    }

    @GetMapping("/{id}/storages")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<StorageSummaryDTO> getChildStorages(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(storageService.getChildStorages(id, pageable));
    }

    @PostMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToParent(
            @PathVariable Long parentId,
            @PathVariable Long childId
    ) {
        storageService.addToParent(parentId, childId);
    }

    @DeleteMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromParent(
            @PathVariable Long parentId,
            @PathVariable Long childId
    ) {
        storageService.removeFromParent(parentId, childId);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<BookSummaryDTO> getBooksByStorageId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(storageService.getBooksByStorageId(id, pageable));
    }

    @PostMapping("/{storageId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookToStorage(
            @PathVariable Long storageId,
            @PathVariable Long bookId
    ) {
        storageService.addBookToStorage(storageId, bookId);
    }

    @DeleteMapping("/{storageId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromStorage(
            @PathVariable Long storageId,
            @PathVariable Long bookId
    ) {
        storageService.removeBookFromStorage(storageId, bookId);
    }

    @GetMapping("/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ItemSummaryDTO> getItemsByStorageId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(storageService.getItemsByStorageId(id, pageable));
    }

    @PostMapping("/{storageId}/items/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItemToStorage(
            @PathVariable Long storageId,
            @PathVariable Long itemId
    ) {
        storageService.addItemToStorage(storageId, itemId);
    }

    @DeleteMapping("/{storageId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItemFromStorage(
            @PathVariable Long storageId,
            @PathVariable Long itemId
    ) {
        storageService.removeItemFromStorage(storageId, itemId);
    }
}
