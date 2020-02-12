package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.openapi.diff.ignore.context.ContextDeserializer;

import java.util.List;
import java.util.Objects;

public class PathsIgnore {

    @JsonProperty("request")
    private RequestIgnore requestIgnore;
    @JsonProperty("response")
    private ResponseIgnore responseIgnore;
    private List<String> parameters;
    private String info;
    @JsonProperty("security")
    @JsonDeserialize(using = ContextDeserializer.class)
    private SecurityIgnore securityIgnore;

    public PathsIgnore() {
    }

    public SecurityIgnore getSecurityIgnore() {
        return securityIgnore;
    }

    public void setSecurityIgnore(SecurityIgnore securityIgnore) {
        this.securityIgnore = securityIgnore;
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
        PathsIgnore that = (PathsIgnore) o;
        return Objects.equals(requestIgnore, that.requestIgnore) &&
                Objects.equals(responseIgnore, that.responseIgnore) &&
                Objects.equals(parameters, that.parameters) &&
                Objects.equals(info, that.info) &&
                Objects.equals(securityIgnore, that.securityIgnore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestIgnore, responseIgnore, parameters, info, securityIgnore);
    }

}
