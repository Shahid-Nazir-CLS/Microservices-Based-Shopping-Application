package com.example.shoppy.dto;

import java.util.Objects;

public class LinkDTO {
    private String href;

    public LinkDTO() {
    }

    public LinkDTO(String href) {
        this.href = href;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public LinkDTO href(String href) {
        setHref(href);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LinkDTO)) {
            return false;
        }
        LinkDTO linkDTO = (LinkDTO) o;
        return Objects.equals(href, linkDTO.href);
    }

    @Override
    public String toString() {
        return "{" +
                " href='" + getHref() + "'" +
                "}";
    }

}
