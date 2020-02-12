package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.deserializes.RequestDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = RequestDeserializer.class)
public class RequestIgnore extends IgnoreElemnt {
    private Content request;
}
