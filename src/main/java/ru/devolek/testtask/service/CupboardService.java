package ru.devolek.testtask.service;

import ru.devolek.testtask.dto.request.AddCupboardRequest;
import ru.devolek.testtask.dto.response.CupboardDto;

import java.util.List;

public interface CupboardService {
    List<CupboardDto> getList();

    boolean addCupboard(AddCupboardRequest request);

    boolean editCupboard(int cupboardId, String cupboardName);

    boolean deleteCupboard(int cupboardId);
}
