package org.openapi.diff.ignore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.ignore.ContextIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenApiIgnore {

    private boolean validIgnore;
    private ContextIgnore ignore;

}
