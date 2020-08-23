package ru.devolek.testtask.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.devolek.testtask.dto.request.AddInventoryInCupboardRequest;
import ru.devolek.testtask.dto.request.AddInventoryRequest;
import ru.devolek.testtask.service.InventoryService;

@Controller
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/inventories")
    public String getInventories(Model model) {
        model.addAttribute("inventories", inventoryService.getList());
        return "inventory/list";
    }

    @PostMapping("/inventories")
    public String addInventory(AddInventoryRequest request) {
        return inventoryService.addInventory(request) ? "redirect:inventories" : "inventory/add";
    }

    @PutMapping("/inventories")
    public ResponseEntity<?> changeInventory(@RequestParam(value = "name") String inventoryName,
                                             @RequestParam(value = "id") int inventoryId) {
        return inventoryService.editInventory(inventoryId, inventoryName) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable int id) {
        return inventoryService.deleteInventory(id) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @PutMapping("/inventory/2cupboard/add")
    public ResponseEntity<?> addInventoryInCupboard(@RequestParam(value = "cupboard_id") int cupboardId,
                                         @RequestParam(value = "inventory_id") int inventoryId) {
        return inventoryService.addInventoryInCupboard(new AddInventoryInCupboardRequest(cupboardId, inventoryId)) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @PutMapping("/inventory/2cupboard/delete/{id}")
    public ResponseEntity<?> deleteInventoryInCupboard(@PathVariable int id) {
        return inventoryService.deleteInventoryInCupboard(id) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
}
