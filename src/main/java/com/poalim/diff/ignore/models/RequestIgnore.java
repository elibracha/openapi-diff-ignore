package com.poalim.diff.ignore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class RequestIgnore {

    @JsonProperty("content-type")
    private List<String> contentType;
    @JsonProperty("parameters")
    private ParamsIgnore params;

    public RequestIgnore() {
    }

    public RequestIgnore(List<String> contentType, ParamsIgnore params) {
        this.contentType = contentType;
        this.params = params;
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
        return Objects.equals(contentType, that.contentType) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, params);
    }

    @Override
    public String toString() {
        return "RequestIgnore{" +
                "contentType='" + contentType + '\'' +
                ", params=" + params +
                '}';
    }
}
