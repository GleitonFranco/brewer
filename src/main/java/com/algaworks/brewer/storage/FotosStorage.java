package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotosStorage {

	public String salvarTemporariamente(MultipartFile[] files);
	
	public byte[] recuperarFotoTemporária(String nome);

}
