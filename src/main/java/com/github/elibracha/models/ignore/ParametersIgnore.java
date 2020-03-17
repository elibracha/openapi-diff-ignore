package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.ParameterDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ParameterDeserializer.class)
public class ParametersIgnore extends IgnoreElemnt {
    private List<String> parameters;
}
