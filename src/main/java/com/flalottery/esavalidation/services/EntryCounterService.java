package com.flalottery.esavalidation.services;

import com.flalottery.esavalidation.model.TicketDetail;

public interface EntryCounterService {
    boolean checkMaxEntry(TicketDetail ticketDetail);
}
