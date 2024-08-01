using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace ConsoleApp2
{
    public sealed class FallbackResponse<T>
    {
        private readonly T? result;
        private readonly Exception? error;

        public FallbackResponse(T? result, Exception? error)
        {
            this.result = result;
            this.error = error;
        }

        public FallbackResponse<T2> Call<T2>(Func<T?, T2> action)
        {
            try
            {
                var resp = action(result);
                return new FallbackResponse<T2>(resp, error);
            }
            catch (Exception ex)
            {
                return new FallbackResponse<T2>(default, ex);
            }
        }

        public Exception? Error() => error;
        public T Result(T def) => result == null ? def : result;
        public T? Result() => result;

        public FallbackResponse<T> WhenErrorDo(Action<Exception> act)
        {
            if (error != null)
                act(error);
            return this;
        }

        public FallbackResponse<T> WhenErrorDo<E>(Action<E> act) where E : Exception
        {
            if (error != null && error is E)
                act((E)error);
            return this;
        }

        public FallbackResponse<T> WhenErrorDoAndThrow(Action<Exception?> act)
        {
            if (error != null)
            {
                act(error);
                throw error;
            }
            return this;
        }

        public FallbackResponse<T> WhenErrorDoAndThrow<E>(Action<E> act) where E : Exception
        {
            if (error != null && error is E)
            {
                act((E)error);
                throw error;
            }
            return this;
        }

        public FallbackResponse<T> WhenErrorThrow()
        {
            if (error != null)
                throw error;
            return this;
        }

        public FallbackResponse<T> WhenErrorThrow<E>()
        {
            if (error != null && error is E)
                throw error;
            return this;
        }

    }
}
