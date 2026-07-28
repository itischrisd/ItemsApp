package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.mapper.ItemMapper;
import com.kdudek.itemsapp.dto.mapper.StorageMapper;
import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.entity.Storage;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.BookRepository;
import com.kdudek.itemsapp.repository.ItemRepository;
import com.kdudek.itemsapp.repository.StorageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageMapper storageMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Page<StorageSummaryDTO> getAll(Specification<Storage> specification, Pageable pageable) {
        return storageRepository.findAll(specification, pageable)
                .map(storageMapper::mapToSummaryDTO);
    }

    public StorageDetailsDTO getById(Long id) {
        return storageRepository.findByIdWithParent(id)
                .map(storageMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
    }

    public StorageDetailsDTO create(@Valid StorageCreateDTO storageCreateDTO) {
        Storage storage = storageMapper.mapToStorage(storageCreateDTO);
        storageRepository.save(storage);
        return storageMapper.mapToDetailsDTO(storage);
    }

    public StorageDetailsDTO update(Long id, @Valid StorageUpdateDTO storageUpdateDTO) {
        Storage storage = storageRepository.findByIdWithParent(id)
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

    public Page<StorageSummaryDTO> getChildStorages(Long id, Pageable pageable) {
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException(Storage.class, id);
        }
        return storageRepository.findAllByParent_Id(id, pageable)
                .map(storageMapper::mapToSummaryDTO);
    }

    public void addToParent(Long parentId, Long childId) {
        if (!storageRepository.existsById(parentId)) {
            throw new ResourceNotFoundException(Storage.class, parentId);
        }
        Storage child = storageRepository.findById(childId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, childId));
        Storage parentProxy = storageRepository.getReferenceById(parentId);
        child.setParent(parentProxy);
        storageRepository.save(child);
    }

    public void removeFromParent(Long parentId, Long childId) {
        if (!storageRepository.existsById(parentId)) {
            throw new ResourceNotFoundException(Storage.class, parentId);
        }
        Storage child = storageRepository.findById(childId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, parentId, Storage.class, childId));
        if (!parentId.equals(child.getParent().getId())) {
            throw new RelatedResourceNotFoundException(Storage.class, parentId, Storage.class, childId);
        }
        child.setParent(null);
        storageRepository.save(child);
    }

    public Page<BookSummaryDTO> getBooksByStorageId(Long id, Pageable pageable) {
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException(Storage.class, id);
        }
        return bookRepository.findAllByStorage_Id(id, pageable)
                .map(bookMapper::mapToSummaryDTO);
    }

    public void addBookToStorage(Long storageId, Long bookId) {
        if (!storageRepository.existsById(storageId)) {
            throw new ResourceNotFoundException(Storage.class, storageId);
        }
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        Storage storageProxy = storageRepository.getReferenceById(storageId);
        book.setStorage(storageProxy);
        bookRepository.save(book);
    }

    public void removeBookFromStorage(Long storageId, Long bookId) {
        if (!storageRepository.existsById(storageId)) {
            throw new ResourceNotFoundException(Storage.class, storageId);
        }
        Book book = bookRepository.findByIdWithStorage(bookId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, storageId, Book.class, bookId));
        if (!storageId.equals(book.getStorage().getId())) {
            throw new RelatedResourceNotFoundException(Storage.class, storageId, Book.class, bookId);
        }
        book.setStorage(null);
        bookRepository.save(book);
    }

    public Page<ItemSummaryDTO> getItemsByStorageId(Long id, Pageable pageable) {
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException(Storage.class, id);
        }
        return itemRepository.findAllByStorage_Id(id, pageable)
                .map(itemMapper::mapToSummaryDTO);
    }

    public void addItemToStorage(Long storageId, Long itemId) {
        if (!storageRepository.existsById(storageId)) {
            throw new ResourceNotFoundException(Storage.class, storageId);
        }
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, itemId));
        Storage storageProxy = storageRepository.getReferenceById(storageId);
        item.setStorage(storageProxy);
        itemRepository.save(item);
    }

    public void removeItemFromStorage(Long storageId, Long itemId) {
        if (!storageRepository.existsById(storageId)) {
            throw new ResourceNotFoundException(Storage.class, storageId);
        }
        Item item = itemRepository.findByIdWithStorage(itemId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, storageId, Item.class, itemId));
        if (!storageId.equals(item.getStorage().getId())) {
            throw new RelatedResourceNotFoundException(Storage.class, storageId, Item.class, itemId);
        }
        item.setStorage(null);
        itemRepository.save(item);
    }
}
