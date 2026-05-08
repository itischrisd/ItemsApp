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
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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

    public List<StorageSummaryDTO> getAll() {
        return storageRepository.findAll().stream()
                .map(storageMapper::mapToSummaryDTO)
                .toList();
    }

    public StorageDetailsDTO getById(Long id) {
        return storageRepository.findByIdWithRelatedObjects(id)
                .map(storageMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, id));
    }

    public StorageDetailsDTO create(@Valid StorageCreateDTO storageCreateDTO) {
        Storage storage = storageMapper.mapToStorage(storageCreateDTO);
        storageRepository.save(storage);
        return storageMapper.mapToDetailsDTO(storage);
    }

    public StorageDetailsDTO update(Long id, @Valid StorageUpdateDTO storageUpdateDTO) {
        Storage storage = storageRepository.findByIdWithRelatedObjects(id)
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

    public List<BookSummaryDTO> getBooksByStorageId(Long id) {
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException(Storage.class, id);
        }
        return bookRepository.findAllByStorage_Id(id).stream()
                .map(bookMapper::mapToSummaryDTO)
                .toList();
    }

    public void addBookToStorage(Long storageId, Long bookId) {
        Storage storage = storageRepository.findByIdWithBooks(storageId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, storageId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        storage.addBook(book);
        storageRepository.save(storage);
    }

    public void removeBookFromStorage(Long storageId, Long bookId) {
        Storage storage = storageRepository.findByIdWithBooks(storageId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, storageId));
        Book book = storage.getBooks().stream()
                .filter(b -> b.getId().equals(bookId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, storageId, Book.class, bookId));
        storage.removeBook(book);
        storageRepository.save(storage);
    }

    public List<ItemSummaryDTO> getItemsByStorageId(Long id) {
        if (!storageRepository.existsById(id)) {
            throw new ResourceNotFoundException(Storage.class, id);
        }
        return itemRepository.findAllByStorage_Id(id).stream()
                .map(itemMapper::mapToSummaryDTO)
                .toList();
    }

    public void addItemToStorage(Long storageId, Long itemId) {
        Storage storage = storageRepository.findByIdWithItems(storageId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, storageId));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, itemId));
        storage.addItem(item);
        storageRepository.save(storage);
    }

    public void removeItemFromStorage(Long storageId, Long itemId) {
        Storage storage = storageRepository.findByIdWithItems(storageId)
                .orElseThrow(() -> new ResourceNotFoundException(Storage.class, storageId));
        Item item = storage.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Storage.class, storageId, Book.class, itemId));
        storage.removeItem(item);
        storageRepository.save(storage);
    }
}
