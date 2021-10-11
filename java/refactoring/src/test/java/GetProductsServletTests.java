import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.DbAccessor;
import ru.akirakozov.sd.refactoring.Product;
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
        DbAccessor.insertProduct(new Product("transformer", 200));
        testServletTrimmed(Map.of(), s -> assertThat(s).isEqualTo("<html><body>\ntransformer\t200</br>\n</body></html>"));
    }

    @Test
    public void testManyElements() throws IOException, ServletException {
        DbAccessor.insertProduct(new Product("toy1", 200));
        DbAccessor.insertProduct(new Product("toy2", 300));
        DbAccessor.insertProduct(new Product("toy3", 400));
        testServletTrimmed(Map.of(), s -> assertThat(s)
                .isEqualTo("<html><body>\ntoy1\t200</br>\ntoy2\t300</br>\ntoy3\t400</br>\n</body></html>"));
    }
}
