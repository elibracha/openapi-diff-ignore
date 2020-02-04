package com.poalim.parsers;

import com.poalim.parsers.processors.IgnoreProcessor;

public class IgnoreParser {

    public static void main(String[] args) {
        IgnoreProcessor parser = new IgnoreProcessor(".ignore.yml");
        parser.processIgnore();
        System.out.println(parser.getMapKey());
    }
}
