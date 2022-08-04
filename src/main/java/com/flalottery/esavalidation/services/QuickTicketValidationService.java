package com.flalottery.esavalidation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.component.AppComponent;
import com.flalottery.esavalidation.exception.RejectedReasonCode;
import com.flalottery.esavalidation.exception.ValidationWinStatus;
import com.flalottery.esavalidation.model.ConnectionDetail;
import com.flalottery.esavalidation.model.QuickTicketDolResponse;
import com.flalottery.esavalidation.model.QuickTicketTransactionLookupResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import static java.text.MessageFormat.format;

@Service
public class QuickTicketValidationService implements TicketValidationService {

    final Logger logger = LoggerFactory.getLogger(QuickTicketValidationService.class);
    final Gson gson = new Gson();
    @Autowired
    Environment env;
    @Autowired
    ConnectionService connectionService;
    QuickTicketTransactionLookupResponse quickTicketTransactionLookupResponse = new QuickTicketTransactionLookupResponse();
    String status;
    @Autowired
    AppComponent appComponent;

    ConnectionDetail connectionDetail = new ConnectionDetail();
    long ticketPrice = 0;

    @Override
    public TicketDetail buildJsonRequest(TicketDetail ticketDetail) {
        ticketDetail.setJsonRequest("{ \"barcode\": \"" + ticketDetail.getTicketNumber() + "\"}");
        logger.info(ticketDetail.getJsonRequest());
        return ticketDetail;
    }

    @Override
    public QuickTicketDolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException {
        StringBuilder responseMessage = new StringBuilder();
        QuickTicketDolResponse quickTicketDolResponse = new QuickTicketDolResponse();

        connectionDetail.setConnectionURI(ticketDetail.getPromotionPropertiesDetail().getEsaWebsiteURI());
        connectionDetail.setServiceURL(ticketDetail.getPromotionPropertiesDetail().getQuickTicketURL());
        connectionDetail.setAccessToken(ticketDetail.getPromotionPropertiesDetail().getQuickTicketAPIKeyName());
        connectionDetail.setAccessKey(ticketDetail.getPromotionPropertiesDetail().getQuickTicketAPIKeyValue());

        String manualAsValue = "Manual";
        logger.info("Manual Value :::" + manualAsValue + " :: " + ticketDetail.getPromotionPropertiesDetail().getScratchOffMethodManual());
        if ((ticketDetail.getMethod() != null) &&
                (!ticketDetail.getMethod().isEmpty()) &&
                ticketDetail.getMethod().equalsIgnoreCase(manualAsValue)) {
            connectionDetail.setMethodKey(ticketDetail.getPromotionPropertiesDetail().getQuickTicketMethodName());
            connectionDetail.setMethodValue(manualAsValue);
        }
        logger.info("Quick Ticket No " + ticketDetail.getJsonRequest());

        ResponseEntity<String> esaResponse = null;
        try {

            esaResponse = connectionService.getESAResponse(connectionDetail, ticketDetail);
        } catch (final HttpClientErrorException | HttpServerErrorException esaResponseException) {
            // To Handle BAD_REQUEST from ESA - call customer support is passed.
        }
        //logger.info(esaResponse.getBody() + " " + drawTransactionLookupResponse.getCode());
        if (esaResponse != null) {
            quickTicketTransactionLookupResponse = gson.fromJson(esaResponse.getBody(), QuickTicketTransactionLookupResponse.class);
            logger.info(esaResponse.getBody() + " " + quickTicketTransactionLookupResponse.getCode());
            if (quickTicketTransactionLookupResponse.getCode() != null) {
                responseMessage.append(RejectedReasonCode.CONTACT_LOTTERY.getDescription())
                ;
                quickTicketDolResponse.setResponseMessage(responseMessage);
                quickTicketDolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
                quickTicketDolResponse.setRejectReason(quickTicketTransactionLookupResponse.getCode());
                return quickTicketDolResponse;
            }
            status = quickTicketTransactionLookupResponse.getStatus();
            logger.info(format("Validation Status {0} for Ticket No : {1}", status, ticketDetail.getTicketNumber()));
            if (status != null && (status.equals(RejectedReasonCode.NOT_FOUND.getDescription()) ||
                    status.equals(ValidationWinStatus.REJECTED.getDescription()))) {
                responseMessage.append(RejectedReasonCode.INELIGIBLE_TICKET);
                quickTicketDolResponse.setGameName(quickTicketTransactionLookupResponse.getGameName());
                quickTicketDolResponse.setRejectReason(quickTicketTransactionLookupResponse.getRejectReason());
                quickTicketDolResponse.setResponseMessage(responseMessage);
                quickTicketDolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
                quickTicketDolResponse.setTicketPrice(appComponent.formatCashAmount(quickTicketTransactionLookupResponse.getCashAmount()));
                if (quickTicketTransactionLookupResponse.getFromWager() != null) {
                    quickTicketDolResponse.setDrawStartDate(appComponent.epochToDate(quickTicketTransactionLookupResponse.getFromWager().getDrawStartDate()));
                    quickTicketDolResponse.setDrawEndDate(appComponent.epochToDate(quickTicketTransactionLookupResponse.getFromWager().getDrawEndDate()));
                }
            } else if (status != null && (status.equals(ValidationWinStatus.FOUND.getDescription())) ||
                    status.equals(ValidationWinStatus.ACCEPTED.getDescription())) {
                responseMessage.append(String.format(RejectedReasonCode.SUCCESSFULLY_ENTERED, ticketDetail.getPromotionPropertiesDetail().getProduct().getProductName()));
                quickTicketDolResponse.setGameName(quickTicketTransactionLookupResponse.getGameName());
                quickTicketDolResponse.setRejectReason(quickTicketTransactionLookupResponse.getRejectReason());
                quickTicketDolResponse.setTicketPrice(appComponent.formatCashAmount(quickTicketTransactionLookupResponse.getCashAmount()));
                if (quickTicketTransactionLookupResponse.getFromWager() != null) {
                    quickTicketDolResponse.setDrawStartDate(appComponent.epochToDate(quickTicketTransactionLookupResponse.getFromWager().getDrawStartDate()));
                    quickTicketDolResponse.setDrawEndDate(appComponent.epochToDate(quickTicketTransactionLookupResponse.getFromWager().getDrawEndDate()));
                }
                quickTicketDolResponse.setResponseMessage(responseMessage);
                quickTicketDolResponse.setResponseStatus(ValidationWinStatus.ACCEPTED.getDescription());
            } else {
                logger.info(format("Something went Wrong. Please contact ESA. Ticket No : {0}", ticketDetail.getTicketNumber()));
                responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getCommonMessages().getCommonMessage());
                quickTicketDolResponse.setResponseMessage(responseMessage);
                quickTicketDolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
            }
        } else {
            logger.info(format("Something went Wrong. Please contact ESA. Ticket No : {0}", ticketDetail.getTicketNumber()));
            responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getCommonMessages().getCommonMessage());
            quickTicketDolResponse.setResponseMessage(responseMessage);
            quickTicketDolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        }
        return quickTicketDolResponse;
    }

}

