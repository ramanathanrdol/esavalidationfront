package com.flalottery.esavalidation.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.exception.ValidationWinStatus;
import com.flalottery.esavalidation.model.CommonMessages;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.QuickTicketDolResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

@Component
public class WebsiteESAClientComponent {
    final Logger logger = LoggerFactory.getLogger(WebsiteESAClientComponent.class);
    @Autowired
    AppComponent appComponent;
    @Autowired
    EntryCountServiceImpl entryCountService;
    @Autowired
    DrawGameValidationService drawGameValidationService;
    @Autowired
    InstantInterleavedValidationService instantInterleavedValidationService;
    @Autowired
    InstantPDF417ValidationService instantPDF417ValidationService;

    @Autowired
    QuickTicketValidationService quickTicketValidationService;
    @Autowired(required = false)
    CommonMessages commonMessages;

    QuickTicketDolResponse quickTicketDOLResponse = new QuickTicketDolResponse();
    StringBuilder responseMessage;

    public QuickTicketDolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException {
        commonMessages = ticketDetail.getPromotionPropertiesDetail().getCommonMessages();
        responseMessage = new StringBuilder();
        if ((ticketDetail != null) && (!ticketDetail.getTicketNumber().isEmpty())) {
                return this.identifyAndValidate(ticketDetail);
        }

        //responseMessage.append(commonMessages.getCommonMessage());
        responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getProduct().getInvalidProductErrorMsg());
        quickTicketDOLResponse.setResponseMessage(responseMessage);
        quickTicketDOLResponse.setRejectReason(ValidationWinStatus.INVALID.getDescription());
        quickTicketDOLResponse.setResponseStatus(ValidationWinStatus.INVALID.getDescription());
        return quickTicketDOLResponse;
    }

    public QuickTicketDolResponse identifyAndValidate(TicketDetail ticketDetail) throws JsonProcessingException {

        if ((ticketDetail != null) && (!ticketDetail.getTicketNumber().isEmpty())) {
            responseMessage = new StringBuilder();
            String ticketNo = ticketDetail.getTicketNumber();
            String pin = ticketDetail.getPin();
            logger.info(format("Ticket No : {0}", ticketNo));
              Matcher quickTicketMatcher = Pattern.compile(Objects.requireNonNull(ticketDetail.getPromotionPropertiesDetail().getTicketFormats().getQuickTicketFormat())).matcher(ticketNo);

            logger.info(format("Ticket No : {0}", ticketNo));

            if (quickTicketMatcher.matches()) {
                return quickTicketValidationService.validateTicket(quickTicketValidationService.buildJsonRequest(ticketDetail));
            } else {
                //responseMessage.append(commonMessages.getInvalidTicketMessage());
                responseMessage.append(ticketDetail.getPromotionPropertiesDetail().getProduct().getInvalidProductErrorMsg());
                logger.info("Response Message =======" + responseMessage);
                quickTicketDOLResponse.setResponseMessage(responseMessage);
                quickTicketDOLResponse.setRejectReason(ValidationWinStatus.INVALID.getDescription());
                quickTicketDOLResponse.setResponseStatus(ValidationWinStatus.INVALID.getDescription());
                return quickTicketDOLResponse;
            }
        }
        responseMessage = new StringBuilder();
        //responseMessage.append(env.getProperty("esa.validation.error.message.common"));
        responseMessage.append(commonMessages.getCommonMessage());
        quickTicketDOLResponse.setResponseMessage(responseMessage);
        quickTicketDOLResponse.setResponseStatus(ValidationWinStatus.ERROR_UNKNOWN.getDescription());
        return quickTicketDOLResponse;
    }
}
