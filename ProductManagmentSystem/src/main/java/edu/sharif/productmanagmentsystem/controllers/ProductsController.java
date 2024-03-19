package edu.sharif.productmanagmentsystem.controllers;


import edu.sharif.productmanagmentsystem.models.Response;
import edu.sharif.productmanagmentsystem.models.dto.ProductDto;
import edu.sharif.productmanagmentsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<ProductDto> listAllProducts(){
        return productService.findAllProducts().stream().map(product -> new ProductDto(product)).toList();
    }

    @PostMapping("/add")
    public boolean addProduct(@RequestHeader("name") String title, @RequestHeader String brand, @RequestHeader int price){
        return productService.addProduct(title,brand,price);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProduct(@PathVariable(value="id") String id){
        if (Objects.isNull(productService.getProductById(id))) {
            return ResponseEntity.status(404).body(new Response("404", "Product with id " + id + " was Not Found"));
        }
        return ResponseEntity.ok(new Response("200", productService.getProductById(id).toString()));
    }
}
