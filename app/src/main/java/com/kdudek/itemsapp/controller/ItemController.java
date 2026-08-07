package com.kdudek.itemsapp.controller;

import com.kdudek.itemsapp.api.ItemApi;
import com.kdudek.itemsapp.dto.request.item.ItemCreateDTO;
import com.kdudek.itemsapp.dto.request.item.ItemUpdateDTO;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.item.ItemDetailsDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController implements ItemApi {

    private final ItemService itemService;

    @Override
    public PageResponse<ItemSummaryDTO> getAll(Specification<Item> specification, Pageable pageable) {
        return PageResponse.of(itemService.getAll(specification, pageable));
    }

    @Override
    public ItemDetailsDTO getById(Long id, Integer ifNoneMatch) {
        return itemService.getById(id, ifNoneMatch);
    }

    @Override
    public ItemDetailsDTO create(ItemCreateDTO itemCreateDTO) {
        return itemService.create(itemCreateDTO);
    }

    @Override
    public ItemDetailsDTO update(Long id, ItemUpdateDTO itemUpdateDTO, Integer ifMatch) {
        return itemService.update(id, itemUpdateDTO, ifMatch);
    }

    @Override
    public void delete(Long id, Integer ifMatch) {
        itemService.delete(id, ifMatch);
    }
}
