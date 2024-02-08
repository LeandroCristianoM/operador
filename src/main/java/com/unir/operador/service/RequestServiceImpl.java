package com.unir.operador.service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.unir.operador.facade.GetProduct;
import com.unir.operador.model.request.ReserveRequest;
import com.unir.operador.model.responseProduct.ResponseProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unir.operador.data.ReserveRepository;
import com.unir.operador.model.pojo.Reserve;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

	@Autowired
	private ReserveRepository repository;
	@Override
	public Reserve getOrdenById(String ordenId) {
		return repository.findById(Long.valueOf(ordenId)).orElse(null);
	}
	@Override
	public List<Reserve> getAllProduct() {
		return repository.findAll();
	}

	private final GetProduct getProduct;

	@Override
	public String createOrden(ReserveRequest request) {
		// Obtener detalles del producto
		ResponseProduct responseProduct = getProduct.getOrden(String.valueOf(request.getProductId()));

		if (responseProduct == null) {
			log.error("Error al recuperar detalles del producto para el ID: {}", request.getProductId());
			return "Error al recuperar detalles del producto";
		}

		if(responseProduct.getScore() < request.getQuantity() ){
			log.error("Error al validar el stock!:{}", request.getProductId());
			return "No hay stock disponible por la cantidad solicitada";
		}

		responseProduct.setScore(responseProduct.getScore()- request.getQuantity());
		boolean prueb = false;
		try {
			prueb = getProduct.updOrden(String.valueOf(request.getProductId()), responseProduct);
			if (prueb)
			{
				Reserve reserve = new Reserve();
				reserve.setProductId(Math.toIntExact(responseProduct.getId()));
				reserve.setQuantity(responseProduct.getScore());
				reserve.setReserveDate(LocalDate.now());
				log.info("Entidad Reserve creada: {}", reserve);

				repository.save(reserve);
				return "OK";
			}
			return "No se pudo actualizar el stock del producto";
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}

}
