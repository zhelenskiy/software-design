import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static ru.akirakozov.sd.refactoring.DbAccessor.insert;
import static ru.akirakozov.sd.refactoring.DbAccessor.runSql;

public class GetProductsServletTests extends AbstractServletTests<GetProductsServlet> {
    public GetProductsServletTests() {
        super(GetProductsServlet::new);
    }

    @Test
    public void testEmpty() throws IOException, ServletException {
        testServletTrimmed(Map.of(), s -> assertThat(s).isEqualTo("<html><body>\n</body></html>"));
    }

    @Test
    public void testOneElement() throws IOException, ServletException {
        runSql(stmt -> stmt.executeUpdate(insert("transformer", 200)));
        testServletTrimmed(Map.of(), s -> assertThat(s).isEqualTo("<html><body>\ntransformer\t200</br>\n</body></html>"));
    }

    @Test
    public void testManyElements() throws IOException, ServletException {
        runSql(stmt -> stmt.executeUpdate(insert("toy1", 200)));
        runSql(stmt -> stmt.executeUpdate(insert("toy2", 300)));
        runSql(stmt -> stmt.executeUpdate(insert("toy3", 400)));
        testServletTrimmed(Map.of(), s -> assertThat(s)
                .isEqualTo("<html><body>\ntoy1\t200</br>\ntoy2\t300</br>\ntoy3\t400</br>\n</body></html>"));
    }
}
