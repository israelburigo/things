using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace ConsoleApp2
{
    public sealed class Fallback
    {
        public static FallbackResponse<T> Call<T>(Func<T> action)
        {
            try
            {
                var resp = action();
                return new FallbackResponse<T>(resp, default);
            }
            catch (Exception ex)
            {
                return new FallbackResponse<T>(default, ex);
            }
        }
    }
}
