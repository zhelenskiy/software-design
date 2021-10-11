import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.model.DbProductAccessor;
import ru.akirakozov.sd.refactoring.model.ProductPOJO;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

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
        accessor.insertProduct(new ProductPOJO("transformer", 200));
        testServletTrimmed(Map.of(), s -> assertThat(s).isEqualTo("<html><body>\ntransformer\t200</br>\n</body></html>"));
    }

    @Test
    public void testManyElements() throws IOException, ServletException {
        accessor.insertProduct(new ProductPOJO("toy1", 200));
        accessor.insertProduct(new ProductPOJO("toy2", 300));
        accessor.insertProduct(new ProductPOJO("toy3", 400));
        testServletTrimmed(Map.of(), s -> assertThat(s)
                .isEqualTo("<html><body>\ntoy1\t200</br>\ntoy2\t300</br>\ntoy3\t400</br>\n</body></html>"));
    }
}
