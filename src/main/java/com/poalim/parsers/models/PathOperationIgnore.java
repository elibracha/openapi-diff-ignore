package com.poalim.parsers.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class PathOperationIgnore {

    @JsonProperty("ignore-type")
    private String ignoreType;
    private List<OperationIgnore> ignore;

    public PathOperationIgnore() {
    }

    public PathOperationIgnore(List<OperationIgnore> ignore, String ignoreType) {
        this.ignore = ignore;
        this.ignoreType = ignoreType;
    }

    public List<OperationIgnore> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<OperationIgnore> ignore) {
        this.ignore = ignore;
    }

    public String getIgnoreType() {
        return ignoreType;
    }

    public void setIgnoreType(String ignoreType) {
        this.ignoreType = ignoreType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathOperationIgnore that = (PathOperationIgnore) o;
        return Objects.equals(ignore, that.ignore) &&
                Objects.equals(ignoreType, that.ignoreType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ignore, ignoreType);
    }

    @Override
    public String toString() {
        return "PathOperationIgnore{" +
                "ignore=" + ignore +
                ", ignoreType='" + ignoreType + '\'' +
                '}';
    }
}
