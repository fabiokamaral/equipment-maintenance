package com.challenge.equipmentmaintenance.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDetails {

    private String title;
    private int status;
    private String details;
    private String message;
    private LocalDateTime date;

}
