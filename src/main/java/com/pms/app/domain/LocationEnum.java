package com.pms.app.domain;

public enum LocationEnum {
    SHIPPING("SHIPPING"), PRE_KNITTING("PRE-KNITTING"), PRE_KNITTING_COMPLETED("PRE-KNITTING-COMPLETED");

    private final String name;

    LocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
