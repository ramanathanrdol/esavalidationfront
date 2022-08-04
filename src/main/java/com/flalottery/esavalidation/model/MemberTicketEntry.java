package com.flalottery.esavalidation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MEMBER_TICKET_ENTRY")
public class MemberTicketEntry {
    @Id
    @Column(name = "MEMBER_ID_FK")
    private String playerId;
    @Column(name = "TICKET_NUMBER")
    private String ticketNumber;
    @Column(name = "SND_GAME_ID_FK")
    private String gameNumber;
    @Column(name = "CREATE_DATE")
    private Date createdDate;
}
