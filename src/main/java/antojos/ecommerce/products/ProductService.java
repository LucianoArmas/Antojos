package antojos.ecommerce.products;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts(){
    return productRepository.findAll();
  }

  public Product getProductById(Long id){
    return productRepository.findById(id).orElse(null);
  }

  public void addProduct(Product product){
    productRepository.save(product);
  }

  public void updateProduct(Product product){
    //AGREGAR MODIFICACIONES DE LOS ATRIBUTOS
    productRepository.save(product);
  }

  public void deleteProduct(Long id){
    //AGREGAR VALIDACION DE EXISTENCIA
    productRepository.deleteById(id);
  }
}
