package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.BookMapper;
import com.kdudek.itemsapp.dto.mapper.PublisherMapper;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherResponseDTO;
import com.kdudek.itemsapp.entity.book.Book;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.exception.PreconditionFailedException;
import com.kdudek.itemsapp.exception.RelatedResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.exception.ResourceNotModifiedException;
import com.kdudek.itemsapp.repository.BookRepository;
import com.kdudek.itemsapp.repository.PublisherRepository;
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
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Page<PublisherResponseDTO> getAll(Specification<Publisher> specification, Pageable pageable) {
        return publisherRepository.findAll(specification, pageable)
                .map(publisherMapper::mapToDTO);
    }

    public PublisherResponseDTO getById(Long id, Integer clientVersion) {
        if (clientVersion != null && publisherRepository.existsByIdAndVersion(id, clientVersion)) {
            throw new ResourceNotModifiedException();
        }
        return publisherRepository.findById(id)
                .map(publisherMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
    }

    public PublisherResponseDTO create(@Valid PublisherCreateDTO publisherCreateDTO) {
        Publisher publisher = publisherMapper.mapToPublisher(publisherCreateDTO);
        publisherRepository.save(publisher);
        return publisherMapper.mapToDTO(publisher);
    }

    public PublisherResponseDTO update(Long id, @Valid PublisherUpdateDTO publisherUpdateDTO, Integer clientVersion) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
        if (!publisher.getVersion().equals(clientVersion)) {
            throw new PreconditionFailedException();
        }
        publisherMapper.updatePublisherFromDTO(publisherUpdateDTO, publisher);
        publisherRepository.save(publisher);
        return publisherMapper.mapToDTO(publisher);
    }

    public void delete(Long id, Integer clientVersion) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
        if (!publisher.getVersion().equals(clientVersion)) {
            throw new PreconditionFailedException();
        }
        publisherRepository.delete(publisher);
    }

    public Page<BookSummaryDTO> getBooksByPublisherId(Long id, Pageable pageable) {
        if (!publisherRepository.existsById(id)) {
            throw new ResourceNotFoundException(Publisher.class, id);
        }
        return bookRepository.findAllByPublisher_Id(id, pageable)
                .map(bookMapper::mapToSummaryDTO);
    }

    public void addBookToPublisher(Long publisherId, Long bookId, Integer clientBookVersion) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new ResourceNotFoundException(Publisher.class, publisherId);
        }
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(Book.class, bookId));
        if (!book.getVersion().equals(clientBookVersion)) {
            throw new PreconditionFailedException();
        }
        Publisher publisherProxy = publisherRepository.getReferenceById(publisherId);
        book.setPublisher(publisherProxy);
        bookRepository.save(book);
    }

    public void removeBookFromPublisher(Long publisherId, Long bookId, Integer clientBookVersion) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new ResourceNotFoundException(Publisher.class, publisherId);
        }
        Book book = bookRepository.findByIdWithPublisher(bookId)
                .orElseThrow(() -> new RelatedResourceNotFoundException(Publisher.class, publisherId, Book.class, bookId));
        if (!publisherId.equals(book.getPublisher().getId())) {
            throw new RelatedResourceNotFoundException(Publisher.class, publisherId, Book.class, bookId);
        }
        if (!book.getVersion().equals(clientBookVersion)) {
            throw new PreconditionFailedException();
        }
        book.setPublisher(null);
        bookRepository.save(book);
    }
}
