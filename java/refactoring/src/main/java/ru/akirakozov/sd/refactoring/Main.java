package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;
import ru.akirakozov.sd.refactoring.model.DbProductAccessor;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final AbstractProductAccessor accessor = new DbProductAccessor();
        accessor.createTableIfNotExists();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(accessor)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(accessor)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(accessor)), "/query");

        server.start();
        server.join();
    }
}
