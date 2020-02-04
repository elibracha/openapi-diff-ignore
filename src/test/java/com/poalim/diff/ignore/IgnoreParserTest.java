package com.poalim.diff.ignore;

import com.poalim.diff.ignore.processors.IgnoreProcessor;
import org.junit.Test;

public class IgnoreParserTest {


    @Test
    public void test() {
        IgnoreProcessor parser = new IgnoreProcessor(".ignore.yml");
        parser.processIgnore();
        System.out.println(parser.getMapKey());
    }
}
