package com.challenge.equipmentmaintenance.util;

import com.challenge.equipmentmaintenance.requests.AddressRequest;
import com.challenge.equipmentmaintenance.requests.CustomerRequest;
import com.challenge.equipmentmaintenance.requests.EquipmentRequest;
import com.challenge.equipmentmaintenance.requests.OrderPostRequestBody;
import com.challenge.equipmentmaintenance.requests.ResponsibleRequest;

public class OrderPostRequestBodyCreator {

    public static OrderPostRequestBody create() {
        return OrderPostRequestBody.builder()
                .customer(CustomerRequest.builder()
                        .name("name")
                        .phone("999999999")
                        .email("email@email.com")
                        .address(AddressRequest.builder()
                                .street("street")
                                .number("S/N")
                                .city("city")
                                .state("state")
                                .country("country")
                                .build())
                        .build())
                .responsible(ResponsibleRequest.builder()
                        .name("Responsible Name")
                        .registration("MT1234")
                        .build())
                .equipment(EquipmentRequest.builder()
                        .type("type")
                        .brand("brand")
                        .description("description")
                        .build())
                .build();
    }

}
