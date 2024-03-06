package antojos.ecommerce.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Map<String, List<Product>> getProducts(String name){
    List<Product> productList;
    if (name.isBlank()){
      productList = productRepository.findAll();
    }else {
      productList = productRepository.findByNameContainingIgnoreCase(name);
    }

    Map<String, List<Product>> prodByType = new HashMap<>();

    for (Product product : productList){
      String type = product.getType();
      prodByType.computeIfAbsent(type, k -> new ArrayList<>()).add(product);
    }

    return prodByType;
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
