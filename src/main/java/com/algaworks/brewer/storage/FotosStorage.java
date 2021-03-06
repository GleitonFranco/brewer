package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotosStorage {

	String salvarTemporariamente(MultipartFile[] files);
	
	byte[] recuperarFotoTemporÃ¡ria(String nome);

	void salvarMesmo(String nome);

    byte[] recuperarFotoMesmo(String nome);
}
