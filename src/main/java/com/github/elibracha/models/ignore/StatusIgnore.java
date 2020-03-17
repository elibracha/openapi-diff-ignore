package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.elibracha.deserializers.StatusDeserializer;
import com.github.elibracha.models.IgnoreElemnt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = StatusDeserializer.class)
public class StatusIgnore extends IgnoreElemnt {
    private Map<String, Content> status;
}
