package com.github.elibracha.models.validations;

import com.github.elibracha.models.validations.enums.ValidationStatus;
import lombok.Data;

@Data
public class ValidationResult {

    private String message;
    private ValidationStatus validationStatus;

}
