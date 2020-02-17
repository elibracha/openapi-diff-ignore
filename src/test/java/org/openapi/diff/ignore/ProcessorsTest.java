package org.openapi.diff.ignore;

import com.qdesrame.openapi.diff.model.ChangedContent;
import com.qdesrame.openapi.diff.model.ChangedParameters;
import com.qdesrame.openapi.diff.model.ChangedRequestBody;
import com.qdesrame.openapi.diff.model.DiffContext;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.junit.Test;
import org.openapi.diff.ignore.processors.ParameterProcessor;
import org.openapi.diff.ignore.processors.RequestProcessor;

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
}
