package com.roman.lv.mullvad;

public enum AvailabilityStatus {

    IN_STOCK("in stock"), OUT_OF_STOCK("out of stock"), UNKNOWN(""), INFORMATION_NOT_AVAILABLE("information not available"), ACCESS_DENIED("access denied"), AVAILABLE("Available"), NOT_AVAILABLE("not available");

    private final String webCode;

    AvailabilityStatus(String status) {
        this.webCode = status;
    }

    public static AvailabilityStatus fromWebCode(String webCode) {
        switch (webCode) {
            case "in stock": return IN_STOCK;
            case "out of stock": return OUT_OF_STOCK;
            case "access denied": return ACCESS_DENIED;
            case "information not available": return INFORMATION_NOT_AVAILABLE;
            case "Available":return  AVAILABLE;
            case "not available":return NOT_AVAILABLE;
            default: return UNKNOWN;
        }
    }

    public String getWebCode() {
        return webCode;
    }
}

