package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConnectionDetail {

    private String connectionURI;
    private String serviceURL;
    private String accessToken;
    private String accessKey;
    private String methodKey;
    private String methodValue;

}
