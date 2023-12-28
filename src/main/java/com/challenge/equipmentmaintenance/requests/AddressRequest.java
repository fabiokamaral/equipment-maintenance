package com.challenge.equipmentmaintenance.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {

    @NotNull(message = "customer.address.street cannot be null")
    @Schema(description = "Nome da Rua", example = "Rua Rui Barbosa")
    private String street;

    @NotNull(message = "customer.address.number cannot be null")
    @Schema(description = "Número", example = "S/N")
    private String number;

    @NotNull(message = "customer.address.city cannot be null")
    @Schema(description = "Nome da Cidade", example = "Rio de Janeiro")
    private String city;

    @NotNull(message = "customer.address.state cannot be null")
    @Schema(description = "Nome do Estado", example = "Rio de Janeiro")
    private String state;

    @NotNull(message = "customer.address.country cannot be null")
    @Schema(description = "Nome do País", example = "Brasil")
    private String country;

}
