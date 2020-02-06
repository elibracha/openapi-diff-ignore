package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class PathIgnore {

    @JsonProperty("request")
    private RequestIgnore requestIgnore;
    @JsonProperty("response")
    private ResponseIgnore responseIgnore;
    private List<String> parameters;
    private String info;

    public PathIgnore() {
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RequestIgnore getRequestIgnore() {
        return requestIgnore;
    }

    public void setRequestIgnore(RequestIgnore requestIgnore) {
        this.requestIgnore = requestIgnore;
    }

    public ResponseIgnore getResponseIgnore() {
        return responseIgnore;
    }

    public void setResponseIgnore(ResponseIgnore responseIgnore) {
        this.responseIgnore = responseIgnore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathIgnore that = (PathIgnore) o;
        return Objects.equals(requestIgnore, that.requestIgnore) &&
                Objects.equals(responseIgnore, that.responseIgnore) &&
                Objects.equals(parameters, that.parameters) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIgnore, responseIgnore, parameters, info);
    }

    @Override
    public String toString() {
        return "PathIgnore{" +
                "requestIgnore=" + requestIgnore +
                ", responseIgnore=" + responseIgnore +
                ", parameters=" + parameters +
                ", info='" + info + '\'' +
                '}';
    }
}
