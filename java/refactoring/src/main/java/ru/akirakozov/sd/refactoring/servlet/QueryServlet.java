package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.H1;
import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.NORMAL;

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

    private void countTo(HttpServletResponse response) throws IOException {
        runQueryToHtml("SELECT COUNT(*) FROM PRODUCT", response,
                "Number of products: ", NORMAL, rs -> response.getWriter().println(rs.getInt(1)));
    }

    private void sumTo(HttpServletResponse response) throws IOException {
        runQueryToHtml("SELECT SUM(price) FROM PRODUCT", response,
                "Summary price: ", NORMAL, rs -> response.getWriter().println(rs.getInt(1)));
    }

    private void minTo(HttpServletResponse response) throws IOException {
        runQueryToHtml("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", response,
                "Product with min price: ", H1, rs -> writeProduct(rs, response.getWriter()));
    }

    private void maxTo(HttpServletResponse response) throws IOException {
        runQueryToHtml("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", response,
                "Product with max price: ", H1, rs -> writeProduct(rs, response.getWriter()));
    }
}
