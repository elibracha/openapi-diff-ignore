package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.ResponseDeserializer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ResponseDeserializer.class)
public class ResponseIgnore {

    private String info;
    private List<String> status;
    private List<String> content;
}
