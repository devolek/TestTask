package ru.devolek.testtask.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InventoryDto {
    private int id;
    private String name;
    @JsonProperty("cupboard_id")
    private Integer cupboardId;
    @JsonProperty("cupboard_name")
    private String cupboardName;
}
