package com.challenge.equipmentmaintenance.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Equipment {

    private String type;
    private String brand;
    private String description;

}
