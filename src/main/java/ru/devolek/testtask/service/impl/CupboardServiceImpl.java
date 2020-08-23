package ru.devolek.testtask.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.devolek.testtask.dto.request.AddCupboardRequest;
import ru.devolek.testtask.dto.response.CupboardDto;
import ru.devolek.testtask.dto.response.ResponseFactory;
import ru.devolek.testtask.model.Cupboard;
import ru.devolek.testtask.repository.CupboardRepository;
import ru.devolek.testtask.repository.InventoryRepository;
import ru.devolek.testtask.service.CupboardService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CupboardServiceImpl implements CupboardService {
    private final CupboardRepository cupboardRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public List<CupboardDto> getList() {
        return ResponseFactory.getCupboardList(cupboardRepository.findAll());
    }

    @Override
    public boolean addCupboard(AddCupboardRequest request) {
        if (!cupboardRepository.existsByName(request.getName())) {
            Cupboard newCupboard = new Cupboard();
            newCupboard.setName(request.getName());
            cupboardRepository.save(newCupboard);
            return true;
        }
        return false;
    }

    @Override
    public boolean editCupboard(int cupboardId, String cupboardName) {
        Optional<Cupboard> optional = cupboardRepository.findById(cupboardId);
        if (optional.isPresent() && !cupboardRepository.existsByName(cupboardName)) {
            Cupboard cupboard = optional.get();
            cupboard.setName(cupboardName);
            cupboardRepository.save(cupboard);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCupboard(int cupboardId) {
        Optional<Cupboard> optional = cupboardRepository.findById(cupboardId);
        if (optional.isPresent()) {
            Cupboard cupboard = optional.get();
            cupboard.getInventories().forEach(inventory -> {
                inventory.setCupboard(null);
                inventoryRepository.save(inventory);
            });
            cupboardRepository.delete(cupboard);
            return true;
        }
        return false;
    }
}
