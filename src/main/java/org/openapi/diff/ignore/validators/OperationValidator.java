package org.openapi.diff.ignore.validators;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.openapi.diff.ignore.models.validations.ValidationResult;
import org.openapi.diff.ignore.models.validations.enums.HttpMethodSupport;
import org.openapi.diff.ignore.models.validations.enums.ValidationStatus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OperationValidator implements Validator {

    private JsonNode operations;
    private ValidationResult result;
    private ResponseValidator responseValidator;
    private RequestValidator requestValidator;


    public boolean validate() {
        List<String> supported = Arrays.stream(HttpMethodSupport.values())
                .map(HttpMethodSupport::getValue)
                .collect(Collectors.toList());


        for (Iterator<Map.Entry<String, JsonNode>> it = operations.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> operationScope = it.next();

            if (!supported.contains(operationScope.getKey())) {
                result.setMessage(String.format("the method \"%s\" not a valid entry in operation method", operationScope.getKey()));
                result.setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
                return false;
            }

            if (operationScope.getValue() instanceof Map) {
//                if (((Map) operationScope.getValue()).containsKey("security")) {
//                    if (!(operationScope.getValue().get("security") instanceof ArrayList)) {
//                        result.setMessage("the security field most be a list").setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
//                        return false;
//                    }
//                }
//
//                if (((Map) operationScope.getValue()).containsKey("parameters")) {
//                    if (!(((Map<String, Object>) entry.getValue()).get("parameters") instanceof ArrayList)) {
//                        result.setMessage("the parameters field most be a list").setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
//                        return false;
//                    }
//                }
//
//                if (((Map) operationScope.getValue()).containsKey("request")) {
//                    requestValidator.setRequest(operationScope.getValue()).get("request"));
//
//                    if (!requestValidator.validate()) {
//                        this.result = requestValidator.getResult();
//                        return false;
//                    }
//                }
//
//                if (((Map<String, Object>) entry.getValue()).containsKey("response")) {
//                    responseValidator.setResponse((Map<String, Object>) ((Map<String, Object>) entry.getValue()).get("response"));
//
//                    if (!responseValidator.validate()) {
//                        this.result = responseValidator.getResult();
//                        return false;
//                    }
//                }
            } else {
//                if (!entry.getKey().equals("ignore-type") || !(entry.getValue().equals("single") || entry.getValue().equals("all"))) {
//                    result.setMessage(String.format("invalid entry \"%s\" value, or value might not be allowed \"%s\"." +
//                            " (only \"single\" or \"all\")", entry.getKey(), entry.getValue()))
//                            .setValidationStatus(ValidationStatus.BAD_IGNORE_FILE);
//                    return false;
//                }
            }
        }
        return true;
    }
}
