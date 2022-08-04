package com.flalottery.esavalidation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.exception.RejectedReasonCode;
import com.flalottery.esavalidation.exception.ValidationWinStatus;
import com.flalottery.esavalidation.model.ConnectionDetail;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.ScratchInquireResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.repository.EntryCountRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static java.text.MessageFormat.format;

@Service
public class InstantInterleavedValidationService implements TicketValidationService {

    final Logger logger = LoggerFactory.getLogger(InstantInterleavedValidationService.class);
    final Gson gson = new Gson();
    @Autowired
    Environment env;
    @Autowired
    EntryCountRepository entryCountRepo;
    @Autowired
    ConnectionService connectionService;
    ScratchInquireResponse scratchInquireResponse = new ScratchInquireResponse();
    String status;
    private ConnectionDetail connectionDetail = new ConnectionDetail();

    @Override
    public TicketDetail buildJsonRequest(TicketDetail ticketDetail) {
        ticketDetail.setJsonRequest("{ \"barcode\": \"" + ticketDetail.getTicketNumber() + "\",\"pin\": \"" + ticketDetail.getPin() + "\" }");
        logger.info(ticketDetail.getJsonRequest());
        return ticketDetail;
    }

    @Override
    public DolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException {
        StringBuilder responseMessage = new StringBuilder();
        DolResponse dolResponse = new DolResponse();

        connectionDetail.setConnectionURI(ticketDetail.getPromotionPropertiesDetail().getEsaBonusPlayURI());
        connectionDetail.setServiceURL(ticketDetail.getPromotionPropertiesDetail().getScratchOffURL());
        connectionDetail.setAccessToken(ticketDetail.getPromotionPropertiesDetail().getScratchOffAPIKeyName());
        connectionDetail.setAccessKey(ticketDetail.getPromotionPropertiesDetail().getScratchOffAPIKeyValue());
        String manualAsValue = "Manual";
logger.info("Manul Value :::" + manualAsValue + " :: " + ticketDetail.getPromotionPropertiesDetail().getScratchOffMethodManual());
        if ((ticketDetail.getMethod() != null) &&
                (!ticketDetail.getMethod().isEmpty()) &&
                ticketDetail.getMethod().equalsIgnoreCase(manualAsValue)) {
            connectionDetail.setMethodKey(ticketDetail.getPromotionPropertiesDetail().getScratchOffMethodName());
            connectionDetail.setMethodValue(manualAsValue);
        }
        ResponseEntity<String> esaResponse = connectionService.getESAResponse(connectionDetail, ticketDetail);
        logger.info(format("Response ::: {0}", esaResponse.getBody()));
        if (esaResponse != null) {
            scratchInquireResponse = gson.fromJson(esaResponse.getBody(), ScratchInquireResponse.class);
            logger.info(esaResponse.getBody() + " " + scratchInquireResponse.getCode());
            if (scratchInquireResponse.getCode() != null) {
                responseMessage.append(RejectedReasonCode.CONTACT_LOTTERY.getDescription())
                ;
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
                return dolResponse;
            }
            status = scratchInquireResponse.getWinStatus();

            if (scratchInquireResponse != null && (!scratchInquireResponse.getRejectReason().isEmpty())) {
                if ((!status.isEmpty())) {
                    if (status.equalsIgnoreCase(ValidationWinStatus.SCOFF_NOT_A_WINNER.getDescription())) {
                        /* Scratch off NOT A Winner */
                        responseMessage
                                .append(String.format(ValidationWinStatus.valueOf(status).getDescription(),ticketDetail.getPromotionName()))
                        ;
                        dolResponse.setResponseMessage(responseMessage);
                        dolResponse.setResponseStatus(ValidationWinStatus.ACCEPTED.getDescription());
                        return dolResponse;

                    } else if (status.equalsIgnoreCase(ValidationWinStatus.SCOFF_WINNER.getDescription())) {/* Scratch off Winner */
                        responseMessage
                                .append(ValidationWinStatus.valueOf(status).getDescription())
                        ;
                        dolResponse.setResponseMessage(responseMessage);
                        dolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
                        return dolResponse;
                    }
                }
                if (scratchInquireResponse.getRejectReason().toUpperCase(Locale.ROOT).contains(RejectedReasonCode.REJECT_REASON.getDescription())) {
                    responseMessage
                            .append(RejectedReasonCode.valueOf(status).getDescription())
                    ;
                    dolResponse.setResponseMessage(responseMessage);
                    dolResponse.setResponseStatus(ValidationWinStatus.REJECTED.getDescription());
                } else if (scratchInquireResponse.getRejectReason().toUpperCase(Locale.ROOT).contains(ValidationWinStatus.REJECT_REASON.getDescription())) {
                    responseMessage
                            .append(ValidationWinStatus.valueOf(status).getDescription())
                    ;
                    dolResponse.setResponseMessage(responseMessage);
                    dolResponse.setResponseStatus(ValidationWinStatus.ACCEPTED.getDescription());
                }
            } else {
                logger.info(format("Something went Wrong. Please contact ESA. Ticket N0 : {0}", ticketDetail.getTicketNumber()));
                responseMessage.append(env.getProperty("esa.validation.error.message.common"));
                dolResponse.setResponseMessage(responseMessage);
                dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
            }
        } else {
            logger.info(format("Something went Wrong. Please contact ESA. Ticket N0 : {0}", ticketDetail.getTicketNumber()));
            responseMessage.append(env.getProperty("esa.validation.error.message.common"));
            dolResponse.setResponseMessage(responseMessage);
            dolResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        }
        return dolResponse;
    }

}
