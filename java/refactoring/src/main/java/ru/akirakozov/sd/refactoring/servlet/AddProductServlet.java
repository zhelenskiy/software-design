package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.DbAccessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.DbAccessor.insert;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {

    @Override
    public void doGet(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        DbAccessor.runSql(stmt -> stmt.executeUpdate(insert(name, price)));
        setHeaderSuccess(response);
        response.getWriter().println("OK");
    }
}
