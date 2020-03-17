package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.SecurityPropertyDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = SecurityPropertyDeserializer.class)
public class SecurityProperty extends IgnoreElemnt {
    private List<String> properties;
}
