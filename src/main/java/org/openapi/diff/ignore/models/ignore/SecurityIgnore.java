package org.openapi.diff.ignore.models.ignore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapi.diff.ignore.context.SecurityDeserializer;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = SecurityDeserializer.class)
public class SecurityIgnore {

    private Map<String, List<String>> securityOperationIgnoreList;

}
