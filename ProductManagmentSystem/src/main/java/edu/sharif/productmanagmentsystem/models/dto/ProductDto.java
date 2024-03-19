package edu.sharif.productmanagmentsystem.models.dto;


import edu.sharif.productmanagmentsystem.models.Product;

public class ProductDto {
    public String title;
    public String brand;
    public int price;

    public ProductDto(Product product) {
        this.title = product.getTitle();
        this.brand = product.getBrand();
        this.price = product.getPrice();
    }


}
