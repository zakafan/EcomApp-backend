package ma.app.ecom;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import ma.app.ecom.dao.CategoryRepository;
import ma.app.ecom.dao.ProductRepository;
import ma.app.ecom.model.Category;
import ma.app.ecom.model.Product;
import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class EcomAppApplication implements CommandLineRunner {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private RepositoryRestConfiguration configuration;

	public static void main(String[] args) {
		SpringApplication.run(EcomAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		configuration.exposeIdsFor(Product.class,Category.class);
	
		categoryRepository.save(new Category(null, "Computers", null, null));
		categoryRepository.save(new Category(null, "Clothes", null, null));
		categoryRepository.save(new Category(null, "Video Games", null, null));
		
		Random rnd=new Random();
		
		categoryRepository.findAll().forEach(c->{
			Product p=new Product();
			p.setName(RandomString.make(15));
			p.setPrice(100+rnd.nextDouble(10000));
			p.setSelected(rnd.nextBoolean());
			p.setCategory(c);
			p.setImageUrl("unknown.png");
			productRepository.save(p);
			
		});

	}

}
