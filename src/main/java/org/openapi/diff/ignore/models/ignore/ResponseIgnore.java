package org.openapi.diff.ignore.models.ignore;

import java.util.List;
import java.util.Objects;

public class ResponseIgnore {

    private String info;
    private List<String> status;

    public ResponseIgnore() {
    }

    public ResponseIgnore(String info, List<String> status) {
        this.info = info;
        this.status = status;
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
        return status == that.status &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, status);
    }

    @Override
    public String toString() {
        return "ResponseIgnore{" +
                "info='" + info + '\'' +
                ", status=" + status +
                '}';
    }
}
