package ru.akirakozov.sd.refactoring.model;

public class ProductPOJO {
    public final String name;
    public final long price;

    public ProductPOJO(String name, long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + "\t" + price + "</br>";
    }
}
