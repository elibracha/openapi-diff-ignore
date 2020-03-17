package com.github.elibracha;

import com.github.elibracha.models.ignore.*;
import org.junit.Test;
import org.openapi.diff.ignore.models.ignore.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class DeserializersTest {

    @Test
    public void testContextDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        ContextIgnore contextIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.context_deserialize"), ContextIgnore.class);
        assertEquals(util.getContext(), contextIgnoreFromFile);
    }

    @Test
    public void testPathsDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        PathsIgnore contextIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.paths_deserialize"), PathsIgnore.class);
        assertEquals(util.getPaths(), contextIgnoreFromFile);
    }

    @Test
    public void testSecurityDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        SecurityIgnore securityIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.security_deserialize"), SecurityIgnore.class);
        assertEquals(util.getSecurityPost(), securityIgnoreFromFile);

    }

    @Test
    public void testContentDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        Content contentIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.content_deserialize"), Content.class);
        assertEquals(util.getContentPost(), contentIgnoreFromFile);

    }

    @Test
    public void testRequestDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        RequestIgnore requestIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.request_deserialize"), RequestIgnore.class);
        assertEquals(util.getRequestPost(), requestIgnoreFromFile);
    }

    @Test
    public void testResponseDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        ResponseIgnore responseIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.response_deserialize"), ResponseIgnore.class);
        assertEquals(util.getResponsePost(), responseIgnoreFromFile);
    }

    @Test
    public void testOperationDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        OperationIgnore operationIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.operation_deserialize"), OperationIgnore.class);
        assertEquals(util.getOperationPost(), operationIgnoreFromFile);
    }

    @Test
    public void testHttpDeserialization() throws FileNotFoundException {
        TestUtil util = new TestUtil();
        HttpMethodIgnore httpMethodIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap("deserialize/.http_method_deserialize"), HttpMethodIgnore.class);
        assertEquals(util.getHttpMethodPost(), httpMethodIgnoreFromFile);
    }
}