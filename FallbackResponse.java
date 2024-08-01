package javaapplication2;

import java.util.function.Consumer;
import java.util.function.Function;

public final class FallbackResponse<R, E extends Exception> {

    private final R result;
    private final E error;

    public FallbackResponse(R result, E error) {
        this.result = result;
        this.error = error;
    }

    public <T> FallbackResponse<T, E> call(Function<R, T> func) {
        try {
            var response = func.apply(this.result);
            return new FallbackResponse(response, null);
        } catch (Exception ex) {
            return new FallbackResponse(null, error);
        }
    }

    public FallbackResponse<R, E> whenErrorDo(Consumer<E> act) {
        if (this.error != null) {
            act.accept(this.error);
        }
        return this;
    }

    public <T extends Exception> FallbackResponse<R, E> whenErrorDo(Class<T> errorType, Consumer<T> act) {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            act.accept((T)this.error);
        }
        return this;
    }

    public FallbackResponse<R, E> whenErrorDoAndThrow(Consumer<E> act) throws E {
        if (this.error != null) {
            act.accept(this.error);
            throw this.error;
        }
        return this;
    }

    public <T extends Exception> FallbackResponse<R, E> whenErrorDoAndThrow(Class<T> errorType, Consumer<T> act) throws E {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            act.accept((T) this.error);
            throw this.error;
        }
        return this;
    }

    public FallbackResponse<R, E> whenErrorThrow() throws E {
        if (this.error != null) {
            throw this.error;
        }
        return this;
    }

    public <T extends Exception> FallbackResponse<R, E> whenErrorThrow(Class<T> errorType) throws E {
        if (this.error != null && errorType.isAssignableFrom(this.error.getClass())) {
            throw this.error;
        }
        return this;
    }

    public R getResult() {
        return result;
    }

    public R getResult(R def) {
        return result == null ? def : result;
    }

    public E getError() {
        return error;
    }

}
