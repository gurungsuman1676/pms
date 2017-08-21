package com.pms.app.domain;

public enum LocationEnum {
    SHIPPING("SHIPPING"), PRE_KNITTING("PRE-KNITTING"), NO_LOCATION("NO-LOCATION"), REJECTED("REJECTED");

    private final String name;

    LocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}