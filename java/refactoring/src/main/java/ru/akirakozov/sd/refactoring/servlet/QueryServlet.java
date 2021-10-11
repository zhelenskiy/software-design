package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        runQuery("SELECT COUNT(*) FROM PRODUCT", response, (rs, writer) -> {
            writer.println("<html><body>");
            writer.println("Number of products: ");

            if (rs.next()) {
                writer.println(rs.getInt(1));
            }
            writer.println("</body></html>");
        });
    }

    private void sumTo(HttpServletResponse response) {
        runQuery("SELECT SUM(price) FROM PRODUCT", response, (rs, writer) -> {
            writer.println("<html><body>");
            writer.println("Summary price: ");

            if (rs.next()) {
                writer.println(rs.getInt(1));
            }
            writer.println("</body></html>");
        });
    }

    private void minTo(HttpServletResponse response) {
        runQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", response, (rs, writer) -> {
            writer.println("<html><body>");
            writer.println("<h1>Product with min price: </h1>");

            if (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                writer.println(name + "\t" + price + "</br>");
            }
            writer.println("</body></html>");
        });
    }

    private void maxTo(HttpServletResponse response) {
        runQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", response, (rs, writer) -> {
            writer.println("<html><body>");
            writer.println("<h1>Product with max price: </h1>");

            if (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                writer.println(name + "\t" + price + "</br>");
            }
            writer.println("</body></html>");
        });
    }
}
