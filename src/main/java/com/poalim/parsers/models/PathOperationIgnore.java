package com.poalim.parsers.models;

import java.util.List;
import java.util.Objects;

public class PathOperationIgnore {

    private List<PathIgnore> ignore;

    public PathOperationIgnore() {
    }

    public PathOperationIgnore(List<PathIgnore> ignore) {
        this.ignore = ignore;
    }

    public List<PathIgnore> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<PathIgnore> ignore) {
        this.ignore = ignore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathOperationIgnore that = (PathOperationIgnore) o;
        return Objects.equals(ignore, that.ignore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ignore);
    }

    @Override
    public String toString() {
        return "PathOperationIgnore{" +
                "ignore=" + ignore +
                '}';
    }
}
