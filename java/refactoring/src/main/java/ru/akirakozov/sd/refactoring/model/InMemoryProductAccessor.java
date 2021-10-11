package ru.akirakozov.sd.refactoring.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryProductAccessor implements AbstractProductAccessor {
    private final List<ProductPOJO> products = new ArrayList<>();

    @Override
    public long count() {
        return products.size();
    }

    @Override
    public long sum() {
        return products.stream().mapToLong(pr -> pr.price).sum();
    }

    @Override
    public ProductPOJO min() {
        return products.stream().min(Comparator.comparing(pr -> pr.price)).orElse(null);
    }

    @Override
    public ProductPOJO max() {
        return products.stream().max(Comparator.comparing(pr -> pr.price)).orElse(null);
    }

    @Override
    public List<ProductPOJO> all() {
        return products;
    }

    @Override
    public void createTableIfNotExists() {
    }

    @Override
    public void clearDatabase() {
        products.clear();
    }

    @Override
    public void insertProduct(@NotNull ProductPOJO product) {
        products.add(product);
    }
}
