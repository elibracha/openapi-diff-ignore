package com.poalim.parsers;

import com.poalim.parsers.processors.IgnoreProcessor;
import org.junit.Test;

public class IgnoreParserTest {


    @Test
    public void test() {
        IgnoreProcessor parser = new IgnoreProcessor(".ignore.yml");
        parser.processIgnore();
        System.out.println(parser.getMapKey());
    }
}
