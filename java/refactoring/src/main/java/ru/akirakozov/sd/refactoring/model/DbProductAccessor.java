package ru.akirakozov.sd.refactoring.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbProductAccessor implements AbstractProductAccessor {
    @FunctionalInterface
    private interface ThrowableFunction<T, R> {
        R accept(T item1) throws Exception;
    }


    @Override
    public long count() {
        List<Long> list = runQuery(countProductsQuery, res -> res.getLong(1));
        assert list.size() == 1;
        return list.get(0);
    }

    @Override
    public long sum() {
        List<Long> list = runQuery(sumPriceProductsQuery, res -> res.getLong(1));
        assert list.size() == 1;
        return list.get(0);
    }

    @Override
    public ProductPOJO min() {
        List<ProductPOJO> list = runQuery(minPriceProductsQuery, DbProductAccessor::getProduct);
        assert list.size() <= 1;
        return list.get(0);
    }

    @Override
    public ProductPOJO max() {
        List<ProductPOJO> list = runQuery(maxPriceProductsQuery, DbProductAccessor::getProduct);
        assert list.size() <= 1;
        return list.get(0);
    }

    @Override
    public List<ProductPOJO> all() {
        return runQuery(allProductsQuery, DbProductAccessor::getProduct);
    }

    private static void runUpdate(@NotNull String query) {
        runSql(st -> st.executeUpdate(query));
    }

    @Override
    public void createTableIfNotExists() {
        runUpdate(createTableQuery);
    }

    @Override
    public void clearDatabase() {
        runUpdate(clearDatabaseQuery);
    }

    @Override
    public void insertProduct(@NotNull ProductPOJO product) {
        runUpdate(insert(product.name, product.price));
    }

    private static final String countProductsQuery = "SELECT COUNT(*) FROM PRODUCT";
    private static final String sumPriceProductsQuery = "SELECT SUM(price) FROM PRODUCT";
    private static final String minPriceProductsQuery = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    private static final String maxPriceProductsQuery = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    private static final String allProductsQuery = "SELECT * FROM PRODUCT";
    private static final String clearDatabaseQuery = "DELETE FROM PRODUCT ";
    private static final String createTableQuery =
            "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";

    @Contract(pure = true)
    private static @NotNull String insert(String name, long price) {
        return "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\", " + price + ")";
    }

    private static <R> List<R> runQuery(String query, ThrowableFunction<ResultSet, R> resSetConsumer) {
        return runSql(stmt -> {
            try (ResultSet rs = stmt.executeQuery(query)) {
                // limits are already specified in query
                // So we don't need to check it twice, and we can just take all received elements <R>
                final List<R> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(resSetConsumer.accept(rs));
                }
                return list;
            }
        });
    }

    private static <R> R runSql(ThrowableFunction<Statement, R> statementUser) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db"); Statement stmt = c.createStatement()) {
            return statementUser.accept(stmt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull ProductPOJO getProduct(@NotNull ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new ProductPOJO(name, price);
    }
}
