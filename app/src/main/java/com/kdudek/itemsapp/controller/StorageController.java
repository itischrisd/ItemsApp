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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StorageController implements StorageApi {

    private final StorageService storageService;

    @Override
    public PageResponse<StorageSummaryDTO> getAll(Specification<Storage> specification, Pageable pageable) {
        return PageResponse.of(storageService.getAll(specification, pageable));
    }

    @Override
    public StorageDetailsDTO getById(Long id, Integer ifNoneMatch) {
        return storageService.getById(id, ifNoneMatch);
    }

    @Override
    public StorageDetailsDTO create(StorageCreateDTO storageCreateDTO) {
        return storageService.create(storageCreateDTO);
    }

    @Override
    public StorageDetailsDTO update(Long id, StorageUpdateDTO storageUpdateDTO, Integer ifMatch) {
        return storageService.update(id, storageUpdateDTO, ifMatch);
    }

    @Override
    public void delete(Long id, Integer ifMatch) {
        storageService.delete(id, ifMatch);
    }

    @Override
    public PageResponse<StorageSummaryDTO> getChildStorages(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getChildStorages(id, pageable));
    }

    @Override
    public void addToParent(Long parentId, Long childId, Integer ifMatch) {
        storageService.addToParent(parentId, childId, ifMatch);
    }

    @Override
    public void removeFromParent(Long parentId, Long childId, Integer ifMatch) {
        storageService.removeFromParent(parentId, childId, ifMatch);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByStorageId(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getBooksByStorageId(id, pageable));
    }

    @Override
    public void addBookToStorage(Long storageId, Long bookId, Integer ifMatch) {
        storageService.addBookToStorage(storageId, bookId, ifMatch);
    }

    @Override
    public void removeBookFromStorage(Long storageId, Long bookId, Integer ifMatch) {
        storageService.removeBookFromStorage(storageId, bookId, ifMatch);
    }

    @Override
    public PageResponse<ItemSummaryDTO> getItemsByStorageId(Long id, Pageable pageable) {
        return PageResponse.of(storageService.getItemsByStorageId(id, pageable));
    }

    @Override
    public void addItemToStorage(Long storageId, Long itemId, Integer ifMatch) {
        storageService.addItemToStorage(storageId, itemId, ifMatch);
    }

    @Override
    public void removeItemFromStorage(Long storageId, Long itemId, Integer ifMatch) {
        storageService.removeItemFromStorage(storageId, itemId, ifMatch);
    }
}
