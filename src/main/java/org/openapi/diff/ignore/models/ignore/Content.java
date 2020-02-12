package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.IgnoreElemnt;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Content extends IgnoreElemnt {
    private Map<String, ContentSchema> content;
}
