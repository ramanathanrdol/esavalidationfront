package com.flalottery.esavalidation.services;


import com.flalottery.esavalidation.model.TicketDetail;
import com.flalottery.esavalidation.repository.EntryCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EntryCountServiceImpl implements EntryCounterService {
    final Logger logger = LoggerFactory.getLogger(EntryCountServiceImpl.class);
    @Autowired
    EntryCountRepository entryCountRepo;

    @Override
    public boolean checkMaxEntry(TicketDetail ticketDetail) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        long count = entryCountRepo.countByPlayerId(Integer.parseInt(ticketDetail.getPlayerId()), ticketDetail.getGameNumber(), dateFormat.format(date));
        return count < ticketDetail.getMaxCountEntries();
    }

}
