package ru.akirakozov.sd.refactoring.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AbstractProductAccessor {
    long count();

    long sum();

    ProductPOJO min();

    ProductPOJO max();

    List<ProductPOJO> all();

    void createTableIfNotExists();

    void clearDatabase();

    void insertProduct(@NotNull ProductPOJO product);
}
