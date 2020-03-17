package com.github.elibracha.models;

import com.github.elibracha.models.ignore.ContextIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenApiIgnore {

    private boolean validIgnore;
    private ContextIgnore ignore;

}
