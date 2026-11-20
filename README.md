# MathCore

A small Java math library with an interactive console. You type a function call like `median([3,1,2])`, and MathCore finds the matching static method by reflection, runs it and prints the result.

I built it to practise Java fundamentals: packages, static utility classes, input validation, Javadoc, and reflection-based dispatch.

## Quick start

Requires a JDK (17 or newer).

```bash
javac -d out app/mainClass.java mymath/*.java
java -cp out app.mainClass
```

```text
> pow(2,10)
[R] 1024.0
> median([3,1,2])
[R] 2.0
> arithmean([1,2,3,4],0,1)
[R] 2.5
> geomean([1,4,16],0,1)
[R] 4.0
> tomonthlyrate(12)
[R] .949
```

Arguments can be integers, decimals or arrays written as `[a,b,c]`. Integers are promoted to `double` automatically when a method expects one. Press `Ctrl+C` to quit.

## What's inside

| Class | Functions |
|---|---|
| `mymath.BasicMath` | `pow`, `exp`, `log`, `glog` (log in any base), `abs`, `sqrt`, `cbrt`, `ceil`, `floor`, `floorDiv`, `floorMod`, `IEEEremainder`, `copySigne`, `addExact`, `incrementExact`, `decrementExact`, `getExponent` |
| `mymath.ArrayMath` | `sum`, `product` |
| `mymath.statistic` | `min`, `max`, `midRange`, `median`, and means restricted to a quantile range: `arithmean`, `geomean`, `harmean`, `wmean` (weighted) |
| `mymath.trigonometry` | `sin`, `cos`, `tan`, their reciprocals `csc`, `sec`, `cot`, inverses, hyperbolic forms and their inverses, `atan2`, `hypot`, `degtorad`, `radtodeg` |
| `mymath.finance` | `tomonthlyrate`: converts an annual rate (%) into the equivalent compounded monthly rate (%) |

`pow`, `exp` and `log` are implemented from scratch: `exp` uses a Taylor series and `log` uses Newton's method on `exp`. The other functions mostly wrap `java.lang.Math`.

### Quantile-range means

`arithmean`, `geomean`, `harmean` and `wmean` take a lower and an upper bound in `[0, 1]`. They sort a copy of the data and average only the values in that slice:

- `arithmean(data, 0, 1)` is the ordinary mean.
- `arithmean(data, 0.25, 0.75)` is the interquartile mean, which is robust to outliers.

## Project layout

```
app/mainClass.java   interactive console + reflection dispatcher
mymath/*.java        the library, one class per domain
```

## License

MIT — see [LICENSE](LICENSE).
