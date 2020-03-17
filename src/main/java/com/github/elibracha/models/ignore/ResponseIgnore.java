package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.ResponseDeserializer;
import com.github.elibracha.models.IgnoreElemnt;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = ResponseDeserializer.class)
public class ResponseIgnore extends IgnoreElemnt {
    private StatusIgnore response;
}
