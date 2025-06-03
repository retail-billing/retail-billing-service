package com.ashish.retailbillingservice.service;

import com.ashish.retailbillingservice.dto.item.ItemRequest;
import com.ashish.retailbillingservice.dto.item.ItemResponse;
import com.ashish.retailbillingservice.entity.CategoryEntity;
import com.ashish.retailbillingservice.entity.ItemEntity;
import com.ashish.retailbillingservice.exception.NotFoundException;
import com.ashish.retailbillingservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileUploadService fileUploadService;
    private final CategoryService categoryService;

    public ItemResponse createItem(ItemRequest itemRequest, MultipartFile file) {
        String imageUrl = fileUploadService.uploadFile(file);
        CategoryEntity categoryEntity = categoryService.fetchCategoryById(itemRequest.getCategoryId());
        ItemEntity itemEntity = ItemEntity.from(itemRequest, imageUrl, categoryEntity);
        return ItemResponse.from(itemRepository.save(itemEntity));
    }

    public void deleteItem(String itemId) {
        itemRepository.findByItemId(itemId).ifPresentOrElse(itemEntity -> {
                    itemRepository.delete(itemEntity);
                    fileUploadService.deleteFile(itemEntity.getImageUrl());
                }, () -> {
                    throw new NotFoundException("Item not found with id: " + itemId);
                });
    }

    public ItemResponse fetchItem(String itemId) {
        ItemEntity itemEntity = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + itemId));
        return ItemResponse.from(itemEntity);
    }

    public List<ItemResponse> fetchAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(ItemResponse::from)
                .toList();
    }
}
