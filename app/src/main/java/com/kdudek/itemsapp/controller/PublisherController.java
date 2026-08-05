package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.PublisherApi;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.publisher.PublisherResponseDTO;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublisherController implements PublisherApi {

    private final PublisherService publisherService;

    @Override
    public PageResponse<PublisherResponseDTO> getAll(Specification<Publisher> specification, Pageable pageable) {
        return PageResponse.of(publisherService.getAll(specification, pageable));
    }

    @Override
    public PublisherResponseDTO getById(Long id) {
        return publisherService.getById(id);
    }

    @Override
    public PublisherResponseDTO create(PublisherCreateDTO publisherCreateDTO) {
        return publisherService.create(publisherCreateDTO);
    }

    @Override
    public PublisherResponseDTO update(Long id, PublisherUpdateDTO publisherUpdateDTO) {
        return publisherService.update(id, publisherUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        publisherService.delete(id);
    }

    @Override
    public PageResponse<BookSummaryDTO> getBooksByPublisherId(Long id, Pageable pageable) {
        return PageResponse.of(publisherService.getBooksByPublisherId(id, pageable));
    }

    @Override
    public void addBookToPublisher(Long publisherId, Long bookId) {
        publisherService.addBookToPublisher(publisherId, bookId);
    }

    @Override
    public void removeBookFromPublisher(Long publisherId, Long bookId) {
        publisherService.removeBookFromPublisher(publisherId, bookId);
    }
}
