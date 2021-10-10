import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.IOException;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;


public class AddProductServletTests extends AbstractServletTests<AddProductServlet> {
    public AddProductServletTests() {
        super(AddProductServlet::new, AddProductServlet::doGet);
    }

    @Test
    public void testSimpleAdding() throws IOException {
        testServletTrimmed(
                Map.of("name", "sample_name", "price", "300"),
                resp -> assertThat(resp).isEqualTo("OK")
        );
    }
}
