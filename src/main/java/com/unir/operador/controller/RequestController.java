package com.unir.operador.controller;
import java.util.ArrayList;
import java.util.List;

import com.unir.operador.model.request.ReserveRequest;
import com.unir.operador.model.responseProduct.ResponseProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.unir.operador.model.pojo.Reserve;
import com.unir.operador.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {
	
	private final RequestService service;

	@GetMapping("/getOrdens/{ordenId}")
	public ResponseEntity<Reserve> getOrder(@PathVariable String ordenId) {
		Reserve orden = service.getOrdenById(ordenId);
		if (orden != null) {
			return ResponseEntity.ok(orden);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/allReserves")
	public List<Reserve> getAllReserves() {
		return service.getAllProduct();
	}

	@PostMapping("/saveOrden")
	public ResponseEntity<String> saveOrden(@RequestBody ReserveRequest request) {

		String ordens = service.createOrden(request);
		if (ordens != null && "OK".equals(ordens)) {
			return ResponseEntity.ok(ordens);
		} else {
			return ResponseEntity.badRequest().body(ordens);
		}
	}



}
