package com.flalottery.esavalidation.exception;

public enum ValidationWinStatus {
    ACCEPTED("ACCEPTED"), //don't change this value.
    WINNER(ValidationWinStatus.INELIGIBLE_TICKET),
    CLAIM_AT_LOTTERY(ValidationWinStatus.INELIGIBLE_TICKET),
    NOT_A_WINNER(ValidationWinStatus.SUCCESSFULLY_ENTERED),
    ALREADY_PAID(ValidationWinStatus.INELIGIBLE_TICKET),
    ALREADY_CLAIMED(ValidationWinStatus.INELIGIBLE_TICKET),
    GAME_CLOSED(ValidationWinStatus.INELIGIBLE_TICKET),
    REQUEST_REJECTED_SEE_RETAILER(ValidationWinStatus.CONTACT_CUSTOMER_SUPPORT),
    REJECT_REASON("FALSE"), // don't change this value.
    REJECTED("REJECTED"), // don't change this value.
    FOUND("FOUND"),
    ERROR_UNKNOWN("ERROR_UNKNOWN"),
    DAY_MAX("DAY_MAX"),
    INVALID("INVALID"),
    SCOFF_NOT_A_WINNER("NOT_A_WINNER"),
    SCOFF_WINNER("WINNER");

    public static final String INELIGIBLE_TICKET = "Your ticket is not eligible for this promotion.";
    public static final String SUCCESSFULLY_ENTERED = "You are successfully entered into the %s.";
    public static final String CONTACT_CUSTOMER_SUPPORT = "Call our Customer Service Support Line at 1-850-487-7787.";
    public static final String TRY_LATER = "Please try your ticket again later.";
    private final String description;

    private ValidationWinStatus(String description) {

        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

}
