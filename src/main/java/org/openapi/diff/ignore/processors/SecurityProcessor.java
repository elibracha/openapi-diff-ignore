package org.openapi.diff.ignore.processors;

import com.qdesrame.openapi.diff.model.ChangedSecurityRequirement;
import com.qdesrame.openapi.diff.model.ChangedSecurityRequirements;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.openapi.diff.ignore.models.ignore.SecurityIgnore;
import org.openapi.diff.ignore.models.ignore.SecurityProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SecurityProcessor {

    public boolean apply(SecurityIgnore security, ChangedSecurityRequirements securityRequirements) {
        List<ChangedSecurityRequirement> changeToRemove = new ArrayList<>();
        List<SecurityRequirement> missingToRemove = new ArrayList<>();
        List<SecurityRequirement> increasedToRemove = new ArrayList<>();

        if (securityRequirements != null) {
            if (securityRequirements.getChanged() != null) {
                for (ChangedSecurityRequirement changedSecurityRequirement : securityRequirements.getChanged()) {
                    boolean result = processRequirement(changedSecurityRequirement.getOldSecurityRequirement(), security.getSecurity());
                    if (result)
                        changeToRemove.add(changedSecurityRequirement);
                }
                securityRequirements.getChanged().removeAll(changeToRemove);
            }

            if (securityRequirements.getMissing() != null) {
                for (SecurityRequirement changedSecurityRequirement : securityRequirements.getMissing()) {
                    boolean result = processRequirement(changedSecurityRequirement, security.getSecurity());
                    if (result)
                        missingToRemove.add(changedSecurityRequirement);

                }
                securityRequirements.getMissing().removeAll(missingToRemove);
            }
            if (securityRequirements.getIncreased() != null) {
                for (SecurityRequirement changedSecurityRequirement : securityRequirements.getIncreased()) {
                    boolean result = processRequirement(changedSecurityRequirement, security.getSecurity());
                    if (result)
                        increasedToRemove.add(changedSecurityRequirement);

                }
                securityRequirements.getIncreased().removeAll(increasedToRemove);
            }
        }

        return (securityRequirements.getChanged() == null ||
                securityRequirements.getChanged().size() == 0) &&
                (securityRequirements.getMissing() == null ||
                        securityRequirements.getMissing().size() == 0) &&
                (securityRequirements.getIncreased() == null ||
                        securityRequirements.getIncreased().size() == 0);
    }

    private boolean processRequirement(SecurityRequirement oldSecurityRequirement, Map<String, SecurityProperty> security) {
        List<String> keysToRemove = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : oldSecurityRequirement.entrySet()) {
            SecurityProperty securityProperty = security.get(entry.getKey());
            if (securityProperty != null) {
                List<String> newValue = entry.getValue().stream().filter(e -> !securityProperty.getProperties().contains(e)).collect(Collectors.toList());
                if (newValue.size() == 0)
                    keysToRemove.add(entry.getKey());
                entry.setValue(newValue);
            }
        }

        oldSecurityRequirement.keySet().removeAll(keysToRemove);
        return oldSecurityRequirement.size() == 0;
    }
}
