package com.challenge.equipmentmaintenance.requests;

import com.challenge.equipmentmaintenance.model.Address;
import com.challenge.equipmentmaintenance.model.Customer;
import com.challenge.equipmentmaintenance.model.Equipment;
import com.challenge.equipmentmaintenance.model.Order;
import com.challenge.equipmentmaintenance.model.Responsible;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostRequestBody {

    @NotNull(message = "customer cannot be null")
    @Schema(description = "Cliente da Ordem de Serviço")
    private CustomerRequest customer;

    @NotNull(message = "responsible cannot be null")
    @Schema(description = "Funcionário responsável pela Ordem de Serviço")
    private ResponsibleRequest responsible;

    @NotNull(message = "equipment cannot be null")
    @Schema(description = "Equipamento em que será feita a manutenção")
    private EquipmentRequest equipment;

    public Order toModel() {
        return Order.builder()
                .customer(Customer.builder()
                        .name(customer.getName())
                        .phone(customer.getPhone())
                        .email(customer.getEmail())
                        .address(Address.builder()
                                .street(customer.getAddress().getStreet())
                                .number(customer.getAddress().getNumber())
                                .city(customer.getAddress().getCity())
                                .state(customer.getAddress().getState())
                                .country(customer.getAddress().getCountry())
                                .build())
                        .build())
                .responsible(Responsible.builder()
                        .name(responsible.getName())
                        .registration(responsible.getRegistration())
                        .build())
                .equipment(Equipment.builder()
                        .type(equipment.getType())
                        .brand(equipment.getBrand())
                        .description(equipment.getDescription())
                        .build())
                .build();
    }

}
