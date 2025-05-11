package com.tamimSoft.store.service;

import com.tamimSoft.store.dto.TagDto;
import com.tamimSoft.store.entity.Tag;
import com.tamimSoft.store.exception.ResourceNotFoundException;
import com.tamimSoft.store.repository.ProductRepository;
import com.tamimSoft.store.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor // Automatically injects TagRepository and ProductRepository via constructor
public class TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    public void createTag(TagDto tagDto) {

        // Map basic fields using ModelMapper
        Tag tag = modelMapper.map(tagDto, Tag.class);
        tagRepository.save(tag);
    }

    // Get all active tags associated with products, paginated
    public Page<TagDto> getAllActiveTagsDto(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);

        // Filter tags that are associated with products
        List<TagDto> filteredTags = tagPage.stream().filter(tag -> productRepository.findByTagsContaining(tag).isPresent()).map(tag -> modelMapper.map(tag, TagDto.class)).toList();

        return new PageImpl<>(filteredTags, pageable, filteredTags.size());
    }

    public Page<TagDto> getAllTagsDto(Pageable pageable) {
        return tagRepository.findAll(pageable).map(tag -> modelMapper.map(tag, TagDto.class));
    }

    public TagDto getTagDtoById(String id) {
        Tag tag = getTagById(id);
        return modelMapper.map(tag, TagDto.class);
    }

    public Tag getTagById(String id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
    }

    public void updateTag(String id, TagDto tagDTO) {
        Tag tagToUpdate = getTagById(id);
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        tagRepository.save(tag);
    }

    public void deleteById(String id) {
        getTagById(id);
        tagRepository.deleteById(id);
    }

    public Set<Tag> getTagsByIds(Set<String> tagIds) {
        return new HashSet<>(tagRepository.findAllById(tagIds));
    }
}
