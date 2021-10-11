package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        runQuery("SELECT * FROM PRODUCT", response, (rs, writer) -> {
            writer.println("<html><body>");

            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                writer.println(name + "\t" + price + "</br>");
            }
            writer.println("</body></html>");
        });
        setHeaderSuccess(response);
    }
}
