package ru.devolek.testtask.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.devolek.testtask.dto.request.AddCupboardRequest;
import ru.devolek.testtask.service.CupboardService;
import ru.devolek.testtask.service.InventoryService;

@Controller
@AllArgsConstructor
public class CupboardController {
    private final InventoryService inventoryService;
    private final CupboardService cupboardService;

    @GetMapping("/cupboards")
    public String getCupboards(Model model) {
        model.addAttribute("inventories", inventoryService.getUnusedList());
        model.addAttribute("cupboards", cupboardService.getList());
        return "cupboard/list";
    }

    @PostMapping("/cupboards")
    public String addCupboard(AddCupboardRequest request) {
        return cupboardService.addCupboard(request) ? "redirect:cupboards" : "cupboard/add";
    }

    @PutMapping("/cupboards")
    public ResponseEntity<?> changeCupboard(@RequestParam(value = "name") String cupboardName,
                                            @RequestParam(value = "id") int cupboardId) {
        return cupboardService.editCupboard(cupboardId, cupboardName) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @DeleteMapping("/cupboards/{id}")
    public ResponseEntity<?> deleteCupboard(@PathVariable int id) {
        cupboardService.deleteCupboard(id);
        return cupboardService.deleteCupboard(id) ?
                ResponseEntity.ok("") : ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }
}
