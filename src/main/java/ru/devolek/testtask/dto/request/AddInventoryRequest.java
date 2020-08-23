package ru.devolek.testtask.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddInventoryRequest {
    private String name;
}
