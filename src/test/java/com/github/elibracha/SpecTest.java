package com.github.elibracha;

import com.github.elibracha.models.SpecConstants;
import org.junit.Assert;
import org.junit.Test;
import com.github.elibracha.models.validations.enums.ContextSupport;
import com.github.elibracha.models.validations.enums.HttpMethodSupport;
import com.github.elibracha.models.validations.enums.OperationSupport;
import com.github.elibracha.models.validations.enums.RequestSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpecTest {

    @Test
    public void testVersionSupport() {
        Assert.assertEquals(SpecConstants.VERSIONS.size(), 1);
    }

    @Test
    public void testContextSupport() {
        List<String> supported = Arrays.stream(ContextSupport.values())
                .map(ContextSupport::getValue)
                .collect(Collectors.toList());

        assertTrue(supported.contains("version"));
        assertTrue(supported.contains("info"));
        assertTrue(supported.contains("extends"));
        assertTrue(supported.contains("paths"));
        assertTrue(supported.contains("project"));
    }

    @Test
    public void testHttpMethodSupport() {
        List<String> supported = Arrays.stream(HttpMethodSupport.values())
                .map(HttpMethodSupport::getValue)
                .collect(Collectors.toList());

        assertTrue(supported.contains("post"));
        assertTrue(supported.contains("put"));
        assertTrue(supported.contains("get"));
        assertTrue(supported.contains("delete"));
        assertTrue(supported.contains("options"));
        assertTrue(supported.contains("trace"));
        assertTrue(supported.contains("head"));
        assertTrue(supported.contains("patch"));
    }

    @Test
    public void testOperationSupport() {
        List<String> supported = Arrays.stream(OperationSupport.values())
                .map(OperationSupport::getValue)
                .collect(Collectors.toList());

        assertTrue(supported.contains("parameters"));
        assertTrue(supported.contains("request"));
        assertTrue(supported.contains("response"));
        assertTrue(supported.contains("security"));
    }

    @Test
    public void testRequestSupport() {
        List<String> supported = Arrays.stream(RequestSupport.values())
                .map(RequestSupport::getValue)
                .collect(Collectors.toList());

        assertTrue(supported.contains("content"));
    }
}
