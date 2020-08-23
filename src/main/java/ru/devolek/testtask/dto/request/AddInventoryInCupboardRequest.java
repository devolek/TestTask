package ru.devolek.testtask.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddInventoryInCupboardRequest {

    @JsonProperty("cupboard_id")
    private int cupboardId;
    @JsonProperty("inventory_id")
    private int inventoryId;
}
