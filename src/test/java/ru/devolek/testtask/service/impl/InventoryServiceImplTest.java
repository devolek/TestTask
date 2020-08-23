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
import ru.devolek.testtask.config.MvcConfig;
import ru.devolek.testtask.dto.request.AddInventoryInCupboardRequest;
import ru.devolek.testtask.dto.request.AddInventoryRequest;
import ru.devolek.testtask.dto.response.InventoryDto;
import ru.devolek.testtask.model.Cupboard;
import ru.devolek.testtask.model.Inventory;
import ru.devolek.testtask.repository.CupboardRepository;
import ru.devolek.testtask.repository.InventoryRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfig.class, DataConfig.class})
@WebAppConfiguration
public class InventoryServiceImplTest {
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private CupboardRepository cupboardRepository;
    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private List<Inventory> allInventoryList;
    private Cupboard cupboard1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cupboard1 = new Cupboard();
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
        Inventory inventory4 = new Inventory();
        inventory3.setId(4);
        inventory3.setName("inventory_4");
        allInventoryList = new ArrayList<>(Arrays.asList(inventory1, inventory2, inventory3, inventory4));
        List<Inventory> inventoryList1 = new ArrayList<>(Arrays.asList(inventory1, inventory2));
        List<Inventory> inventoryList2 = new ArrayList<>(Collections.singletonList(inventory3));


        cupboard1.setInventories(inventoryList1);
        cupboard2.setInventories(inventoryList2);
    }

    @Test
    public void testGetList() {
        List<InventoryDto> inventoryDtoList = allInventoryList.stream()
                .map(inventory -> new InventoryDto(inventory.getId(),
                        inventory.getName(),
                        inventory.getCupboard() == null ? null : inventory.getCupboard().getId(),
                        inventory.getCupboard() == null ? null : inventory.getCupboard().getName()))
                .collect(Collectors.toList());
        when(inventoryRepository.findAll()).thenReturn(allInventoryList);
        List<InventoryDto> inventoryServiceList = inventoryService.getList();
        Assert.assertEquals(inventoryDtoList, inventoryServiceList);
        Mockito.verify(inventoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetUnusedList() {
        List<Inventory> inventoryUnusedList = allInventoryList.stream()
                .filter(inventory -> inventory.getCupboard() == null).collect(Collectors.toList());
        List<InventoryDto> inventoryDtoUnusedList = inventoryUnusedList.stream()
                .map(inventory -> new InventoryDto(inventory.getId(),
                        inventory.getName(),
                        inventory.getCupboard() == null ? null : inventory.getCupboard().getId(),
                        inventory.getCupboard() == null ? null : inventory.getCupboard().getName()))
                .collect(Collectors.toList());
        when(inventoryRepository.getUnusedInventories()).thenReturn(inventoryUnusedList);
        List<InventoryDto> inventoryServiceList = inventoryService.getUnusedList();
        Assert.assertEquals(inventoryDtoUnusedList, inventoryServiceList);
        Mockito.verify(inventoryRepository, Mockito.times(1)).getUnusedInventories();
    }

    @Test
    public void testAddInventory() {
        String inventoryName = "inventory_test";
        boolean inventoryIsCrated = inventoryService.addInventory(new AddInventoryRequest(inventoryName));
        Assert.assertTrue(inventoryIsCrated);
        Inventory savedInventory = new Inventory();
        savedInventory.setName(inventoryName);
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(savedInventory);
    }

    @Test
    public void testAddInventoryFail() {
        String inventoryName = "inventory_test";
        when(inventoryRepository.existsByName(inventoryName)).thenReturn(true);
        boolean inventoryIsCrated = inventoryService.addInventory(new AddInventoryRequest(inventoryName));
        Assert.assertFalse(inventoryIsCrated);
        Mockito.verify(inventoryRepository, Mockito.times(0)).save(any(Inventory.class));
    }

    @Test
    public void testEditInventory() {
        int inventoryId = 1;
        String inventoryName = "inventory_test";
        Inventory inventoryTest = new Inventory();
        inventoryTest.setId(inventoryId);
        inventoryTest.setName(inventoryName);
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventoryTest));
        when(inventoryRepository.existsByName(inventoryName)).thenReturn(false);
        boolean inventoryIsChanged = inventoryService.editInventory(inventoryId, inventoryName);
        Assert.assertTrue(inventoryIsChanged);
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(inventoryTest);
    }

    @Test
    public void testDeleteInventory() {
        int inventoryId = 1;
        String inventoryName = "inventory_test";
        Inventory inventoryTest = new Inventory();
        inventoryTest.setId(inventoryId);
        inventoryTest.setName(inventoryName);
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventoryTest));
        boolean inventoryIsDeleted = inventoryService.deleteInventory(inventoryId);
        Assert.assertTrue(inventoryIsDeleted);
        Mockito.verify(inventoryRepository, Mockito.times(1)).delete(inventoryTest);
    }

    @Test
    public void testAddInventoryInCupboard() {
        int inventoryId = 1;
        String inventoryName = "inventory_test";
        Inventory inventoryTest = new Inventory();
        inventoryTest.setId(inventoryId);
        inventoryTest.setName(inventoryName);
        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventoryTest));
        when(cupboardRepository.findById(cupboard1.getId())).thenReturn(Optional.of(cupboard1));

        boolean addInventoryInCupboard = inventoryService.addInventoryInCupboard(new AddInventoryInCupboardRequest(cupboard1.getId(), inventoryId));
        Assert.assertTrue(addInventoryInCupboard);
        inventoryTest.setCupboard(cupboard1);
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(inventoryTest);
    }

    @Test
    public void testDeleteInventoryInCupboard() {
        int inventoryId = 1;
        String inventoryName = "inventory_test";
        Inventory inventoryTest = new Inventory();
        inventoryTest.setId(inventoryId);
        inventoryTest.setName(inventoryName);
        inventoryTest.setCupboard(cupboard1);

        when(inventoryRepository.findById(inventoryId)).thenReturn(Optional.of(inventoryTest));
        when(cupboardRepository.findById(cupboard1.getId())).thenReturn(Optional.of(cupboard1));

        boolean deleteInventoryInCupboard = inventoryService.deleteInventoryInCupboard(inventoryId);
        Assert.assertTrue(deleteInventoryInCupboard);
        inventoryTest.setCupboard(null);
        Mockito.verify(inventoryRepository, Mockito.times(1)).save(inventoryTest);
    }
}
