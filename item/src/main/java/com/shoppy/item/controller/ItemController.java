package com.shoppy.item.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.shoppy.item.dto.ErrorDetails;
import com.shoppy.item.dto.ItemDTO;
import com.shoppy.item.dto.ItemDTOsResponse;
import com.shoppy.item.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/items")
@Tag(name = "item", description = "Methods of Item Service")
public class ItemController {
    private static final Logger logger = LogManager.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Search Items", description = "Search for items using a keyword")
    @GetMapping("/search")
    public List<ItemDTO> getSearchItems(@RequestParam("keyword") String keyword) {
        logger.info("Searching for items with keyword: {}", keyword);
        return itemService.getSearchItems(keyword);
    }

    @Operation(summary = "Get Items by IDs", description = "Retrieve items by their IDs")
    @PostMapping("/byIds")
    public ItemDTOsResponse getItemsWithIds(@RequestBody List<Integer> ids) {
        logger.info("Retrieving items with IDs: {}", ids);
        return new ItemDTOsResponse(itemService.getItemsByIds(ids));
    }

    @Operation(summary = "Get Item by ID", description = "Retrieve an item by its ID")
    @GetMapping("/{itemId}")
    public ItemDTO getItem(@PathVariable("itemId") Integer itemId) {
        logger.info("Retrieving item with ID: {}", itemId);
        return itemService.getItem(itemId);
    }

    @Operation(summary = "Update Item", description = "Update an existing item")
    @PutMapping("/")
    public ItemDTO updateItem(@RequestBody ItemDTO itemDto) {
        logger.info("Updating item with ID: {}", itemDto.getId());
        return itemService.saveItem(itemDto);
    }

    @Operation(summary = "Save Item", description = "Save a new item")
    @PostMapping("/")
    public ItemDTO saveItem(@Valid @RequestBody ItemDTO itemDto) {
        logger.info("Saving new item with name: {}", itemDto.getName());
        return itemService.saveItem(itemDto);
    }

    @Operation(summary = "Delete Item", description = "Delete an item by its ID")
    @DeleteMapping("/{itemId}")
    public String deleteItem(@PathVariable("itemId") Integer itemId) {
        logger.info("Deleting item with ID: {}", itemId);
        itemService.deleteItem(itemId);
        return "Item deleted";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
