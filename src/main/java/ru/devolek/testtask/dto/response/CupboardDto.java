package ru.devolek.testtask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CupboardDto {
    private int id;
    private String name;
    private List<InventoryDto> inventories;
}
