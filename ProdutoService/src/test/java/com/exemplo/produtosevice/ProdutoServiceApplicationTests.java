package com.exemplo.produtosevice;

import com.exemplo.produtosevice.model.Produto;
import com.exemplo.produtosevice.repository.ProdutoRepository;
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
public class ProdutoServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testCreateProduto() throws Exception {
        String produtoJson = "{\"nome\":\"Produto A\",\"preco\":123.45}";

        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content(produtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto A"))
                .andExpect(jsonPath("$.preco").value(123.45));
    }

    @Test
    public void testGetProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto B");
        produto.setPreco(678.90);
        produtoRepository.save(produto);

        mockMvc.perform(get("/produtos/" + produto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto B"))
                .andExpect(jsonPath("$.preco").value(678.90));
    }

    @Test
    public void testUpdateProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto C");
        produto.setPreco(234.56);
        produtoRepository.save(produto);

        String updatedProdutoJson = "{\"nome\":\"Produto C Atualizado\",\"preco\":789.01}";

        mockMvc.perform(put("/produtos/" + produto.getId())
                        .contentType("application/json")
                        .content(updatedProdutoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto C Atualizado"))
                .andExpect(jsonPath("$.preco").value(789.01));
    }

    @Test
    public void testListProdutos() throws Exception {
        Produto produto1 = new Produto();
        produto1.setNome("Produto D");
        produto1.setPreco(456.78);
        produtoRepository.save(produto1);

        Produto produto2 = new Produto();
        produto2.setNome("Produto E");
        produto2.setPreco(987.65);
        produtoRepository.save(produto2);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto D"))
                .andExpect(jsonPath("$[1].nome").value("Produto E"));
    }
}
