package com.github.elibracha.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.elibracha.deserializers.PathsDeserializer;
import com.github.elibracha.models.IgnoreElemnt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = PathsDeserializer.class)
public class PathsIgnore extends IgnoreElemnt {

    private Map<String, HttpMethodIgnore> paths;
}
