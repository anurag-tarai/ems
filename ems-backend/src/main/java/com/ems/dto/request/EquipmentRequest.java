package com.ems.dto.request;

import com.ems.enums.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipmentRequest {

    @NotBlank(message = "Equipment name is required")
    @Size(max = 150, message = "Name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "Equipment type is required")
    @Size(max = 100, message = "Type must not exceed 100 characters")
    private String type;

    @NotNull(message = "Status is required")
    private EquipmentStatus status;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;
}
