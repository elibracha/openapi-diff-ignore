package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.SecurityDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = SecurityDeserializer.class)
public class SecurityIgnore extends IgnoreElemnt {
    private Map<String, SecurityProperty> security;
}
