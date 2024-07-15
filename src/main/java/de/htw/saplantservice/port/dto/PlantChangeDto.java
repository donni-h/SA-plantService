package de.htw.saplantservice.port.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlantChangeDto {
    private int changeAmount;
    private UUID plantId;
}