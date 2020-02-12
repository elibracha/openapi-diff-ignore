package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PathIgnore {

    private RequestIgnore request;
    private ResponseIgnore response;
    private List<String> parameters;
    private String info;
    private SecurityIgnore security;

}
