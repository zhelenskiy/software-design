package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractProductServlet extends HttpServlet {
    public final AbstractProductAccessor accessor;

    protected AbstractProductServlet(AbstractProductAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public static void setHeaderSuccess(@NotNull HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
