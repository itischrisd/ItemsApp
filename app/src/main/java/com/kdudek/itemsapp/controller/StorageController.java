package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.StorageApi;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class StorageController implements StorageApi {

    private final StorageService storageService;

    @Override
    public PageResponse<StorageSummaryDTO> getAll(Specification<Storage> specification, Pageable pageable) {
        return PageResponse.of(storageService.getAll(specification, pageable));
    }

    @Override
    public StorageDetailsDTO getById(Long id) {
        return storageService.getById(id);
    }

    @Override
    public StorageDetailsDTO create(StorageCreateDTO storageCreateDTO) {
        return storageService.create(storageCreateDTO);
    }

    @Override
    public StorageDetailsDTO update(Long id, StorageUpdateDTO storageUpdateDTO) {
        return storageService.update(id, storageUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        storageService.delete(id);
    }

    @Override
    public PageResponse<StorageSummaryDTO> getChildStorages(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getChildStorages(id, pageable));
    }

    @Override
    public void addToParent(Long parentId, Long childId) {
        storageService.addToParent(parentId, childId);
    }

    @Override
    public void removeFromParent(Long parentId, Long childId) {
        storageService.removeFromParent(parentId, childId);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByStorageId(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getBooksByStorageId(id, pageable));
    }

    @Override
    public void addBookToStorage(Long storageId, Long bookId) {
        storageService.addBookToStorage(storageId, bookId);
    }

    @Override
    public void removeBookFromStorage(Long storageId, Long bookId) {
        storageService.removeBookFromStorage(storageId, bookId);
    }

    @Override
    public PageResponse<ItemSummaryDTO> getItemsByStorageId(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getItemsByStorageId(id, pageable));
    }

    @Override
    public void addItemToStorage(Long storageId, Long itemId) {
        storageService.addItemToStorage(storageId, itemId);
    }

    @Override
    public void removeItemFromStorage(Long storageId, Long itemId) {
        storageService.removeItemFromStorage(storageId, itemId);
    }
}
