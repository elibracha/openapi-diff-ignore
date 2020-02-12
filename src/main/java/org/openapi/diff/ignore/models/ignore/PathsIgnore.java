package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.PathsDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = PathsDeserializer.class)
public class PathsIgnore extends IgnoreElemnt {

    private Map<String, HttpMethodIgnore> paths;
}
