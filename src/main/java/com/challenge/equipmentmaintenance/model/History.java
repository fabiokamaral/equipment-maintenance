package com.challenge.equipmentmaintenance.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class History {

    private LocalDateTime date;
    private String description;

}
