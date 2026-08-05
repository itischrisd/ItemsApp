package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.ItemApi;
import com.kdudek.itemsapp.dto.request.item.ItemCreateDTO;
import com.kdudek.itemsapp.dto.request.item.ItemUpdateDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemDetailsDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.service.ItemService;
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
public class ItemController implements ItemApi {

    private final ItemService itemService;

    @Override
    public PageResponse<ItemSummaryDTO> getAll(Specification<Item> specification, Pageable pageable) {
        return PageResponse.of(itemService.getAll(specification, pageable));
    }

    @Override
    public ItemDetailsDTO getById(Long id) {
        return itemService.getById(id);
    }

    @Override
    public ItemDetailsDTO create(ItemCreateDTO itemCreateDTO, HttpServletResponse response) {
        ItemDetailsDTO created = itemService.create(itemCreateDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        response.setHeader(HttpHeaders.LOCATION, location.toString());
        return created;
    }

    @Override
    public ItemDetailsDTO update(Long id, ItemUpdateDTO itemUpdateDTO) {
        return itemService.update(id, itemUpdateDTO);
    }

    @Override
    public void delete(Long id) {
        itemService.delete(id);
    }
}
