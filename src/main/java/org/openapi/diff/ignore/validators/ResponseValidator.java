package org.openapi.diff.ignore.validators;

import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;

import java.util.Map;

@Data
public class ResponseValidator implements Validator {

    private ValidationResult result = new ValidationResult();
    private Map<String, Object> response;

    public boolean validate() {
//        List<String> supported = Arrays.stream(ResponseSupport.values())
//                .map(ResponseSupport::getValue)
//                .collect(Collectors.toList());
//
//
//        for (Map.Entry<String, Object> entry : response.entrySet()) {
//            if (!supported.contains(entry.getKey())) {
//                result.setMessage(String.format("value \"%s\" not supported int response", entry.getKey()));
//                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
//                return false;
//            }
//        }
        return true;
    }
}
