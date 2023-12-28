package com.challenge.equipmentmaintenance.requests;

import com.challenge.equipmentmaintenance.model.History;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPostRequestBody {

    @NotNull(message = "orderId cannot be null")
    @Schema(description = "Id da Order de Serviço", example = "1000")
    private Long orderId;

    @NotNull(message = "description cannot be null")
    @Schema(description = "Detalhes sobre o que aconteceu durante a manutenção", example = "Aguardando peça da distribuidora")
    private String description;


    public History toModel() {
        return History.builder()
                .date(LocalDateTime.now())
                .description(description)
                .build();
    }

}
