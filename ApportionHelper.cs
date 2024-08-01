 public class ApportionHelper
 {
     private static PropertyInfo? Resolve(Expression exp)
     {
         MemberExpression memberExpression = (MemberExpression)exp;
         if (memberExpression != null)
             return (PropertyInfo)memberExpression.Member;

         if (exp.NodeType == ExpressionType.Convert)
             return Resolve(((UnaryExpression)exp).Operand);
         return null;
     }

     public static void Do<T>(List<T> list, Func<T, decimal> valBase, Expression<Func<T, decimal>> valDest, decimal valueToApportion, int precision)
     {
         if (!list.Any()) return;

         var propDest = Resolve(valDest.Body);
         if (propDest == null) return;
         var funcDest = valDest.Compile();
         var sumBase = list.Sum(p => valBase(p));
         if (sumBase == 0m) return;

         list.ForEach(p =>
         {
             var value = valBase(p) * valueToApportion / sumBase;
             propDest.SetValue(p, Math.Round(value, precision));
         });
         var sumDest = list.Sum(funcDest);
         var diff = valueToApportion - sumDest;
         if (diff != 0m)
         {
             var item = (diff > 0m) 
                 ? list.OrderBy(p => funcDest(p)).First() 
                 : list.OrderBy(p => funcDest(p)).Last();

             propDest.SetValue(item, funcDest(item) + diff);
         }
     }
 }
