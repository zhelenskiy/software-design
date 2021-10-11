package ru.akirakozov.sd.refactoring;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccessor {
    @FunctionalInterface
    private interface ThrowableFunction<T, R> {
        R accept(T item1) throws Exception;
    }

    public static long count() {
        List<Long> list = runQuery(countProductsQuery, res -> res.getLong(1));
        assert list.size() == 1;
        return list.get(0);
    }

    public static long sum() {
        List<Long> list = runQuery(sumPriceProductsQuery, res -> res.getLong(1));
        assert list.size() == 1;
        return list.get(0);
    }

    public static Product min() {
        List<Product> list = runQuery(minPriceProductsQuery, DbAccessor::getProduct);
        assert list.size() <= 1;
        return list.get(0);
    }

    public static Product max() {
        List<Product> list = runQuery(maxPriceProductsQuery, DbAccessor::getProduct);
        assert list.size() <= 1;
        return list.get(0);
    }

    public static List<Product> all() {
        return runQuery(allProductsQuery, DbAccessor::getProduct);
    }

    private static void runUpdate(@NotNull String query) {
        runSql(st -> st.executeUpdate(query));
    }

    public static void createTableIfNotExists() {
        runUpdate(createTableQuery);
    }

    public static void clearDatabase() {
        runUpdate(clearDatabaseQuery);
    }

    public static void insertProduct(@NotNull Product product) {
        runUpdate(insert(product.name, product.price));
    }

    public static final String countProductsQuery = "SELECT COUNT(*) FROM PRODUCT";
    public static final String sumPriceProductsQuery = "SELECT SUM(price) FROM PRODUCT";
    public static final String minPriceProductsQuery = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    public static final String maxPriceProductsQuery = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    public static final String allProductsQuery = "SELECT * FROM PRODUCT";
    public static final String clearDatabaseQuery = "DELETE FROM PRODUCT ";
    public static final String createTableQuery =
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

    private static @NotNull Product getProduct(@NotNull ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        return new Product(name, price);
    }
}
