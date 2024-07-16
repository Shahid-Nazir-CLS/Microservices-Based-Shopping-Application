package com.example.shoppy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shoppy.dto.ItemDTO;
import com.example.shoppy.dto.ItemsResponseDTO;
import com.example.shoppy.service.ItemService;

@Controller
public class HomeController {
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String getHome(@RequestParam(name = "page", defaultValue = "0") String pageNumber, Model model) {
        logger.info("Fetching home page items for page number: " + pageNumber);

        ItemsResponseDTO response = itemService.getItems(pageNumber);
        model.addAttribute("items", response.getEmbeddedItems().getItems());
        model.addAttribute("totalPages", response.getPage().getTotalPages());
        model.addAttribute("currPageNumber", response.getPage().getNumber());
        model.addAttribute("linksProvided", true);

        logger.info("Home page items retrieved successfully for page number: " + pageNumber);
        return "home";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam("keyword") String keyword, Model model) {
        logger.info("Searching items with keyword: " + keyword);

        List<ItemDTO> items = itemService.searchItems(keyword);
        model.addAttribute("items", items);
        model.addAttribute("keyword", keyword);
        model.addAttribute("linksProvided", false);

        logger.info("Items searched successfully for keyword: " + keyword);
        return "home";
    }

    @GetMapping("/itemDetails/{itemId}")
    public String getItemDetails(@PathVariable("itemId") String itemId, Model model) {
        logger.info("Fetching details for item ID: " + itemId);

        ItemDTO itemDto = itemService.getItemDetails(itemId);
        model.addAttribute("item", itemDto);

        logger.info("Item details retrieved successfully for item ID: " + itemId);
        return "itemDetails";
    }
}
