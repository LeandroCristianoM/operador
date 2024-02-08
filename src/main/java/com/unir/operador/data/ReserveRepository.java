package com.unir.operador.data;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unir.operador.model.pojo.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	List<Reserve> findByProductId(Long productId);
}
