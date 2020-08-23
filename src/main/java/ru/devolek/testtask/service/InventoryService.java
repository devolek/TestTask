package ru.devolek.testtask.service;

import org.springframework.ui.Model;
import ru.devolek.testtask.dto.request.AddInventoryInCupboardRequest;
import ru.devolek.testtask.dto.request.AddInventoryRequest;
import ru.devolek.testtask.dto.response.InventoryDto;
import ru.devolek.testtask.model.Inventory;

import java.util.List;

public interface InventoryService {
    List<InventoryDto> getList();

    List<InventoryDto> getUnusedList();

    boolean addInventory(AddInventoryRequest request);

    boolean editInventory(int inventoryId, String inventoryName);

    boolean deleteInventory(int inventoryId);

    boolean addInventoryInCupboard(AddInventoryInCupboardRequest request);

    boolean deleteInventoryInCupboard(int inventoryId);
}
