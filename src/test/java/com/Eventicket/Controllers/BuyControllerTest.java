package com.Eventicket.Controllers;

import com.Eventicket.Entities.BuyEntity;
import com.Eventicket.Services.BuyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BuyControllerTest {

    @MockBean
    BuyService buyService;

    @Autowired
    BuyController buyController;

    Long buyId;
    BuyEntity buyEntity;
    Long userId;

    @BeforeEach
    void setup() {
        buyId = 1L;
        userId = 1L;
        buyEntity = new BuyEntity();

        when(buyService.save(Mockito.anyLong(), Mockito.anyMap())).thenReturn(buyEntity);
        when(buyService.findById(buyId)).thenReturn(buyEntity);
        when(buyService.findAll()).thenReturn(List.of(buyEntity));
    }

    @Test
    @DisplayName("Teste controller save, status e compra salva")
    void save() {
        Map<Long, Integer> carrinho = Map.of(1L, 2);

        ResponseEntity<BuyEntity> response = buyController.save(userId, carrinho);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Teste controller update, status e compra atualizada")
    void update() {
        when(buyService.update(Mockito.any(BuyEntity.class), Mockito.anyLong())).thenReturn(buyEntity);

        ResponseEntity<BuyEntity> response = buyController.update(buyEntity, buyId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Teste controller delete, status e confirmação de deletado")
    void delete() {
        when(buyService.delete(buyId)).thenReturn("Compra deletada");

        ResponseEntity<String> response = buyController.delete(buyId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Compra deletada", response.getBody());
    }

    @Test
    @DisplayName("Teste controller findAll, status e lista de compras")
    void findAll() {
        ResponseEntity<List<BuyEntity>> response = buyController.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Teste controller findById, status e compra encontrada")
    void findById() {
        ResponseEntity<BuyEntity> response = buyController.findById(buyId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
