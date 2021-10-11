import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.akirakozov.sd.refactoring.Main;
import ru.akirakozov.sd.refactoring.servlet.AbstractProductServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.DbAccessor.*;

public class AbstractServletTests<T extends AbstractProductServlet> {
    private final Supplier<T> supplier;

    public AbstractServletTests(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @BeforeAll
    static void startServer() {
        try {
            Main.main(new String[0]);
        } catch (Exception ignored) {
            // is already started
        }
    }

    @BeforeEach
    @AfterEach
    void clearDataBase() {
        runSql(stmt -> stmt.executeUpdate(clearDatabaseQuery));
    }

    protected void testServlet(@NotNull Map<String, String> requestParameters, @NotNull Consumer<String> responseChecker) throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        requestParameters.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter, true));
        supplier.get().doGet(request, response);
        responseChecker.accept(stringWriter.toString());
    }

    protected void testServletTrimmed(@NotNull Map<String, String> requestParameters, @NotNull Consumer<String> responseChecker) throws IOException, ServletException {
        testServlet(requestParameters, s -> responseChecker.accept(s.trim()));
    }
}
