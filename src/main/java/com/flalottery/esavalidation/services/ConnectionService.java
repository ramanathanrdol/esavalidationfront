package com.flalottery.esavalidation.services;

import com.flalottery.esavalidation.model.ConnectionDetail;
import com.flalottery.esavalidation.model.TicketDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class ConnectionService {

    final Logger logger = LoggerFactory.getLogger(ConnectionService.class);

    public ResponseEntity<String> getESAResponse(ConnectionDetail connectionDetail, TicketDetail ticketDetail) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder serviceURL = new StringBuilder();

        serviceURL.append(connectionDetail.getConnectionURI()).append(connectionDetail.getServiceURL());
        logger.info("Service URL: " + serviceURL.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(connectionDetail.getAccessToken(), connectionDetail.getAccessKey());
        logger.info("Entry Method :" + connectionDetail.getMethodKey() + connectionDetail.getMethodValue());
        if (connectionDetail.getMethodValue() != null && (!connectionDetail.getMethodValue().isEmpty())) {
            headers.add(connectionDetail.getMethodKey(), connectionDetail.getMethodValue());
        }

        HttpEntity<?> entity = new HttpEntity<Object>(ticketDetail.getJsonRequest(), headers);
        return restTemplate.postForEntity(serviceURL.toString(), entity, String.class);
    }
}
