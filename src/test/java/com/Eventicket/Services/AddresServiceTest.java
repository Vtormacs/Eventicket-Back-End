package com.Eventicket.Services;

import com.Eventicket.Entities.AddresEntity;
import com.Eventicket.Repositories.AddresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddresServiceTest {

    @MockBean
    AddresRepository addresRepository;

    @Autowired
    AddresService addresService;

    Long id;

    @BeforeEach
    void setup() {
        //FindById
        AddresEntity endereco = new AddresEntity(11L, "Estado", "Cidade", "Rua", "1234", null, null);
        this.id = endereco.getId();
        when(addresRepository.findById(this.id)).thenReturn(Optional.of(endereco));

        //FindAll
        List<AddresEntity> lista = new ArrayList<>();
        lista.add(new AddresEntity(1L, "Estado1", "Cidade1", "Rua1", "12134", null, null));
        lista.add(new AddresEntity(2L, "Estado2", "Cidade2", "Rua2", "1234", null, null));
        lista.add(new AddresEntity(3L, "Estado3", "Cidade3", "Rua3", "1234", null, null));
        lista.add(new AddresEntity(4L, "Estado4", "Cidade4", "Rua4", "1234", null, null));
        lista.add(new AddresEntity(5L, "Estado5", "Cidade5", "Rua5", "1234", null, null));
        when(addresRepository.findAll()).thenReturn(lista);
    }

    @Test
    void save() {
        AddresEntity endereco = new AddresEntity(1L, "Estado", "Cidade", "Rua", "1234", null, null);
        when(addresRepository.save(endereco)).thenReturn(endereco);

        var retorno = addresService.save(endereco);

        assertEquals(1l, retorno.getId());
        assertEquals("Estado", retorno.getEstado());
        assertEquals("Cidade", retorno.getCidade());
        assertEquals("Rua", retorno.getRua());
    }

    @Test
    void update() {
        AddresEntity novoEndereco = new AddresEntity(this.id, "Estado atualizado", "Cidade", "Rua", "1234", null, null);
        when(addresRepository.save(novoEndereco)).thenReturn(novoEndereco);

        var retorno = addresService.update(novoEndereco, this.id);

        assertEquals(11L, retorno.getId());
        assertEquals("Estado atualizado", retorno.getEstado());
    }

    @Test
    void delete() {
        var retorno = addresService.delete(this.id);

        assertEquals("Endereco Deletado!", retorno);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }
}