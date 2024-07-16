package com.shoppy.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoppy.item.dto.ItemDTO;
import com.shoppy.item.model.Item;
import com.shoppy.item.repository.ItemDAO;

@Service
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private ModelMapper modelMapper;

    // getItem
    public ItemDTO getItem(long itemId) {
        // convert Item returned by itemDAO to itemDTO object using modelmapper
        return modelMapper.map(itemDAO.getReferenceById(itemId), ItemDTO.class);
    }

    // save
    public ItemDTO saveItem(ItemDTO itemDto) {
        // first convert dto to item and save and get item back
        Item item = itemDAO.save(modelMapper.map(itemDto, Item.class));

        // then convert item to dto and return
        return modelMapper.map(item, ItemDTO.class);
    }

    // getAll
    public Page<ItemDTO> getItems(Pageable pageable) {
        Page<Item> itemsPage = itemDAO.findAll(pageable);
        return itemsPage.map(item -> modelMapper.map(item, ItemDTO.class));
    }

    // getSearchItems
    public List<ItemDTO> getSearchItems(String searchKeyword) {
        return itemDAO.findByNameContaining(searchKeyword).stream().map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    // getItemsByIdS
    public List<ItemDTO> getItemsByIds(List<Integer> ids) {
        return itemDAO.findByIds(ids).stream().map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());
    }

    // delete
    public void deleteItem(long itemId) {
        itemDAO.deleteById(itemId);
    }
}
