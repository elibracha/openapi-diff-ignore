package com.github.elibracha.processors;

import com.github.elibracha.models.ignore.ParametersIgnore;
import com.qdesrame.openapi.diff.model.ChangedParameters;

import java.util.stream.Collectors;

public class ParameterProcessor {

    public boolean apply(ParametersIgnore parameters, ChangedParameters changedParameters) {
        if (parameters.isIgnoreAll()) {
            return true;
        }

        changedParameters.setMissing(
                changedParameters.getMissing().stream().filter(e -> !parameters.getParameters().contains(e.getName())).collect(Collectors.toList())
        );
        changedParameters.setIncreased(
                changedParameters.getIncreased().stream().filter(e -> !parameters.getParameters().contains(e.getName())).collect(Collectors.toList())
        );
        changedParameters.setChanged(
                changedParameters.getChanged().stream().filter(e -> !parameters.getParameters().contains(e.getName())).collect(Collectors.toList())
        );

        return (changedParameters.getMissing() == null ||
                changedParameters.getMissing().size() == 0) &&
                (changedParameters.getIncreased() == null ||
                        changedParameters.getIncreased().size() == 0) &&
                (changedParameters.getChanged() == null ||
                        changedParameters.getChanged().size() == 0);
    }

}
