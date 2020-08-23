package ru.devolek.testtask.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.devolek.testtask.config.DataConfig;
import ru.devolek.testtask.config.DispatcherServletInitializer;
import ru.devolek.testtask.config.MvcConfig;
import ru.devolek.testtask.dto.request.AddCupboardRequest;
import ru.devolek.testtask.dto.response.CupboardDto;
import ru.devolek.testtask.dto.response.InventoryDto;
import ru.devolek.testtask.model.Cupboard;
import ru.devolek.testtask.model.Inventory;
import ru.devolek.testtask.repository.CupboardRepository;
import ru.devolek.testtask.repository.InventoryRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfig.class, DataConfig.class, DispatcherServletInitializer.class})
@WebAppConfiguration
public class CupboardServiceImplTest {

    @Mock
    private CupboardRepository cupboardRepository;
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private CupboardServiceImpl cupboardService;

    private List<Cupboard> cupboardList;
    private List<CupboardDto> cupboardDtoList;
    private List<Inventory> inventoryList1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cupboard cupboard1 = new Cupboard();
        cupboard1.setName("cupboard_1");
        cupboard1.setId(1);
        Cupboard cupboard2 = new Cupboard();
        cupboard2.setName("cupboard_2");
        cupboard2.setId(2);

        Inventory inventory1 = new Inventory();
        inventory1.setId(1);
        inventory1.setName("inventory_1");
        inventory1.setCupboard(cupboard1);
        Inventory inventory2 = new Inventory();
        inventory2.setId(2);
        inventory2.setName("inventory_2");
        inventory2.setCupboard(cupboard1);
        Inventory inventory3 = new Inventory();
        inventory3.setId(3);
        inventory3.setName("inventory_3");
        inventory3.setCupboard(cupboard2);
        inventoryList1 = new ArrayList<>(Arrays.asList(inventory1, inventory2));
        List<Inventory> inventoryList2 = new ArrayList<>(Collections.singletonList(inventory3));
        List<InventoryDto> inventoryDtoList1 = inventoryList1.stream().map(inventory -> new InventoryDto(inventory.getId(),
                inventory.getName(),
                inventory.getCupboard().getId(),
                inventory.getCupboard().getName())).collect(Collectors.toList());
        List<InventoryDto> inventoryDtoList2 = inventoryList2.stream().map(inventory -> new InventoryDto(inventory.getId(),
                inventory.getName(),
                inventory.getCupboard().getId(),
                inventory.getCupboard().getName())).collect(Collectors.toList());

        cupboard1.setInventories(inventoryList1);
        cupboard2.setInventories(inventoryList2);
        cupboardList = new ArrayList<>(Arrays.asList(cupboard1, cupboard2));

        cupboardDtoList = new ArrayList<>();
        cupboardDtoList.add(new CupboardDto(cupboard1.getId(), cupboard1.getName(), inventoryDtoList1));
        cupboardDtoList.add(new CupboardDto(cupboard2.getId(), cupboard2.getName(), inventoryDtoList2));
    }


    @Test
    public void testGetList() {
        when(cupboardRepository.findAll()).thenReturn(cupboardList);
        List<CupboardDto> cupboardServiceList = cupboardService.getList();
        Assert.assertEquals(cupboardDtoList, cupboardServiceList);
        Mockito.verify(cupboardRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testAddCupboard() {
        String cupboardName = "cupboard_test";
        boolean cupboardIsCreated = cupboardService.addCupboard(new AddCupboardRequest(cupboardName));
        Assert.assertTrue(cupboardIsCreated);
        Cupboard savedCupboard = new Cupboard();
        savedCupboard.setName(cupboardName);
        Mockito.verify(cupboardRepository, Mockito.times(1)).save(savedCupboard);
    }

    @Test
    public void testAddCupboardFail() {
        String cupboardName = "cupboard_test";
        when(cupboardRepository.existsByName(cupboardName)).thenReturn(true);
        boolean cupboardIsCreated = cupboardService.addCupboard(new AddCupboardRequest(cupboardName));
        Assert.assertFalse(cupboardIsCreated);
        Mockito.verify(cupboardRepository, Mockito.times(0)).save(any(Cupboard.class));
    }

    @Test
    public void testEditCupboard() {
        int cupboardId = 1;
        String cupboardName = "cupboard_test";
        Cupboard cupboardTest = new Cupboard();
        cupboardTest.setId(cupboardId);
        cupboardTest.setName(cupboardName);
        cupboardTest.setInventories(inventoryList1);
        when(cupboardRepository.findById(cupboardId)).thenReturn(Optional.of(cupboardTest));
        when(cupboardRepository.existsByName(cupboardName)).thenReturn(false);
        boolean cupboardIsChanged = cupboardService.editCupboard(cupboardId, cupboardName);
        Assert.assertTrue(cupboardIsChanged);
        Mockito.verify(cupboardRepository, Mockito.times(1)).save(cupboardTest);
    }

    @Test
    public void testDeleteCupboard() {
        int cupboardId = 1;
        String cupboardName = "cupboard_test";
        Cupboard cupboardTest = new Cupboard();
        cupboardTest.setId(cupboardId);
        cupboardTest.setName(cupboardName);
        cupboardTest.setInventories(inventoryList1);
        when(cupboardRepository.findById(cupboardId)).thenReturn(Optional.of(cupboardTest));
        boolean cupboardIsChanged = cupboardService.deleteCupboard(cupboardId);
        Assert.assertTrue(cupboardIsChanged);
        Mockito.verify(cupboardRepository, Mockito.times(1)).delete(cupboardTest);
        inventoryList1.forEach(inventory -> inventory.setCupboard(null));
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(inventoryList1.get(0));
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(inventoryList1.get(1));
    }
}
