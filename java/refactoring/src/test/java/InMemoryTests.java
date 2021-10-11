import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.model.AbstractProductAccessor;
import ru.akirakozov.sd.refactoring.model.InMemoryProductAccessor;
import ru.akirakozov.sd.refactoring.model.ProductPOJO;

import static com.google.common.truth.Truth.assertThat;

public class InMemoryTests {
    @Test
    void insertAndGetAll() {
        final AbstractProductAccessor accessor = new InMemoryProductAccessor();
        assertThat(accessor.all()).isEmpty();
        final int n = 1000;
        for (int i = 0; i < n; i++) {
            accessor.insertProduct(new ProductPOJO("toy" + i, i));
        }
        assertThat(accessor.all().size()).isEqualTo(n);
        assertThat(accessor.count()).isEqualTo(n);
        assertThat(accessor.min().price).isEqualTo(0);
        assertThat(accessor.max().price).isEqualTo(n - 1);
    }
}
