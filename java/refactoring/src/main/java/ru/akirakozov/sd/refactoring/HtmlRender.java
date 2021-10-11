package ru.akirakozov.sd.refactoring;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class HtmlRender {
    public enum HeaderState { H1, NORMAL }

    @FunctionalInterface
    public interface ThrowableRunnable {
        void run() throws Exception;
    }
    public static void html(@NotNull PrintWriter writer, String header, HeaderState headerState, ThrowableRunnable body) throws Exception {
        writer.println("<html><body>");
        if (header != null) {
            writer.println(headerState == HeaderState.H1 ? "<h1>" + header + "</h1>" : header);
        }
        body.run();
        writer.println("</body></html>");
    }
}
