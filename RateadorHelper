public class RateadorHelper
{
    private static PropertyInfo? Resolver(Expression exp)
    {
        MemberExpression memberExpression = (MemberExpression)exp;
        if (memberExpression != null)
        {
            return (PropertyInfo)memberExpression.Member;
        }

        if (exp.NodeType == ExpressionType.Convert)
        {
            return Resolver(((UnaryExpression)exp).Operand);
        }
        return null;
    }

    public static void Ratear<T>(List<T> lista, Func<T, decimal> valBase, Expression<Func<T, decimal>> valDest, decimal valorParaRatear, int precisao)
    {
        if (!lista.Any()) return;

        var propDest = Resolver(valDest.Body);
        if (propDest == null) return;
        var funcDest = valDest.Compile();
        var totOrig = lista.Sum(p => valBase(p));
        if (totOrig == 0m) return;

        lista.ForEach(p =>
        {
            var valor = valBase(p) * valorParaRatear / totOrig;
            propDest.SetValue(p, Math.Round(valor, precisao));
        });
        var d = lista.Sum(funcDest);
        var num = valorParaRatear - d;
        if (num != 0m)
        {
            var item = (num > 0m) 
                ? lista.OrderBy(p => funcDest(p)).First() 
                : lista.OrderByDescending(p => funcDest(p)).First();

            propDest.SetValue(item, funcDest(item) + num);
        }
    }
}
