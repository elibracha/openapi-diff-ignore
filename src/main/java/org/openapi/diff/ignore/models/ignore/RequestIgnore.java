package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class RequestIgnore {

    @JsonProperty("content-type")
    private List<String> contentType;

    public RequestIgnore() {
    }

    public RequestIgnore(List<String> contentType) {
        this.contentType = contentType;
    }

    public List<String> getContentType() {
        return contentType;
    }

    public void setContentType(List<String> contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestIgnore that = (RequestIgnore) o;
        return Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType);
    }

    @Override
    public String toString() {
        return "RequestIgnore{" +
                "contentType='" + contentType + '\'' +
                '}';
    }
}
