package com.exemplo.produtosevice.repository;

import com.exemplo.produtosevice.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
