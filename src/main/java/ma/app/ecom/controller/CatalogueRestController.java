package ma.app.ecom.controller;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ma.app.ecom.dao.ProductRepository;
import ma.app.ecom.model.Product;

@CrossOrigin("*")
@RestController
public class CatalogueRestController {
	
	private ProductRepository productRepository;

	public CatalogueRestController(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
	}
	
	@GetMapping(path =  "/photoProduct/{id}",produces =org.springframework.http.MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto(@PathVariable( "id")Long id) throws Exception {
		Product p=productRepository.findById(id).get();
		return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecomApp/products/"+p.getImageUrl()));
	}
	
	@PostMapping(path="/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file,@PathVariable Long id) throws Exception {
		
		Product p=productRepository.findById(id).get();
		
		p.setImageUrl(file.getOriginalFilename());
		Files.write(Paths.get(System.getProperty("user.home")+"ecom/products"+p.getImageUrl()),file.getBytes());
		productRepository.save(p);
	}

}
