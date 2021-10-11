package ru.akirakozov.sd.refactoring;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class HtmlRender {
    public enum HeaderState { H1, NORMAL }
    public static void html(@NotNull PrintWriter writer, String header, HeaderState headerState, Runnable body) {
        writer.println("<html><body>");
        if (header != null) {
            writer.println(headerState == HeaderState.H1 ? "<h1>" + header + "</h1>" : header);
        }
        body.run();
        writer.println("</body></html>");
    }
}
