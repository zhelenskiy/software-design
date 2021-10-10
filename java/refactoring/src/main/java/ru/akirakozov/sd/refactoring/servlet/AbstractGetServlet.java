package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractGetServlet extends HttpServlet {
    @Override
    public abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
