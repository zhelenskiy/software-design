package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class AbstractProductServlet extends HttpServlet {
    @Override
    public abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    @FunctionalInterface
    public interface ThrowableBiConsumer<T, U> {
        void accept(T item1, U item2) throws Exception;
    }
    @FunctionalInterface
    public interface ThrowableConsumer<T> {
        void accept(T item1) throws Exception;
    }

    public static void runQuery(String query, HttpServletResponse response, ThrowableBiConsumer<ResultSet, PrintWriter> writer) {
        runSql(stmt -> {
            ResultSet rs = stmt.executeQuery(query);
            writer.accept(rs, response.getWriter());
            rs.close();
        });
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
}