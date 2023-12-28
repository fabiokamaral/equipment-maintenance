package com.challenge.equipmentmaintenance.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {

    @NotNull(message = "customer.name cannot be null")
    @Schema(description = "Nome do cliente", example = "João Silva")
    private String name;

    @NotNull(message = "customer.phone cannot be null")
    @Schema(description = "Telefone do cliente", example = "(21) 99999-9999")
    private String phone;

    @NotNull(message = "customer.email cannot be null")
    @Email
    @Schema(description = "Email do cliente", example = "joao.silva@email.com")
    private String email;

    @NotNull(message = "customer.address cannot be null")
    @Schema(description = "Endereço do cliente")
    private AddressRequest address;




}
