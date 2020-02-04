package com.poalim.parsers;

import com.poalim.parsers.context.MapKeyIgnore;
import com.poalim.parsers.processors.IgnoreProcessor;

public class IgnoreParser {

    public static void main(String[] args) {
        IgnoreProcessor parser = new IgnoreProcessor(".ignore.yml");
        MapKeyIgnore<String, String> result = parser.processIgnore();
        System.out.println(result);
    }
}
