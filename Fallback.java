
import java.util.function.Supplier;

import java.util.function.Supplier;

public class Fallback {

    public static <T> FallbackResponse<T> call(Supplier<T> func) {

        try {
            var resp = func.get();
            return new FallbackResponse(resp, null);
        } catch (Exception ex) {
            return new FallbackResponse(null, ex);
        }
    }
}
