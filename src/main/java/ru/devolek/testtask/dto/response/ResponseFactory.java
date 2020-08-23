package ru.devolek.testtask.dto.response;

import ru.devolek.testtask.model.Cupboard;
import ru.devolek.testtask.model.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseFactory {
    public static List<CupboardDto> getCupboardList(List<Cupboard> cupboards){
        if (cupboards.isEmpty()){
            return new ArrayList<>();
        }
        return cupboards.stream().map(ResponseFactory::cupboardToDto).collect(Collectors.toList());
    }
    public static CupboardDto cupboardToDto(Cupboard cupboard){
        return new CupboardDto(cupboard.getId(), cupboard.getName(), getInventoriesDtoList(cupboard.getInventories()));
    }
    public static List<InventoryDto> getInventoriesDtoList(List<Inventory> inventories){
        if (inventories.isEmpty()){
            return new ArrayList<>();
        }
        return inventories.stream().map(ResponseFactory::inventoryToDto).collect(Collectors.toList());
    }
    public static InventoryDto inventoryToDto(Inventory inventory){
        return new InventoryDto(inventory.getId(),
                inventory.getName(),
                inventory.getCupboard() == null ? null : inventory.getCupboard().getId(),
                inventory.getCupboard() == null ? null : inventory.getCupboard().getName());
    }
}
