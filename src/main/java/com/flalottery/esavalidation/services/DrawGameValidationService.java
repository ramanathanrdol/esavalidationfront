package com.flalottery.esavalidation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.component.AppComponent;
import com.flalottery.esavalidation.exception.RejectedReasonCode;
import com.flalottery.esavalidation.exception.ValidationWinStatus;
import com.flalottery.esavalidation.model.ConnectionDetail;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.DrawTransactionLookupResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.repository.EntryCountRepository;
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
public class DrawGameValidationService implements TicketValidationService {

    final Logger logger = LoggerFactory.getLogger(DrawGameValidationService.class);
    final Gson gson = new Gson();
    @Autowired
    Environment env;
    @Autowired
    EntryCountRepository entryCountRepo;
    @Autowired
    ConnectionService connectionService;
    DrawTransactionLookupResponse drawTransactionLookupResponse = new DrawTransactionLookupResponse();
    String status;
    @Autowired
    AppComponent appComponent;

    ConnectionDetail connectionDetail = new ConnectionDetail();
    long ticketPrice = 0;

    @Override
    public TicketDetail buildJsonRequest(TicketDetail ticketDetail) {
        ticketDetail.setJsonRequest("{ \"id\": \"" + this.trimZero(ticketDetail.getTicketNumber()) + "\"}");
        return ticketDetail;
    }

    @Override
    public DolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException {
        StringBuilder responseMessage = new StringBuilder();
        DolResponse dolResponse = new DolResponse();

        // To validate F5 Ticket or Not
        if (!appComponent.isValidProductCode(ticketDetail.getTicketNumber(), ticketDetail.getPromotionPropertiesDetail().getProduct().getProductCode())) {
            responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getProduct().getInvalidProductErrorMsg());
            dolResponse.setResponseMessage(responseMessage);
            dolResponse.setResponseStatus(ValidationWinStatus.INVALID.getDescription());
            return dolResponse;
        }
        connectionDetail.setConnectionURI(ticketDetail.getPromotionPropertiesDetail().getEsaBonusPlayURI());
        connectionDetail.setServiceURL(ticketDetail.getPromotionPropertiesDetail().getDrawURL());
        connectionDetail.setAccessToken(ticketDetail.getPromotionPropertiesDetail().getDrawAPIKeyName());
        connectionDetail.setAccessKey(ticketDetail.getPromotionPropertiesDetail().getDrawAPIKeyValue());


        logger.info("Draw Ticket No " + ticketDetail.getJsonRequest());

        ResponseEntity<String> esaResponse = null;
        try {

            esaResponse = connectionService.getESAResponse(connectionDetail, ticketDetail);
        } catch (final HttpClientErrorException | HttpServerErrorException esaResponseException) {
            // To Handle BAD_REQUEST from ESA - call customer support is passed.
        }
        //logger.info(esaResponse.getBody() + " " + drawTransactionLookupResponse.getCode());
        if (esaResponse != null) {
            drawTransactionLookupResponse = gson.fromJson(esaResponse.getBody(), DrawTransactionLookupResponse.class);
            logger.info(esaResponse.getBody() + " " + drawTransactionLookupResponse.getCode());
            if (drawTransactionLookupResponse.getCode() != null) {
                responseMessage.append(RejectedReasonCode.CONTACT_LOTTERY.getDescription())
                ;
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
                return dolResponse;
            }
            status = drawTransactionLookupResponse.getStatus();
            logger.info(format("Validation Status {0} for Ticket No : {1}", status, ticketDetail.getTicketNumber()));
            if (status != null && (status.equals(RejectedReasonCode.NOT_FOUND.getDescription()) ||
                    status.equals(ValidationWinStatus.REJECTED.getDescription()))) {
                responseMessage
                        .append(RejectedReasonCode.INELIGIBLE_TICKET)
                ;
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
            } else if (status != null && status.equals(ValidationWinStatus.FOUND.getDescription())) {
                responseMessage
                        .append(String.format(RejectedReasonCode.SUCCESSFULLY_ENTERED,ticketDetail.getPromotionName()))
                ;
                if (drawTransactionLookupResponse.getWagers().length > 0 && drawTransactionLookupResponse.getWagers()[0] != null) {
                    if (drawTransactionLookupResponse.getWagers()[0].getPrice() != 0) {
                        ticketPrice = drawTransactionLookupResponse.getWagers()[0].getPrice() / 100;
                        if (drawTransactionLookupResponse.getWagers()[0].getAddonPlayed() != null &&
                                drawTransactionLookupResponse.getWagers()[0].getAddonPlayed().equalsIgnoreCase("TRUE")) {
                            ticketPrice += 1;
                        }
                    }
                } else{
                    logger.info(format("Something went Wrong. Please contact ESA. Ticket No : {0}", ticketDetail.getTicketNumber()));
                    responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getCommonMessages().getCommonMessage());
                    dolResponse.setResponseMessage(responseMessage);
                    dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
                }
                dolResponse.setTicketPrice(String.valueOf(ticketPrice));
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.ACCEPTED.getDescription());
            }
        } else {
            logger.info(format("Something went Wrong. Please contact ESA. Ticket No : {0}", ticketDetail.getTicketNumber()));
            responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getCommonMessages().getCommonMessage());
            dolResponse.setResponseMessage(responseMessage);
            dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        }
        return dolResponse;
    }

    private String trimZero(String ticketNumber) {
        if (!ticketNumber.isEmpty() && ticketNumber.startsWith("0")) {
            ticketNumber = ticketNumber.replaceFirst("^0+(?!$)", "").concat("40");
        }
        return ticketNumber;
    }
}

