package com.flalottery.esavalidation.exception;

public enum ValidationResultCode {
    ERROR_AGENT_NOT_ELIGIBLE_KEYLESS(ValidationResultCode.TRY_LATER),
    ERROR_AGENT_NOT_KEYLESS(ValidationResultCode.TRY_LATER),
    ERROR_BAD_CHECK_DIGITS(ValidationResultCode.TRY_LATER),
    ERROR_CCARD_LOAD_LIMIT_EXCEEDED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_CHECKSUM(ValidationResultCode.TRY_LATER),
    ERROR_CLAIM_AT_ONLINE(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_DAILY_KEYLESS_EXCEEDED(ValidationResultCode.TRY_LATER),
    ERROR_DBTRANSACTION_FAILURE(ValidationResultCode.TRY_LATER),
    ERROR_DIFFERENT_CARD_NUMBER(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_ESCC_CONFIGURATION(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_FUNCTION_NOT_FOUND(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_GAME_NOT_KEYLESS(ValidationResultCode.TRY_LATER),
    ERROR_INSUFFICIENT_PRIVILEGE(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_INVALID_ATTEMPT(ValidationResultCode.TRY_LATER),
    ERROR_INVALID_PACK_OR_TICKET(ValidationResultCode.TRY_LATER),
    ERROR_INVALID_PACK_STATUS(ValidationResultCode.TRY_LATER),
    ERROR_INVALID_PRIZE_AMOUNT(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_INVALID_PRIZE_LEVEL(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_INVAL_IDGVT_PRIZE_LEVEL(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_KEYLESS_INQUIRY_EXCEEDED(ValidationResultCode.TRY_LATER),
    ERROR_KEYLESS_SECURITY_VIOLATION(ValidationResultCode.TRY_LATER),
    ERROR_KEYLESS_SUPPRESSED(ValidationResultCode.TRY_LATER),
    ERROR_KEYLESS_THRESH(ValidationResultCode.TRY_LATER),
    ERROR_NOT_WINNER(ValidationResultCode.SUCCESSFULLY_ENTERED),
    ERROR_NO_CODE(ValidationResultCode.TRY_LATER),
    ERROR_NO_CODE41(ValidationResultCode.TRY_LATER),
    ERROR_NO_MEMORY_UPDATED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PACK_NOT_ACT(ValidationResultCode.TRY_LATER),
    ERROR_PACK_NOT_CONF(ValidationResultCode.TRY_LATER),
    ERROR_PAY_CONFIRM_REQUIRED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PINLESS_EPP_INQUIRY_DISABLED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PIN_REQUIRED(ValidationResultCode.TRY_LATER),
    ERROR_PIVC_TICKET_FAIL(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PREV_CLAIMED_OTHER(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PREV_CLAIMED_YOU(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PREV_PAID_OTHER(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_PREV_PAID_YOU(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_RECORD_NOT_FOUND(ValidationResultCode.TRY_LATER),
    ERROR_REDEMPTION_DATE_EXCEEDED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_REJECTED_GAME_CLOSED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_SECURITY_CODE_REQUIRED(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_SECURITY_GAME_TICKET(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_STOLEN_CLAIM_CENTER(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_TERMINAL_THRESH(ValidationResultCode.TRY_LATER),
    ERROR_TICKET_UNSOLD(ValidationResultCode.INELIGIBLE_TICKET),
    ERROR_TIER_NOT_KEYLESS(ValidationResultCode.TRY_LATER),
    ERROR_UNKNOWN(ValidationResultCode.TRY_LATER),
    ERROR_WINNING_TICKET_ON_HOLD(ValidationResultCode.INELIGIBLE_TICKET),
    OK_APPROVED_NORMAL(ValidationResultCode.INELIGIBLE_TICKET),
    OK_CASHING_LIMIT_EXCEEDED(ValidationResultCode.INELIGIBLE_TICKET),
    OK_CLAIM_AT_LOTTERY(ValidationResultCode.INELIGIBLE_TICKET),
    OK_CLAIM_FORM(ValidationResultCode.INELIGIBLE_TICKET),
    OK_FILE_CLAIM_AT_LOTTERY(ValidationResultCode.INELIGIBLE_TICKET),
    OK_PACK_NOT_ACTIVE(ValidationResultCode.TRY_LATER),
    OK_PAY(ValidationResultCode.INELIGIBLE_TICKET),
    OK_PIN_LOCK(ValidationResultCode.TRY_LATER);;

    public static final String INELIGIBLE_TICKET = "Your ticket is not eligible for this promotion.";
    public static final String SUCCESSFULLY_ENTERED = "You are successfully entered into the %s.";
    public static final String CONTACT_CUSTOMER_SUPPORT = "Call our Customer Service Support Line at 1-850-487-7787.";
    public static final String TRY_LATER = "Please try your ticket again later.";
    private final String description;

    private ValidationResultCode(String description) {
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
