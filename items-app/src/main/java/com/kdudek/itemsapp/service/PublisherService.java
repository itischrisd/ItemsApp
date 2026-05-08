package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.mapper.PublisherMapper;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherDetailsDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherSummaryDTO;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.BookRepository;
import com.kdudek.itemsapp.repository.PublisherRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<PublisherSummaryDTO> getAll() {
        return publisherRepository.findAll().stream()
                .map(publisherMapper::mapToSummaryDTO)
                .toList();
    }

    public PublisherDetailsDTO getById(Long id) {
        return publisherRepository.findByIdWithRelatedObjects(id)
                .map(publisherMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
    }

    public PublisherDetailsDTO create(@Valid PublisherCreateDTO publisherCreateDTO) {
        Publisher publisher = publisherMapper.mapToPublisher(publisherCreateDTO);
        publisherRepository.save(publisher);
        return publisherMapper.mapToDetailsDTO(publisher);
    }

    public PublisherDetailsDTO update(Long id, @Valid PublisherUpdateDTO publisherUpdateDTO) {
        Publisher publisher = publisherRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
        publisherMapper.updatePublisherFromDTO(publisherUpdateDTO, publisher);
        publisherRepository.save(publisher);
        return publisherMapper.mapToDetailsDTO(publisher);
    }

    public void delete(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new ResourceNotFoundException(Publisher.class, id);
        }
        publisherRepository.deleteById(id);
    }

    public List<BookSummaryDTO> getBooksByPublisherId(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new ResourceNotFoundException(Publisher.class, id);
        }
        return bookRepository.findAllByPublisher_Id(id).stream()
                .map(bookMapper::mapToSummaryDTO)
                .toList();
    }

    public void addBookToPublisher(Long publisherId, Long bookId) {
        Publisher publisher = publisherRepository.findByIdWithRelatedObjects(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, publisherId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        publisher.addBook(book);
        publisherRepository.save(publisher);
    }

    public void removeBookFromPublisher(Long publisherId, Long bookId) {
        Publisher publisher = publisherRepository.findByIdWithRelatedObjects(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, publisherId));
        Book book = publisher.getBooks().stream()
                .filter(b -> b.getId().equals(bookId))
                .findAny()
                .orElseThrow(() -> new RelatedResourceNotFoundException(Publisher.class, publisherId, Book.class, bookId));
        publisher.removeBook(book);
        publisherRepository.save(publisher);
    }
}
