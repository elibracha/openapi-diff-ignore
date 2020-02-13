package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.deserializers.SecurityDeserializer;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = SecurityDeserializer.class)
public class SecurityIgnore extends IgnoreElemnt {
    private Map<String, SecurityProperty> security;
}
