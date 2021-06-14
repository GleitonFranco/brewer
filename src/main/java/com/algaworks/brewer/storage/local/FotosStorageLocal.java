package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotosStorage;

public class FotosStorageLocal implements FotosStorage {
	private final Logger logger = LoggerFactory.getLogger(FotosStorageLocal.class);
	Path local;
	Path localTemporario;

	public FotosStorageLocal() {
		this.local = FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerfotos");
		criarPastas();
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas");
				logger.debug("Pastas local: " + this.local.toAbsolutePath());
				logger.debug("Pastas temp: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		System.out.println("Salvando fotos temporariamente....");
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = this.renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao tentar salvar arquivo! ", e);
			}
		}
		return novoNome;
	}

	private String renomearArquivo(String nomeOriginal) {
		final String novoNome = UUID.randomUUID() + "_" + nomeOriginal;
		if (logger.isDebugEnabled()) {
			System.out.println("Renomeado: de " + nomeOriginal + " para " + novoNome);
		}
		return novoNome;
	}

	@Override
	public byte[] recuperarFotoTemporária(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Deu ruim na foto! :o", e);
		}
	}

    @Override
    public void salvarMesmo(String nome) {
        try {
            Files.move(this.localTemporario.resolve(nome), this.local.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao mover foto para o destino final ", e);
        }
        try {
            Thumbnails.of(this.local.resolve(nome).toString()).size(40, 68).toFiles(Rename.PREFIX_HYPHEN_THUMBNAIL);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar thumbnail da foto no destino final ", e);
        }
    }

    @Override
    public byte[] recuperarFotoMesmo(String nome) {
        try {
            return Files.readAllBytes(this.local.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Deu ruim na foto! :o", e);
        }
    }

}
