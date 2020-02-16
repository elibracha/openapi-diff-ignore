package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedParameters;
import org.openapi.diff.ignore.models.ignore.ParametersIgnore;

public class ParamterProcessor {

    public boolean apply(ParametersIgnore parameters, ChangedParameters changedParameters) {
        changedParameters.getMissing().stream().filter(e -> !parameters.getParameters().contains(e.getName()));
        changedParameters.getIncreased().stream().filter(e -> !parameters.getParameters().contains(e.getName()));
        changedParameters.getChanged().stream().filter(e -> !parameters.getParameters().contains(e.getName()));

        return changedParameters.getMissing() == null ||
                changedParameters.getMissing().size() == 0 &&
                        changedParameters.getIncreased() == null ||
                changedParameters.getIncreased().size() == 0 &&
                        changedParameters.getChanged() == null ||
                changedParameters.getChanged().size() == 0;
    }

}
