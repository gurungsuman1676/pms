package com.pms.app.domain;

public enum LocationEnum {
    SHIPPING("SHIPPING"), PRE_KNITTING("PRE-KNITTING"), PRE_KNITTING_COMPLETED("PRE-KNITTING-COMPLETED"),
    ORDER_IN("ORDER-IN"), ORDER_OUT("ORDER-OUT");

    private final String name;

    LocationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
