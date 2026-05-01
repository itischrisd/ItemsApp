package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.PublisherMapper;
import com.kdudek.itemsapp.dto.request.publisher.PublisherCreateDTO;
import com.kdudek.itemsapp.dto.request.publisher.PublisherUpdateDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherDetailsDTO;
import com.kdudek.itemsapp.dto.response.publisher.PublisherSummaryDTO;
import com.kdudek.itemsapp.entity.book.Publisher;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public List<PublisherSummaryDTO> getAll() {
        return publisherRepository.findAll().stream()
                .map(publisherMapper::mapToSummaryDTO)
                .toList();
    }

    public PublisherDetailsDTO getById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Publisher.class, id));
    }

    public PublisherDetailsDTO create(PublisherCreateDTO publisherCreateDTO) {
        Publisher publisher = publisherMapper.mapToPublisher(publisherCreateDTO);
        publisherRepository.save(publisher);
        return publisherMapper.mapToDetailsDTO(publisher);
    }

    public PublisherDetailsDTO update(Long id, PublisherUpdateDTO publisherUpdateDTO) {
        Publisher publisher = publisherRepository.findById(id)
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
}
