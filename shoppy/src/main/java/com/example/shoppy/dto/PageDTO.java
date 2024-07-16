package com.example.shoppy.dto;

import java.util.Objects;

public class PageDTO {
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;

    public PageDTO() {
    }

    public PageDTO(int size, long totalElements, int totalPages, int number) {
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.number = number;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PageDTO size(int size) {
        setSize(size);
        return this;
    }

    public PageDTO totalElements(long totalElements) {
        setTotalElements(totalElements);
        return this;
    }

    public PageDTO totalPages(int totalPages) {
        setTotalPages(totalPages);
        return this;
    }

    public PageDTO number(int number) {
        setNumber(number);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PageDTO)) {
            return false;
        }
        PageDTO pageDTO = (PageDTO) o;
        return size == pageDTO.size && totalElements == pageDTO.totalElements && totalPages == pageDTO.totalPages
                && number == pageDTO.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, totalElements, totalPages, number);
    }

    @Override
    public String toString() {
        return "{" +
                " size='" + getSize() + "'" +
                ", totalElements='" + getTotalElements() + "'" +
                ", totalPages='" + getTotalPages() + "'" +
                ", number='" + getNumber() + "'" +
                "}";
    }

}
