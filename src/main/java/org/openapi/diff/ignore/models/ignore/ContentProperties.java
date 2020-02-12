package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentProperties extends IgnoreElemnt {
    private List<String> properties;
}
