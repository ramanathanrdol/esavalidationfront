package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Wagers {

    private String drawId;
    private long duration;
    private String gameName;
    private long price;
    private Boards[] boards;
    private String addonPlayed;
}
