package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.ItemMapper;
import com.kdudek.itemsapp.dto.request.item.ItemCreateDTO;
import com.kdudek.itemsapp.dto.request.item.ItemUpdateDTO;
import com.kdudek.itemsapp.dto.response.item.ItemDetailsDTO;
import com.kdudek.itemsapp.dto.response.item.ItemSummaryDTO;
import com.kdudek.itemsapp.entity.Item;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemSummaryDTO> getAll() {
        return itemRepository.findAll().stream()
                .map(itemMapper::mapToSummaryDTO)
                .toList();
    }

    public ItemDetailsDTO getById(Long id) {
        return itemRepository.findByIdWithRelatedObjects(id)
                .map(itemMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, id));
    }

    public ItemDetailsDTO create(@Valid ItemCreateDTO itemCreateDTO) {
        Item item = itemMapper.mapToItem(itemCreateDTO);
        itemRepository.save(item);
        return itemMapper.mapToDetailsDTO(item);
    }

    public ItemDetailsDTO update(Long id, @Valid ItemUpdateDTO itemUpdateDTO) {
        Item item = itemRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Item.class, id));
        itemMapper.updateItemFromDTO(itemUpdateDTO, item);
        itemRepository.save(item);
        return itemMapper.mapToDetailsDTO(item);
    }

    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException(Item.class, id);
        }
        itemRepository.deleteById(id);
    }
}
