package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedParameters;
import org.openapi.diff.ignore.models.ignore.ParametersIgnore;

public class ParamterProcessor {
    public boolean apply(ParametersIgnore parameters, ChangedParameters parameters1) {
        return false;
    }
}
