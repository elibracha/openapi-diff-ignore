package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.models.IgnoreElemnt;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestIgnore extends IgnoreElemnt {
    private Content request;
}
