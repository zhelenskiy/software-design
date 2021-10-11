package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {

    @Override
    public void doGet(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        runSql(stmt -> stmt.executeUpdate(
                "INSERT INTO PRODUCT " + "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")")
        );
        setHeaderSuccess(response);
        response.getWriter().println("OK");
    }
}
