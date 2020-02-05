package org.openapi.diff.ignore.models.ignore;

import java.util.Objects;

public class ParamsIgnore {

    private String content;

    public ParamsIgnore() {
    }

    public ParamsIgnore(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParamsIgnore that = (ParamsIgnore) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "ParamsIgnore{" +
                "content='" + content + '\'' +
                '}';
    }
}
