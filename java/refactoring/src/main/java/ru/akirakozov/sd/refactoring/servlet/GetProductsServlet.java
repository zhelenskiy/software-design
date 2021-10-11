package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DbAccessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.akirakozov.sd.refactoring.DbAccessor.allProductsQuery;
import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.NORMAL;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DbAccessor.runQueryToHtml(allProductsQuery, response, null, NORMAL, rs -> DbAccessor.writeProduct(rs, response.getWriter()));
        setHeaderSuccess(response);
    }
}
