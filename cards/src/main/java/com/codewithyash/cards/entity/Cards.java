package com.codewithyash.cards.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Cards")
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Cards extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardId;
    private String mobileNumber;
    private String cardNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;

}
