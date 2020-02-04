package com.poalim.parsers.models;

import java.util.Objects;

public class ResponseIgnore {

    private String info;
    private int status;

    public ResponseIgnore() {
    }

    public ResponseIgnore(String info, int status) {
        this.info = info;
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
