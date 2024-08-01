package javaapplication2;

import java.util.function.Supplier;

public class Fallback {

    /**
     * Wraps a method with fallback to execute it without throws exceptions
     */
    public static <T, E extends Exception> FallbackResponse<T, E> call(Supplier<T> func) {

        try {
            var resp = func.get();
            return new FallbackResponse(resp, null);
        } catch (Exception ex) {
            return new FallbackResponse(null, ex);
        }
    }

}
