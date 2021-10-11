package ru.akirakozov.sd.refactoring;

public class Product {
    final String name;
    final long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + "\t" + price + "</br>";
    }
}
