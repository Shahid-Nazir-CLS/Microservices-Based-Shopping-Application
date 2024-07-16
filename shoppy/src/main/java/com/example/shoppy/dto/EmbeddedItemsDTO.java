package com.example.shoppy.dto;

import java.util.List;
import java.util.Objects;

public class EmbeddedItemsDTO {
    private List<ItemDTO> items;

    public EmbeddedItemsDTO() {
    }

    public EmbeddedItemsDTO(List<ItemDTO> items) {
        this.items = items;
    }

    public List<ItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public EmbeddedItemsDTO items(List<ItemDTO> items) {
        setItems(items);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EmbeddedItemsDTO)) {
            return false;
        }
        EmbeddedItemsDTO embeddedItemsDTO = (EmbeddedItemsDTO) o;
        return Objects.equals(items, embeddedItemsDTO.items);
    }

    @Override
    public String toString() {
        return "{" +
                " items='" + getItems() + "'" +
                "}";
    }

}
