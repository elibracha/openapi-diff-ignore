package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.deserializes.SecurityPropertyDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = SecurityPropertyDeserializer.class)
public class SecurityProperty extends IgnoreElemnt {
    private List<String> properties;
}
