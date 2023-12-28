package com.challenge.equipmentmaintenance.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String street;
    private String number;
    private String city;
    private String state;
    private String country;

}
