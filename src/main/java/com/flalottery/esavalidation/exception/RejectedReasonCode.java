package com.flalottery.esavalidation.exception;

public enum RejectedReasonCode {

    ALREADY_CLAIMED(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    ALREADY_PAID(RejectedReasonCode.INELIGIBLE_TICKET),
    BAD_CHECKSUM(RejectedReasonCode.INELIGIBLE_TICKET),
    CALL_HOTLINE(RejectedReasonCode.CONTACT_CUSTOMER_SUPPORT),
    CANNOT_PAY_YET(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    CLAIMED_BY_OTHER(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    CLAIMED_BY_YOU(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    CONTACT_LOTTERY(RejectedReasonCode.CONTACT_CUSTOMER_SUPPORT),
    DRAW_BREAK(RejectedReasonCode.INELIGIBLE_TICKET),
    DRAW_NOT_CLOSED_YET(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    DUPLICATE_TRANSACTION(RejectedReasonCode.INELIGIBLE_TICKET),
    ENCRYPTION_ERROR(RejectedReasonCode.INELIGIBLE_TICKET),
    ERROR_TERMINAL_IN_TRAINING_MODE(RejectedReasonCode.INELIGIBLE_TICKET),
    EXCEEDS_CASHING_LIMIT(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    FINANCIAL_LIMIT_EXCEEDED(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    FREE_TICKET_SEQUENCE_IN_PROGRESS(RejectedReasonCode.INELIGIBLE_TICKET),
    FUNCTION_SUPPRESSED(RejectedReasonCode.TRY_LATER),
    HIGH_WINNER(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    INCORRECT_CLAIM_CARD_SESSION(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    INFORMATION_NOT_AVAILABLE(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_EXTERNAL_CHANNEL_INFO(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_NOT_A_SELLING_AGENT(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_PRODUCT_CODE(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_REVISION(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_TARGET_TERMINAL_ID(RejectedReasonCode.INELIGIBLE_TICKET),
    INVALID_TARGET_TERMINAL_STATUS(RejectedReasonCode.INELIGIBLE_TICKET),
    MAXIMUM_BALANCE_WOULD_BE_EXCEEDED(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    MUST_GET_FREE_TICKET_FIRST(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    NOT_ABLE_TO_PAY(RejectedReasonCode.INELIGIBLE_TICKET),
    NOT_AUTHORIZED(RejectedReasonCode.INELIGIBLE_TICKET),
    NOT_A_WINNER(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    NOT_A_WINNER_YET(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    NOT_CLAIMED_YET(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    NOT_FOUND("NOT_FOUND"), //  don't change it.
    NOT_SIGNED_ON(RejectedReasonCode.INELIGIBLE_TICKET),
    PAID_BY_EFT(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    PAID_BY_OTHER(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    PAID_BY_YOU(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    PAYMENT_DEFERRED(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    REISSUE_IN_PROGRESS(RejectedReasonCode.INELIGIBLE_TICKET),
    REJECTED("REJECTED"),
    REJECT_REASON("TRUE"),//  don't change it.
    REQUEST_REJECTED_SEE_RETAILER(ValidationWinStatus.CONTACT_CUSTOMER_SUPPORT),
    RESULTS_NOT_IN(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    SERVICE_DISABLED(RejectedReasonCode.INELIGIBLE_TICKET),
    SYNTAX_ERROR(RejectedReasonCode.INELIGIBLE_TICKET),
    SYSTEM_NOT_AVAILABLE(RejectedReasonCode.TRY_LATER),
    TICKET_COMPLETELY_EXPIRED(RejectedReasonCode.INELIGIBLE_TICKET),
    TICKET_EXPIRED(RejectedReasonCode.INELIGIBLE_TICKET),
    TICKET_NOT_ON_FILE(RejectedReasonCode.INELIGIBLE_TICKET),
    TICKET_PREVIOUSLY_CANCELLED(RejectedReasonCode.INELIGIBLE_TICKET),
    TICKET_PRICE_MISMATCH(RejectedReasonCode.INELIGIBLE_TICKET),
    TICKET_TOO_OLD(RejectedReasonCode.INELIGIBLE_TICKET),
    TOO_MANY_TRANSACTIONS_IN_SESSION(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    UNABLE_TO_CANCEL_FREE_TICKET(RejectedReasonCode.INELIGIBLE_TICKET),
    UNABLE_TO_CASH_CLAIM_AMT_TOO_BIG_TOO_LOW(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    VALIDATE_MANUALLY(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    VALIDATION_REJECTED_CANCELLED(RejectedReasonCode.SUCCESSFULLY_ENTERED),
    WINNER_AGENT_CANNOT_PAY(RejectedReasonCode.INELIGIBLE_TICKET);

    public static final String INELIGIBLE_TICKET = "Your ticket is not eligible for this promotion.";
    public static final String SUCCESSFULLY_ENTERED = "You are successfully entered into the %s Promotion.";
    public static final String CONTACT_CUSTOMER_SUPPORT = "Call our Customer Service Support Line at 1-850-487-7787.";
    public static final String TRY_LATER = "Please try your ticket again later.";
    private final String description;

    private RejectedReasonCode(String description) {
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
