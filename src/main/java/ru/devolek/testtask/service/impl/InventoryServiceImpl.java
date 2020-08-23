package ru.devolek.testtask.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.devolek.testtask.dto.request.AddInventoryInCupboardRequest;
import ru.devolek.testtask.dto.request.AddInventoryRequest;
import ru.devolek.testtask.dto.response.InventoryDto;
import ru.devolek.testtask.dto.response.ResponseFactory;
import ru.devolek.testtask.model.Cupboard;
import ru.devolek.testtask.model.Inventory;
import ru.devolek.testtask.repository.CupboardRepository;
import ru.devolek.testtask.repository.InventoryRepository;
import ru.devolek.testtask.service.InventoryService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final CupboardRepository cupboardRepository;

    @Override
    public List<InventoryDto> getList() {
        return ResponseFactory.getInventoriesDtoList(inventoryRepository.findAll());
    }

    @Override
    public List<InventoryDto> getUnusedList() {
        return ResponseFactory.getInventoriesDtoList(inventoryRepository.getUnusedInventories());
    }


    @Override
    public boolean addInventory(AddInventoryRequest request) {
        if (!inventoryRepository.existsByName(request.getName())) {
            Inventory newInventory = new Inventory();
            newInventory.setName(request.getName());
            inventoryRepository.save(newInventory);
            return true;
        }
        return false;
    }

    @Override
    public boolean editInventory(int inventoryId, String inventoryName) {
        Optional<Inventory> optional = inventoryRepository.findById(inventoryId);
        if (optional.isPresent() && !inventoryRepository.existsByName(inventoryName)) {
            Inventory inventory = optional.get();
            inventory.setName(inventoryName);
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInventory(int inventoryId) {
        Optional<Inventory> optional = inventoryRepository.findById(inventoryId);
        if (optional.isPresent()) {
            inventoryRepository.delete(optional.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean addInventoryInCupboard(AddInventoryInCupboardRequest request) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(request.getInventoryId());
        Optional<Cupboard> optionalCupboard = cupboardRepository.findById(request.getCupboardId());
        if (optionalCupboard.isPresent() && optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            inventory.setCupboard(optionalCupboard.get());
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInventoryInCupboard(int inventoryId) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            inventory.setCupboard(null);
            inventoryRepository.save(inventory);
            return true;
        }
        return false;
    }
}
