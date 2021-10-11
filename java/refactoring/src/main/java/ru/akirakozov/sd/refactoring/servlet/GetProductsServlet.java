package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DbAccessor;
import ru.akirakozov.sd.refactoring.HtmlRender;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.NORMAL;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<Product> productList = DbAccessor.all();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(productList.get(i).toString());
        }
        HtmlRender.html(response.getWriter(), null, NORMAL, sb.toString());
        setHeaderSuccess(response);
    }
}
