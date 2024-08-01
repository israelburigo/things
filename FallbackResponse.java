
import java.util.function.Consumer;
import java.util.function.Function;

public final class FallbackResponse<T> {

    private final T result;
    private final Exception error;

    public FallbackResponse(T result, Exception error) {
        this.result = result;
        this.error = error;
    }

    public <T2> FallbackResponse<T2> call(Function<T, T2> func) {
        try {
            var response = func.apply(this.result);
            return new FallbackResponse(response, null);
        } catch (Exception ex) {
            return new FallbackResponse(null, error);
        }
    }

    public FallbackResponse<T> whenErrorDo(Consumer<Exception> act) {
        if (this.error != null) {
            act.accept(this.error);
        }
        return this;
    }

    public <E extends Exception> FallbackResponse<T> whenErrorDo(Class<E> errorType, Consumer<E> act) {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            act.accept((E)this.error);
        }
        return this;
    }

    public FallbackResponse<T> whenErrorDoAndThrow(Consumer<Exception> act) throws Exception {
        if (this.error != null) {
            act.accept(this.error);
            throw this.error;
        }
        return this;
    }

    public <E extends Exception> FallbackResponse<T> whenErrorDoAndThrow(Class<E> errorType, Consumer<E> act) throws E {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            act.accept((E) this.error);
            throw (E)this.error;
        }
        return this;
    }

    public FallbackResponse<T> whenErrorThrow() throws Exception {
        if (this.error != null) {
            throw this.error;
        }
        return this;
    }

    public <E extends Exception> FallbackResponse<T> whenErrorThrow(Class<E> errorType) throws E {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            throw (E)this.error;
        }
        return this;
    }

    public T getResult() {
        return result;
    }

    public T getResult(T def) {
        return result == null ? def : result;
    }

    public Exception getError() {
        return error;
    }

}
