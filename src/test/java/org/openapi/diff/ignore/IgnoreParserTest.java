package org.openapi.diff.ignore;

import org.junit.Test;
import org.openapi.diff.ignore.processors.IgnoreProcessor;

public class IgnoreParserTest {


    @Test
    public void test() {
        IgnoreProcessor parser = new IgnoreProcessor(".ignore.yml");
        parser.processIgnore();
        System.out.println(parser.getMapKey());
    }
}
