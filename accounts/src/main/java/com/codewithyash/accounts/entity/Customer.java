package com.codewithyash.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Table(name = "Customer")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_id")
//    Since we have the variable name matching with that of Column so we can skip @Column
    private Long customerId;

    private String name;

    private String email;

    private String mobileNumber;

}
