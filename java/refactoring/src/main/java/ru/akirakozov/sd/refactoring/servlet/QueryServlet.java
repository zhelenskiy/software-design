package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    @Override
    public void doGet(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            maxTo(response);
        } else if ("min".equals(command)) {
            minTo(response);
        } else if ("sum".equals(command)) {
            sumTo(response);
        } else if ("count".equals(command)) {
            countTo(response);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        setHeaderSuccess(response);
    }

    private void countTo(HttpServletResponse response) {
        runQueryToHtml("SELECT COUNT(*) FROM PRODUCT", response,
                "Number of products: ", false, (rs, writer) -> {
            if (rs.next()) {
                writer.println(rs.getInt(1));
            }
        });
    }

    private void sumTo(HttpServletResponse response) {
        runQueryToHtml("SELECT SUM(price) FROM PRODUCT", response,
                "Summary price: ", false, (rs, writer) -> {
                    if (rs.next()) {
                        writer.println(rs.getInt(1));
                    }
                });
    }

    private void minTo(HttpServletResponse response) {
        runQueryToHtml("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", response,
                "Product with min price: ", true, (rs, writer) -> {
                    if (rs.next()) {
                        writeProduct(rs, writer);
                    }
                });
    }

    private void maxTo(HttpServletResponse response) {
        runQueryToHtml("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", response,
                "Product with max price: ", true, (rs, writer) -> {
                    if (rs.next()) {
                        writeProduct(rs, writer);
                    }
                });
    }
}
