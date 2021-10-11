package ru.akirakozov.sd.refactoring.servlet;

import org.jetbrains.annotations.NotNull;
import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;
import ru.akirakozov.sd.refactoring.HtmlRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.H1;
import static ru.akirakozov.sd.refactoring.HtmlRender.HeaderState.NORMAL;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    public QueryServlet(AbstractProductAccessor accessor) {
        super(accessor);
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        final PrintWriter writer = response.getWriter();
        if ("max".equals(command)) {
            maxTo(writer);
        } else if ("min".equals(command)) {
            minTo(writer);
        } else if ("sum".equals(command)) {
            sumTo(writer);
        } else if ("count".equals(command)) {
            countTo(writer);
        } else {
            writer.println("Unknown command: " + command);
        }

        setHeaderSuccess(response);
    }

    private void countTo(@NotNull PrintWriter writer) {
        HtmlRender.html(writer, "Number of products: ", NORMAL, Long.toString(accessor.count()));
    }

    private void sumTo(@NotNull PrintWriter writer) {
        HtmlRender.html(writer, "Summary price: ", NORMAL, Long.toString(accessor.sum()));
    }

    private void minTo(@NotNull PrintWriter writer) {
        HtmlRender.html(writer, "Product with min price: ", H1, accessor.min().toString());
    }

    private void maxTo(@NotNull PrintWriter writer) {
        HtmlRender.html(writer, "Product with max price: ", H1, accessor.max().toString());
    }
}
