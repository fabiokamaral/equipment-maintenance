package com.challenge.equipmentmaintenance.util;

import com.challenge.equipmentmaintenance.model.Address;
import com.challenge.equipmentmaintenance.model.Customer;
import com.challenge.equipmentmaintenance.model.Equipment;
import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.model.Responsible;

public class OrderCreator {

    public static Order createOrderToSave() {
        return Order.builder()
                .customer(Customer.builder()
                        .name("Name")
                        .phone("999999999")
                        .email("email@email.com")
                        .address(Address.builder()
                                .street("street")
                                .number("S/N")
                                .city("city")
                                .state("state")
                                .country("country")
                                .build())
                        .build())
                .responsible(Responsible.builder()
                        .name("Responsible Name")
                        .registration("MT1234")
                        .build())
                .equipment(Equipment.builder()
                        .type("type")
                        .brand("brand")
                        .description("description")
                        .build())
                .build();
    }

    public static Order createOrderWithOrderIdToSave() {
        Order order = createOrderToSave();
        order.setOrderId(1000L);
        return order;
    }

    public static Order createSavedOrder() {
        Order order = createOrderToSave();
        order.setOrderId(1000L);
        order.setId("id");
        return order;
    }

}
