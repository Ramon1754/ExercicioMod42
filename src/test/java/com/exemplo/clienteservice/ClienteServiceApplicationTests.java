package com.exemplo.clienteservice;

import com.exemplo.clienteservice.model.Cliente;
import com.exemplo.clienteservice.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ClienteServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testCreateCliente() throws Exception {
        String clienteJson = "{\"nome\":\"João\",\"email\":\"joao@example.com\"}";

        mockMvc.perform(post("/clientes")
                        .contentType("application/json")
                        .content(clienteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }

    @Test
    public void testGetCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEmail("maria@example.com");
        clienteRepository.save(cliente);

        mockMvc.perform(get("/clientes/" + cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.email").value("maria@example.com"));
    }

    @Test
    public void testUpdateCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setEmail("carlos@example.com");
        clienteRepository.save(cliente);

        String updatedClienteJson = "{\"nome\":\"Carlos Silva\",\"email\":\"carlos.silva@example.com\"}";

        mockMvc.perform(put("/clientes/" + cliente.getId())
                        .contentType("application/json")
                        .content(updatedClienteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"))
                .andExpect(jsonPath("$.email").value("carlos.silva@example.com"));
    }

    @Test
    public void testListClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Ana");
        cliente1.setEmail("ana@example.com");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Pedro");
        cliente2.setEmail("pedro@example.com");
        clienteRepository.save(cliente2);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"))
                .andExpect(jsonPath("$[1].nome").value("Pedro"));
    }
}
