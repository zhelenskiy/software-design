import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class QueryServletTests extends AbstractServletTests<QueryServlet> {
    public QueryServletTests() {
        super(QueryServlet::new, QueryServlet::doGet);
    }

    @Test
    public void testManyElements() throws IOException, SQLException {
        runQuery(
                inserter("toy1", 200),
                inserter("toy2", 300),
                inserter("toy3", 400)
        );
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
