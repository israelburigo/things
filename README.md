**Fallbacks**

The Fallback class wraps the try-catch instruction, and can return the result function of the call.
You can use WhenErrorDo's methods to check the error type or only do something when error and continue to return the value that you desire without breaks your flow.

**C#:**
````
decimal someBaseCalc = 0m;
decimal someValue = 10m;

var value = Fallback.Call(() => someValue/someBaseCalc)
    .WhenErrorDo<ArithmeticException>(err => Console.WriteLine($"Error: {err.Message}"))
    .WhenErrorDo(err => Console.WriteLine($"Error: {err.Message}"))     
    .Result();
````

**Java:**
````
Float someBaseCalc = 0f;
Float someValue = 10f;

var value = Fallback.call(() -> someValue/someBaseCalc)
    .whenErrorDo(ArithmeticException.class, err -> System.out.printLn($"Error: {err.Message}"))
    .whenErrorDo(err => System.out.printLn($"Error: {err.Message}"))     
    .result();
