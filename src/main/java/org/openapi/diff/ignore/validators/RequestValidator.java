package org.openapi.diff.ignore.validators;

import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;

import java.util.Map;

@Data
public class RequestValidator implements Validator {

    private final ValidationResult result = new ValidationResult();
    private Map<String, Object> request;

    public boolean validate() {
//        List<String> supported = Arrays.stream(RequestSupport.values())
//                .map(RequestSupport::getValue)
//                .collect(Collectors.toList());
//
//        for (Map.Entry<String, Object> entry : request.entrySet()) {
//            if (!supported.contains(entry.getKey())) {
//                result.setMessage(String.format("value \"%s\" not supported in request", entry.getKey()));
//                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
//                return false;
//            }
//        }
//
//        if (request.containsKey("parameters") && request.get("parameters") != null) {
//            requestParamValidator.setParams((Map<String, Object>) request.get("parameters"));
//
//            if (!requestParamValidator.validate()) {
//                this.result = requestParamValidator.getResult();
//                return false;
//            }
//        }

        return true;
    }
}
