package com.github.elibracha.models;

import java.util.Collections;
import java.util.List;

public class SpecConstants {
    public final static String DEFAULT_SEARCH = ".diffignore";

    public final static List<String> VERSIONS = Collections.singletonList(
            "1.0.0"
    );

    public static class ContentSchemaEntries {
        public final static String SCHEMA = "schema";
    }

    public static class RequestEntries {
        public final static String CONTENT = "content";
    }

    public static class ContextEntries {
        public final static String VERSION = "version";
        public final static String PROJECT = "project";
        public final static String INFO = "info";
        public final static String PATHS = "paths";
        public final static String EXTENDS = "extends";
    }

    public static class OperationIgnoresEntries {
        public final static String REQUEST = "request";
        public final static String RESPONSE = "response";
        public final static String PARAMETERS = "parameters";
        public final static String SECURITY = "security";
    }

    public static class HttpMethods {
        public final static String POST = "post";
        public final static String GET = "get";
        public final static String OPTIONS = "options";
        public final static String DELETE = "delete";
        public final static String PUT = "put";
        public final static String TRACE = "trace";
        public final static String PATCH = "patch";
        public final static String HEAD = "head";
    }
}
