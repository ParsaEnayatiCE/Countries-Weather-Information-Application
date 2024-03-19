package edu.sharif.productmanagmentsystem.models;


import edu.sharif.productmanagmentsystem.repository.ProductRepository;

public class Product {
    public String id;
    public String title;
    public String brand;
    public int price;

    public Product(String title, String brand, int price) {
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.id = "PRD_" + ProductRepository.lastIndex++;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
