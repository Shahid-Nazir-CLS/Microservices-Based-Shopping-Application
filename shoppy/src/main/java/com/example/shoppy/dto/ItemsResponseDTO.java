package com.example.shoppy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class ItemsResponseDTO {
    @JsonProperty("_embedded")
    private EmbeddedItemsDTO embeddedItems;

    @JsonProperty("_links")
    private Map<String, LinkDTO> links;

    private PageDTO page;

    public ItemsResponseDTO() {
    }

    public ItemsResponseDTO(EmbeddedItemsDTO embeddedItems, Map<String, LinkDTO> links, PageDTO page) {
        this.embeddedItems = embeddedItems;
        this.links = links;
        this.page = page;
    }

    public EmbeddedItemsDTO getEmbeddedItems() {
        return this.embeddedItems;
    }

    public void setEmbeddedItems(EmbeddedItemsDTO embeddedItems) {
        this.embeddedItems = embeddedItems;
    }

    public Map<String, LinkDTO> getLinks() {
        return this.links;
    }

    public void setLinks(Map<String, LinkDTO> links) {
        this.links = links;
    }

    public PageDTO getPage() {
        return this.page;
    }

    public void setPage(PageDTO page) {
        this.page = page;
    }

    public ItemsResponseDTO embeddedItems(EmbeddedItemsDTO embeddedItems) {
        setEmbeddedItems(embeddedItems);
        return this;
    }

    public ItemsResponseDTO links(Map<String, LinkDTO> links) {
        setLinks(links);
        return this;
    }

    public ItemsResponseDTO page(PageDTO page) {
        setPage(page);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ItemsResponseDTO)) {
            return false;
        }
        ItemsResponseDTO itemsResponseDTO = (ItemsResponseDTO) o;
        return Objects.equals(embeddedItems, itemsResponseDTO.embeddedItems)
                && Objects.equals(links, itemsResponseDTO.links) && Objects.equals(page, itemsResponseDTO.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(embeddedItems, links, page);
    }

    @Override
    public String toString() {
        return "{" +
                " embeddedItems='" + getEmbeddedItems() + "'" +
                ", links='" + getLinks() + "'" +
                ", page='" + getPage() + "'" +
                "}";
    }

}
