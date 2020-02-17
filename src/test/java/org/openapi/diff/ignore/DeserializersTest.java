package org.openapi.diff.ignore;

import org.junit.Test;
import org.openapi.diff.ignore.models.ignore.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class DeserializersTest {

    @Test
    public void testContextDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        ContextIgnore contextIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".context"), ContextIgnore.class);
        assertEquals(util.getContext(), contextIgnoreFromFile);
    }

    @Test
    public void testPathsDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        PathsIgnore contextIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".paths"), PathsIgnore.class);
        assertEquals(util.getPaths(), contextIgnoreFromFile);
    }

    @Test
    public void testSecurityDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        SecurityIgnore securityIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".security"), SecurityIgnore.class);
        assertEquals(util.getSecurityPost(), securityIgnoreFromFile);

    }

    @Test
    public void testContentDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        Content contentIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".content"), Content.class);
        assertEquals(util.getContentPost(), contentIgnoreFromFile);

    }

    @Test
    public void testRequestDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        RequestIgnore requestIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".request"), RequestIgnore.class);
        assertEquals(util.getRequestPost(), requestIgnoreFromFile);
    }

    @Test
    public void testResponseDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        ResponseIgnore responseIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".response"), ResponseIgnore.class);
        assertEquals(util.getResponsePost(), responseIgnoreFromFile);
    }

    @Test
    public void testOperationDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        OperationIgnore operationIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".operation"), OperationIgnore.class);
        assertEquals(util.getOperationPost(), operationIgnoreFromFile);
    }

    @Test
    public void testHttpDeserialization() throws FileNotFoundException {
        DeserializersTestUtil util = new DeserializersTestUtil();
        HttpMethodIgnore httpMethodIgnoreFromFile = ObjectMapperFactory.createYaml().convertValue(util.loadMap(".http_method"), HttpMethodIgnore.class);
        assertEquals(util.getHttpMethodPost(), httpMethodIgnoreFromFile);
    }
}