package com.challenge.equipmentmaintenance.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private String name;
    private String phone;
    private String email;
    private Address address;

}
