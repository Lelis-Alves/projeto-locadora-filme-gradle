package com.ealves.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ealves.model.Filme;
import com.ealves.repositories.FilmeRepository;
import com.ealves.rexceptions.FileStorageException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class FilmeService {

	@Autowired
	private FilmeRepository filmeRepository;

	public List<Filme> listar() {
		return filmeRepository.findAll();
	}

	public Filme findId(Long id) throws ObjectNotFoundException {
		Optional<Filme> obj = filmeRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Filme.class.getName()));
	}

	public Filme storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String movie = "Avengers";
		Integer year = 2019;

		try {
			
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Filme dbFile = new Filme(fileName, file.getContentType(), year, movie, file.getBytes());

			return filmeRepository.save(dbFile);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Filme getFile(Long fileId) throws FileNotFoundException {
		return filmeRepository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
	}

}
