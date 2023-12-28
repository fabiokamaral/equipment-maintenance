package com.challenge.equipmentmaintenance.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsibleRequest {

    @NotNull(message = "responsible.name cannot be null")
    @Schema(description = "Nome do responsável pela Ordem de Serviço", example = "Pedro Lopes")
    private String name;

    @NotNull(message = "responsible.registration cannot be null")
    @Schema(description = "Matricula do responsável pela Ordem de Serviço", example = "MT1234")
    private String registration;

}
