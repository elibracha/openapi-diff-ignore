package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.ContextDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ContextDeserializer.class)
public class ContextIgnore extends IgnoreElemnt {

    private PathsIgnore paths;
    private String info;
    private String project;
    private String version;

}
