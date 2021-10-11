import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static ru.akirakozov.sd.refactoring.DbAccessor.insert;
import static ru.akirakozov.sd.refactoring.DbAccessor.runSql;

public class QueryServletTests extends AbstractServletTests<QueryServlet> {
    public QueryServletTests() {
        super(QueryServlet::new);
    }

    @Test
    public void testManyElements() throws IOException, ServletException {
        runSql(stmt -> stmt.executeUpdate(insert("toy1", 200)));
        runSql(stmt -> stmt.executeUpdate(insert("toy2", 300)));
        runSql(stmt -> stmt.executeUpdate(insert("toy3", 400)));
        for (Map.Entry<String, String> pair : Map.of(
                "max", "<html><body>\n<h1>Product with max price: </h1>\ntoy3\t400</br>\n</body></html>",
                "min", "<html><body>\n<h1>Product with min price: </h1>\ntoy1\t200</br>\n</body></html>",
                "sum", "<html><body>\nSummary price: \n900\n</body></html>",
                "count", "<html><body>\nNumber of products: \n3\n</body></html>",
                "akf", "Unknown command: akf"
        ).entrySet()) {
            testServletTrimmed(Map.of("command", pair.getKey()), s -> assertThat(s).isEqualTo(pair.getValue()));
        }
    }
}
