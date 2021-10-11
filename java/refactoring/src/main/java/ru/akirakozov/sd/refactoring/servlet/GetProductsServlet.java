package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;
import ru.akirakozov.sd.refactoring.HtmlRender;
import ru.akirakozov.sd.refactoring.model.ProductPOJO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.NORMAL;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    public GetProductsServlet(AbstractProductAccessor accessor) {
        super(accessor);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<ProductPOJO> productList = accessor.all();
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
