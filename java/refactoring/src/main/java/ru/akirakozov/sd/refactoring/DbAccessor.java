package ru.akirakozov.sd.refactoring;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.servlet.AbstractProductServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class DbAccessor {
    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accept(T item1) throws Exception;
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
    public static @NotNull String insert(String name, long price) {
        return "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\", " + price + ")";
    }

    public static void runQuery(String query, ThrowableConsumer<ResultSet> resSetConsumer) {
        runSql(stmt -> {
            try (ResultSet rs = stmt.executeQuery(query)) {
                // limits are already specified in query
                // So we don't need to check it twice, and we can just take all received elements
                while (rs.next()) {
                    resSetConsumer.accept(rs);
                }
            }
        });
    }

    public static void runQueryToHtml(String query, @NotNull HttpServletResponse response, String header, HtmlRender.HeaderState headerState, ThrowableConsumer<ResultSet> res) throws IOException {
        HtmlRender.html(response.getWriter(), header, headerState, () -> runQuery(query, res));
    }

    public static void runSql(ThrowableConsumer<Statement> statementUser) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db"); Statement stmt = c.createStatement()) {
                statementUser.accept(stmt);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeProduct(@NotNull ResultSet rs, @NotNull PrintWriter writer) throws SQLException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        writer.println(name + "\t" + price + "</br>");
    }
}
