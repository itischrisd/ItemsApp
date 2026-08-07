package com.kdudek.itemsapp.api;

import com.kdudek.itemsapp.controller.advice.ingress.RsqlSpecificationArgumentResolver.RsqlConstants;
import com.kdudek.itemsapp.controller.annotation.IfMatch;
import com.kdudek.itemsapp.controller.annotation.IfNoneMatch;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.publisher.PublisherResponseDTO;
import com.kdudek.itemsapp.entity.book.Publisher;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Publishers")
@RequestMapping("/api/publishers")
public interface PublisherApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = RsqlConstants.QUERY_PARAM_NAME,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    PageResponse<PublisherResponseDTO> getAll(
            @Parameter(hidden = true) Specification<Publisher> specification,
            @ParameterObject Pageable pageable
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = HttpHeaders.IF_NONE_MATCH,
            in = ParameterIn.HEADER,
            schema = @Schema(type = "string")
    )
    PublisherResponseDTO getById(
            @PathVariable Long id,
            @Parameter(hidden = true) @IfNoneMatch Integer ifNoneMatch
    );

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PublisherResponseDTO create(@RequestBody PublisherCreateDTO publisherCreateDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = HttpHeaders.IF_MATCH,
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(type = "string")
    )
    PublisherResponseDTO update(
            @PathVariable Long id,
            @RequestBody PublisherUpdateDTO publisherUpdateDTO,
            @Parameter(hidden = true) @IfMatch Integer ifMatch
    );

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Parameter(
            name = HttpHeaders.IF_MATCH,
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(type = "string")
    )
    void delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @IfMatch Integer ifMatch
    );

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<BookSummaryDTO> getBooksByPublisherId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{publisherId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Parameter(
            name = HttpHeaders.IF_MATCH,
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(type = "string")
    )
    void addBookToPublisher(
            @PathVariable Long publisherId,
            @PathVariable Long bookId,
            @Parameter(hidden = true) @IfMatch Integer ifMatch
    );

    @DeleteMapping("/{publisherId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Parameter(
            name = HttpHeaders.IF_MATCH,
            in = ParameterIn.HEADER,
            required = true,
            schema = @Schema(type = "string")
    )
    void removeBookFromPublisher(
            @PathVariable Long publisherId,
            @PathVariable Long bookId,
            @Parameter(hidden = true) @IfMatch Integer ifMatch
    );
}
