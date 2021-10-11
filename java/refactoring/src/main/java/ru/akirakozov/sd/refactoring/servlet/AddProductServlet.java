package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;
import ru.akirakozov.sd.refactoring.model.ProductPOJO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {

    public AddProductServlet(AbstractProductAccessor accessor) {
        super(accessor);
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        accessor.insertProduct(new ProductPOJO(name, price));
        response.getWriter().println("OK");
        setHeaderSuccess(response);
    }
}
