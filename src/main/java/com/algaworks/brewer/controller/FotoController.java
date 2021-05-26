package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.dto.FotoDTO;
import com.algaworks.brewer.storage.FotosStorage;

@RestController
@RequestMapping("/fotos")
public class FotoController {

	@Autowired
	FotosStorage fotosStorage;

	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<FotoDTO>();
		new Thread(() ->  {
			System.out.println(files[0].getSize());
			final String contentType = files[0].getContentType();
			final String novoNomeFoto = this.fotosStorage.salvarTemporariamente(files);
			resultado.setResult(new FotoDTO(novoNomeFoto, contentType));
		}).start();
		return resultado;
	}

//	@GetMapping("/temp/{x}")
//	public byte[] recuperarFotoTemporária(@PathVariable("x") String nome) {
	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarFotoTemporária(@PathVariable String nome) {
		return this.fotosStorage.recuperarFotoTemporária(nome);
	}

}
