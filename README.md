**Fallbacks**

The Fallback class wraps the try-catch instruction and can return the result function of the `Call`.
You can use `WhenErrorDo's` methods to check the error type or only do something when error and continue to return the value that you desire without breaks your flow.

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
    .whenErrorDo(ArithmeticException.class, err -> System.out.printLn($"Error: {err.getMesage()}"))
    .whenErrorDo(err => System.out.printLn($"Error: {err.getMesage()}"))     
    .result();

````
**ApportionHelper**


This helper is usefull to realize an apportion in a list.
````
class Item {
    public decimal SomeValue {get; set;}
    public decimal OtherValue {get; set;}
}

var list = new List<Item>(){
  new Item() { SomeValue = 10 },
  new Item() { SomeValue = 20 },
  new Item() { SomeValue = 30 },
};

ApportionHelper.Do(list, p => p.SomeValue, p => p.OtherValue, 10, 2);
````
The result must be the value `10` distributed on `OtherValue` proportional with `SomeValue`
````
Item 1: OtherValue = 1.67
Item 2: OtherValue = 3.33
Item 3: OtherValue = 5
