package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.HtmlRender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public abstract class AbstractProductServlet extends HttpServlet {
    @Override
    public abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accept(T item1) throws Exception;
    }

    public static void runQuery(String query, HttpServletResponse response, ThrowableConsumer<ResultSet> resSetConsumer) {
        runSql(stmt -> {
            ResultSet rs = stmt.executeQuery(query);
            resSetConsumer.accept(rs);
            rs.close();
        });
    }

    public static void runQueryToHtml(String query, HttpServletResponse response, String header, HtmlRender.HeaderState headerState, ThrowableConsumer<ResultSet> res) {
        runQuery(query, response,
                set -> HtmlRender.html(response.getWriter(), header, headerState, () -> res.accept(set))
        );
    }

    public static void runSql(ThrowableConsumer<Statement> statementUser) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                statementUser.accept(stmt);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHeaderSuccess(@NotNull HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static void writeProduct(@NotNull ResultSet rs, @NotNull PrintWriter writer) throws SQLException {
        String name = rs.getString("name");
        int price = rs.getInt("price");
        writer.println(name + "\t" + price + "</br>");
    }
}
