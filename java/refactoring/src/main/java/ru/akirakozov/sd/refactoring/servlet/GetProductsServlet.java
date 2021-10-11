package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        runQueryToHtml("SELECT * FROM PRODUCT", response, null, false, (rs, writer) -> {
            while (rs.next()) {
                writeProduct(rs, writer);
            }
        });
        setHeaderSuccess(response);
    }
}
