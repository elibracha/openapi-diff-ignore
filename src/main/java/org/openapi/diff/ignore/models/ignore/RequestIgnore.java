package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.RequestDeserializer;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = RequestDeserializer.class)
public class RequestIgnore {

    @JsonProperty("content-type")
    private List<String> contentType;
}
