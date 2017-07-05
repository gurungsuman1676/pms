package com.pms.app.controller;


public interface Routes {
    public static final String V1 = "api/v1";
    public static final String SIZE = V1 + "/sizes";
    public static final String COLOR = V1 + "/colors";
    public static final String PRICE = V1 + "/prices";
    public static final String PRINT = V1 + "/prints";
    public static final String USER = V1 + "/users";
    public static final String DESIGN = V1 + "/designs";
    public static final String CLOTH = V1 + "/clothes";
    public static final String CUSTOMER = V1 + "/customers";
    public static final String AUTHENTICATE = V1 + "/authenticate";
    public static final String REPORT = V1 + "/report";
    public static final String LOCATION = V1 + "/locations";
    public static final String CURRENCY = V1 + "/currencies";
    public static final String YARNS = V1 + "/yarns";
    public static final String ACTIVITY = CLOTH + "/{clothId}" + "/activities";
    public static final String KNITTER = V1 + "/knitters";
    public static final String MACHINE = V1 + "/machines";
    public static final String KNITTER_HISTORY = V1 + "/knitters-history";
    public static final String SHAWL = V1 + "/shawls";
    public static final String SHAWL_COLOR = SHAWL + "/colors";
    public static final String DESIGN_PROPERTIES = DESIGN + "/{id}/properties";
    public static final String SHAWL_ENTRY = SHAWL + "/entries";


}
