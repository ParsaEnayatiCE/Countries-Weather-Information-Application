package edu.sharif.productmanagmentsystem.services;

import edu.sharif.productmanagmentsystem.models.Product;
import edu.sharif.productmanagmentsystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts(){
        return productRepository.findAllProducts();
    }

    public boolean addProduct(String title, String brand, int price){
        return productRepository.createNewProduct(new Product(title,brand,price));
    }

    public Product getProductById (String id) {
        List<Product> allProducts = findAllProducts();
        for (Product p : allProducts) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        Logger.getLogger(ProductService.class.getName()).log(Level.INFO, "Product with id " + id + " was Not Found");
        return null;
    }




}
