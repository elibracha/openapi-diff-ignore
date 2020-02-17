package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.model.*;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.junit.Test;
import org.openapi.diff.ignore.processors.ParameterProcessor;
import org.openapi.diff.ignore.processors.RequestProcessor;
import org.openapi.diff.ignore.processors.ResponseProcessor;
import org.openapi.diff.ignore.processors.SecurityProcessor;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class ProcessorsTest {

    @Test
    public void testParametersProcessor() {
        ParameterProcessor parameterProcessor = new ParameterProcessor();
        Parameter parameterUserName = new Parameter();
        Parameter parameterPassword = new Parameter();
        TestUtil util = new TestUtil();

        parameterUserName.setName("username");
        parameterPassword.setName("password");

        List<Parameter> oldParameters = Arrays.asList(parameterUserName, parameterPassword);
        List<Parameter> newParameters = Collections.EMPTY_LIST;

        ChangedParameters changedParameters = new ChangedParameters(oldParameters, newParameters, new DiffContext());
        changedParameters.setMissing(oldParameters);

        assertTrue(parameterProcessor.apply(util.getParametersPost(), changedParameters));
    }

    @Test
    public void testSecurityProcessor() {
        SecurityProcessor securityProcessor = new SecurityProcessor();
        List<SecurityRequirement> requirementsOld = new ArrayList<>();
        List<SecurityRequirement> requirementsNew = new ArrayList<>();

        ChangedSecurityRequirements securityRequirements = new ChangedSecurityRequirements(requirementsOld, requirementsNew);

        SecurityRequirement securityRequirementPetAuth = new SecurityRequirement();
        SecurityRequirement SecurityRequirementRandom = new SecurityRequirement();
        TestUtil util = new TestUtil();

        securityRequirementPetAuth.put("petstore_auth", Arrays.asList("write:pets", "write:pests", "read:pets"));
        SecurityRequirementRandom.put("random", Collections.singletonList("write:random"));


        requirementsNew.add(securityRequirementPetAuth);
        requirementsNew.add(SecurityRequirementRandom);

        securityRequirements.setMissing(requirementsNew);

        assertTrue(securityProcessor.apply(util.getSecurityPost(), securityRequirements));
    }

    @Test
    public void testRequestProcessor() {
        RequestProcessor requestProcessor = new RequestProcessor();
        RequestBody requestBodyOld = new RequestBody();
        RequestBody requestBodyNew = new RequestBody();
        DiffContext diffContext = new DiffContext();
        TestUtil util = new TestUtil();

        MediaType mediaTypeJson = new MediaType();
        MediaType mediaTypeXForm = new MediaType();

        Schema schemaJson = new Schema();
        Schema schemaXForm = new Schema();

        schemaJson.setName("petId");
        schemaXForm.setName("orderId");

        mediaTypeJson.setSchema(schemaJson);
        mediaTypeXForm.setSchema(schemaXForm);

        Map<String, MediaType> missing = new HashMap<String, MediaType>() {
            {
                put("application/json", mediaTypeJson);
                put("application/x-www-form-urlencoded", mediaTypeXForm);
            }
        };

        ChangedRequestBody changedRequestBody = new ChangedRequestBody(requestBodyOld, requestBodyNew, diffContext);

        ChangedContent changedContent = new ChangedContent(new Content(), new Content(), diffContext);
        changedContent.setMissing(missing);

        changedRequestBody.setContent(changedContent);

        assertTrue(requestProcessor.apply(util.getRequestPost(), changedRequestBody));
    }

    @Test
    public void testResponseProcessor() {
        ResponseProcessor responseProcessor = new ResponseProcessor();
        ApiResponses responseOld = new ApiResponses();
        ApiResponses responseNew = new ApiResponses();
        DiffContext diffContext = new DiffContext();
        TestUtil util = new TestUtil();

        ApiResponse apiResponse = new ApiResponse();

        Content content = new Content();
        MediaType mediaTypeJson = new MediaType();
        MediaType mediaTypeXml = new MediaType();

        Schema schemaJson = new Schema();
        Schema schemaXml = new Schema();

        schemaJson.setName("petId");
        schemaXml.setName("orderId");

        mediaTypeJson.setSchema(schemaJson);
        mediaTypeXml.setSchema(schemaXml);


        content.put("application/json", mediaTypeJson);
        content.put("application/xml", mediaTypeXml);

        apiResponse.setContent(content);

        ChangedApiResponse changedApiResponse = new ChangedApiResponse(responseOld, responseNew, diffContext);

        Map<String, ApiResponse> missing = new HashMap<String, ApiResponse>() {
            {
                put("200", apiResponse);
            }
        };

        changedApiResponse.setMissing(missing);

        assertTrue(responseProcessor.apply(util.getResponsePost(), changedApiResponse));
    }
}
