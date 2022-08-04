package com.flalottery.esavalidation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.component.BonusPlayESAClientComponent;
import com.flalottery.esavalidation.component.WebsiteESAClientComponent;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.QuickTicketDolResponse;
import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.services.ESAPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Map;

@RestController
@Configuration
@CrossOrigin("*")
@PropertySource(value = "file:${ESAPROPLOC}/validation.properties", ignoreResourceNotFound = true)
public class ESAClientController {

    final Logger logger = LoggerFactory.getLogger(ESAClientController.class);
    @Autowired
    BonusPlayESAClientComponent bonusPlayESAClientComponent;

    @Autowired
    WebsiteESAClientComponent websiteESAClientComponent;
    @Autowired
    ESAPropertyService esaPropertyService;
    @Value("#{${esa.validation.gamenumber.promotion.map}}")
    private Map<String, String> gameNumberPromoMap;
    @Value("#{${esa.validation.gamenumber.promotion.message.map}}")
    private Map<String, String> gameNbrPromoMsgMap;

    @CrossOrigin("*")
    @PostMapping(value = "/validateTicket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DolResponse> validateTicket(@RequestBody TicketDetail ticketDetail) {
        DolResponse dolResponse = new DolResponse();
        try {
            logger.info("Game Number: " + ticketDetail.getGameNumber() + " " + gameNumberPromoMap.containsKey(ticketDetail.getGameNumber()));
            if(gameNumberPromoMap.containsKey(ticketDetail.getGameNumber())){
                ticketDetail.setPromotionName(gameNbrPromoMsgMap.get(ticketDetail.getGameNumber()));
                logger.info("###### Promotion Name " + ticketDetail.getPromotionName() + " Ticket No "+ ticketDetail.getTicketNumber());
                ticketDetail.setPromotionPropertiesDetail(esaPropertyService.getPromotionPropertiesDetail(gameNumberPromoMap.get(ticketDetail.getGameNumber())));
                dolResponse = bonusPlayESAClientComponent.validateTicket(ticketDetail);
                logger.info(MessageFormat.format("Response Status : {0}", dolResponse.getResponseStatus()));
            } else {
                dolResponse.setResponseMessage(new StringBuilder("Invalid Game Number."));
                dolResponse.setResponseStatus("INVALID_GAME_NUMBER");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(dolResponse);
    }
    @CrossOrigin("*")
    @PostMapping(value = "/validateQuickTicket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuickTicketDolResponse> validateQuickTicket(@RequestBody TicketDetail ticketDetail) {
        QuickTicketDolResponse dolResponse = new QuickTicketDolResponse();
        try {
            if(gameNumberPromoMap.containsKey(ticketDetail.getPromotionName())){
                //ticketDetail.setPromotionName(gameNbrPromoMsgMap.get(ticketDetail.getPromotionName()));
                ticketDetail.setPromotionName(ticketDetail.getPromotionName());
                logger.info("###### Promotion Name " + ticketDetail.getPromotionName() + " Ticket No "+ ticketDetail.getTicketNumber());
                ticketDetail.setPromotionPropertiesDetail(esaPropertyService.getPromotionPropertiesDetail(gameNumberPromoMap.get(ticketDetail.getPromotionName())));
                dolResponse = websiteESAClientComponent.validateTicket(ticketDetail);
                logger.info(MessageFormat.format("Response Status : {0}", dolResponse.getResponseStatus()));
            } else {
                dolResponse.setResponseMessage(new StringBuilder("Invalid Game Number."));
                dolResponse.setResponseStatus("INVALID_GAME_NUMBER");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(dolResponse);
    }
}
