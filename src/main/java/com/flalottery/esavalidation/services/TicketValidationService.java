package com.flalottery.esavalidation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flalottery.esavalidation.model.DolResponse;
import com.flalottery.esavalidation.model.TicketDetail;

public interface TicketValidationService {


    TicketDetail buildJsonRequest(TicketDetail ticketDetail);

    DolResponse validateTicket(TicketDetail ticketDetail) throws JsonProcessingException;

}
