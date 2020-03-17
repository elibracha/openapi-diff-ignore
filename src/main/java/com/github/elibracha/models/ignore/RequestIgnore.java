package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.elibracha.deserializers.RequestDeserializer;
import com.github.elibracha.models.IgnoreElemnt;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = RequestDeserializer.class)
public class RequestIgnore extends IgnoreElemnt {
    private Content content;
}
