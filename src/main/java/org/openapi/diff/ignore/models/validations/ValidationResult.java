package org.openapi.diff.ignore.models.validations;

import lombok.Data;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

@Data
public class ValidationResult {

    private String message;
    private ValidationStatus validationStatus;

}
