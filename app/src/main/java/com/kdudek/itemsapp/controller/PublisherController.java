package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.publisher.PublisherResponseDTO;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<PublisherResponseDTO> getAll(
            Specification<Publisher> specification,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(publisherService.getAll(specification, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherResponseDTO getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherResponseDTO create(@RequestBody @Valid PublisherCreateDTO publisherCreateDTO) {
        return publisherService.create(publisherCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid PublisherUpdateDTO publisherUpdateDTO
    ) {
        return publisherService.update(id, publisherUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<BookSummaryDTO> getBooksByPublisherId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    ) {
        return PageResponse.of(publisherService.getBooksByPublisherId(id, pageable));
    }

    @PostMapping("/{publisherId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookToPublisher(
            @PathVariable Long publisherId,
            @PathVariable Long bookId
    ) {
        publisherService.addBookToPublisher(publisherId, bookId);
    }

    @DeleteMapping("/{publisherId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookFromPublisher(
            @PathVariable Long publisherId,
            @PathVariable Long bookId
    ) {
        publisherService.removeBookFromPublisher(publisherId, bookId);
    }
}
