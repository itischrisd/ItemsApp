package com.kdudek.itemsapp.api;

import com.kdudek.itemsapp.controller.advice.RsqlSpecificationArgumentResolver.RsqlConstants;
import com.kdudek.itemsapp.dto.request.storage.StorageCreateDTO;
import com.kdudek.itemsapp.dto.request.storage.StorageUpdateDTO;
import com.kdudek.itemsapp.dto.response.book.BookSummaryDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageDetailsDTO;
import com.kdudek.itemsapp.dto.response.storage.StorageSummaryDTO;
import com.kdudek.itemsapp.entity.Storage;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Storages")
@RequestMapping("/api/storages")
public interface StorageApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Parameter(
            name = RsqlConstants.QUERY_PARAM_NAME,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "string")
    )
    PageResponse<StorageSummaryDTO> getAll(
            @Parameter(hidden = true) Specification<Storage> specification,
            @ParameterObject Pageable pageable
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    StorageDetailsDTO getById(@PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    StorageDetailsDTO create(@RequestBody StorageCreateDTO storageCreateDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    StorageDetailsDTO update(
            @PathVariable Long id,
            @RequestBody StorageUpdateDTO storageUpdateDTO
    );

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping("/{id}/storages")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<StorageSummaryDTO> getChildStorages(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.OK)
    void addToParent(
            @PathVariable Long parentId,
            @PathVariable Long childId
    );

    @DeleteMapping("/{parentId}/storages/{childId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeFromParent(
            @PathVariable Long parentId,
            @PathVariable Long childId
    );

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<BookSummaryDTO> getBooksByStorageId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{storageId}/books/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    void addBookToStorage(
            @PathVariable Long storageId,
            @PathVariable Long bookId
    );

    @DeleteMapping("/{storageId}/books/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeBookFromStorage(
            @PathVariable Long storageId,
            @PathVariable Long bookId
    );

    @GetMapping("/{id}/items")
    @ResponseStatus(HttpStatus.OK)
    PageResponse<ItemSummaryDTO> getItemsByStorageId(
            @PathVariable Long id,
            @ParameterObject Pageable pageable
    );

    @PostMapping("/{storageId}/items/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    void addItemToStorage(
            @PathVariable Long storageId,
            @PathVariable Long itemId
    );

    @DeleteMapping("/{storageId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeItemFromStorage(
            @PathVariable Long storageId,
            @PathVariable Long itemId
    );
}
