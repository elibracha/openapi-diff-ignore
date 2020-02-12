package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.PathDeserializer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = PathDeserializer.class)
public class PathIgnore {

    private RequestIgnore request;
    private ResponseIgnore response;
    private List<String> parameters;
    private String info;
    private SecurityIgnore security;

}
