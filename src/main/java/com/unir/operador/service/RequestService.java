package com.unir.operador.service;
import java.util.List;
import com.unir.operador.model.pojo.Reserve;
import com.unir.operador.model.request.ReserveRequest;

public interface RequestService {

	Reserve getOrdenById(String ordenId);
	List<Reserve> getAllProduct();

	String createOrden(ReserveRequest request);
}
