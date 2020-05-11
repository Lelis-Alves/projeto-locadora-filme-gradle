package com.ealves.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ealves.model.Filme;
import com.ealves.model.Response;
import com.ealves.services.FilmeService;

import javassist.tools.rmi.ObjectNotFoundException;

/**
 * 
 * @author Elias Alves - Para fazer upload da imagens, segue caminho -
 *         locadorafilme-api\src\main\resources\img
 *         
 *         Para executar o test Post - localhost:8080/filmes/uploadFilme
 *         file - file - value avengers.jps
 *         
 *         Para execytar o test Get - localhost:8080/filmes
 */

@RestController
@RequestMapping(value = "/filmes")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Filme>> findAll() {
		List<Filme> list = filmeService.listar();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Filme> find(@PathVariable Long id) throws ObjectNotFoundException {
		Filme obj = filmeService.findId(id);
		return obj != null ? ResponseEntity.ok().body(obj) : ResponseEntity.notFound().build();
	}

	@PostMapping("/uploadFilme")
	public Response uploadFile(@RequestParam("file") MultipartFile file) {
		
		Filme fileName = filmeService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName.getName()).toUriString();

		return new Response(fileName.getName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

}
