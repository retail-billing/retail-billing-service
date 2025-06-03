package com.ashish.retailbillingservice.controller;

import com.ashish.retailbillingservice.dto.item.ItemRequest;
import com.ashish.retailbillingservice.dto.item.ItemResponse;
import com.ashish.retailbillingservice.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ObjectMapper objectMapper;

    @PostMapping("/admin/items")
    public ResponseEntity<ItemResponse> createItem(
            @RequestPart String itemRequestJson,
            @RequestPart MultipartFile file
    ) {
        try {
            ItemRequest itemRequest = objectMapper.readValue(itemRequestJson, ItemRequest.class);
            itemRequest.validate();
            ItemResponse response = itemService.createItem(itemRequest, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> fetchAllItems() {
        List<ItemResponse> items = itemService.fetchAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemResponse> fetchItem(@PathVariable String itemId) {
        ItemResponse item = itemService.fetchItem(itemId);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/admin/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
