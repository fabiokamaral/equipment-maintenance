package com.challenge.equipmentmaintenance.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentRequest {

    @NotNull(message = "equipment.type cannot be null")
    @Schema(description = "Tipo do equipamento", example = "Impressora")
    private String type;

    @NotNull(message = "equipment.brand cannot be null")
    @Schema(description = "Marca do equipamento", example = "Epson")
    private String brand;

    @NotNull(message = "equipment.description cannot be null")
    @Schema(description = "Descrição do problema do equipamento", example = "Falha durante a impressão")
    private String description;

}
