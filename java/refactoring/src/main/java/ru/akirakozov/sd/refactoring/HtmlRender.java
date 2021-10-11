package ru.akirakozov.sd.refactoring;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;

public class HtmlRender {
    public enum HeaderState { H1, NORMAL }
    public static void html(@NotNull PrintWriter writer, String header, HeaderState headerState, String body) {
        writer.println("<html><body>");
        if (header != null && !header.matches("\\s*")) {
            writer.println(headerState == HeaderState.H1 ? "<h1>" + header + "</h1>" : header);
        }
        if (body != null && !body.matches("\\s*")) {
            writer.println(body);
        }
        writer.println("</body></html>");
    }
}
