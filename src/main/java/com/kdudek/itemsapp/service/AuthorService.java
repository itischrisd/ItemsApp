package com.kdudek.itemsapp.service;

import com.kdudek.itemsapp.dto.mapper.AuthorMapper;
import com.kdudek.itemsapp.dto.request.author.AuthorCreateDTO;
import com.kdudek.itemsapp.dto.request.author.AuthorUpdateDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorDetailsDTO;
import com.kdudek.itemsapp.dto.response.author.AuthorSummaryDTO;
import com.kdudek.itemsapp.entity.book.Author;
import com.kdudek.itemsapp.exception.ResourceNotFoundException;
import com.kdudek.itemsapp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorSummaryDTO> getAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::mapToSummaryDTO)
                .toList();
    }

    public AuthorDetailsDTO getById(Long id) {
        return authorRepository.findByIdWithRelatedObjects(id)
                .map(authorMapper::mapToDetailsDTO)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
    }

    public AuthorDetailsDTO create(AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.mapToAuthor(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.mapToDetailsDTO(author);
    }

    public AuthorDetailsDTO update(Long id, AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorRepository.findByIdWithRelatedObjects(id)
                .orElseThrow(() -> new ResourceNotFoundException(Author.class, id));
        authorMapper.updateAuthorFromDTO(authorUpdateDTO, author);
        authorRepository.save(author);
        return authorMapper.mapToDetailsDTO(author);
    }

    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException(Author.class, id);
        }
        authorRepository.deleteById(id);
    }
}
