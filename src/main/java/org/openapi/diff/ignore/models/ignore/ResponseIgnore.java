package org.openapi.diff.ignore.models.ignore;

import java.util.List;
import java.util.Objects;

public class ResponseIgnore {

    private String info;
    private List<String> status;
    private List<String> content;

    public ResponseIgnore() {
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseIgnore that = (ResponseIgnore) o;
        return Objects.equals(info, that.info) &&
                Objects.equals(status, that.status) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, status, content);
    }

    @Override
    public String toString() {
        return "ResponseIgnore{" +
                "info='" + info + '\'' +
                ", status=" + status +
                ", content=" + content +
                '}';
    }
}
