package org.openapi.diff.ignore.models.ignore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GlobalIgnore {

    private Map<String, OperationIgnore> paths;
    private String info;
    private String project;
    private String version;

    public GlobalIgnore() {
        this.paths = new HashMap<>();
    }

    public GlobalIgnore(Map<String, OperationIgnore> paths, String info, String project, String version) {
        this.paths = paths;
        this.info = info;
        this.project = project;
        this.version = version;
    }

    public Map<String, OperationIgnore> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, OperationIgnore> paths) {
        this.paths = paths;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GlobalIgnore that = (GlobalIgnore) o;
        return Objects.equals(paths, that.paths) &&
                Objects.equals(info, that.info) &&
                Objects.equals(project, that.project) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paths, info, project, version);
    }

    @Override
    public String toString() {
        return "GlobalIgnore{" +
                "paths=" + paths +
                ", info='" + info + '\'' +
                ", project='" + project + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
