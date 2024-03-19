package edu.sharif.productmanagmentsystem.repository;

import edu.sharif.productmanagmentsystem.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepository {
    public static ArrayList<Product> products= new ArrayList<>();
    public static int lastIndex=0;

    public ProductRepository(){
        products.add(new Product("Mobile","Samsung", 50000000));
        products.add(new Product("Cacke","TITOP!",10000));
    }

    public boolean createNewProduct(Product product){
        products.add(product);
        if(ProductRepository.lastIndex == ProductRepository.products.size()){
            return true;
        }else{
            return false;
        }
    }

    public List<Product> findAllProducts(){
        return products;
    }
}
