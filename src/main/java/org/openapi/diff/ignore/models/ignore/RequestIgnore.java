package org.openapi.diff.ignore.models.ignore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestIgnore extends IgnoreElemnt {
    private Content request;
}
