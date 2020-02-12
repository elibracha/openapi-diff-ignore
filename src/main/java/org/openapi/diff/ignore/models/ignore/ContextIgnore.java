package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContextIgnore {

    private Map<String, HttpMethodIgnore> paths;
    private String info;
    private String project;
    private String version;

}
