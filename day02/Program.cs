async Task<string> ReadFile(string file)
{
    using StreamReader reader = new(file);
    return await reader.ReadToEndAsync();
}

List<(long, long)> MapData(string data)
    => data.Split(",")
        .Select(x =>
        {
            var split = x.Split("-");
            return (long.Parse(split[0]), long.Parse(split[1]);
        }
        )
        .ToList();

bool IsInvalidIdPart1(long id)
{
    var str = $"{id}";
    if (str.Length % 2 != 0)
    {
        return false;
    }
    var first = str.Substring(0, str.Length / 2);
    var second  = str.Substring(str.Length / 2);
    return first == second;
}

bool IsInvalidIdPart2(long id)
{
    List<string> divide(string input, int n)
    {
        var parts = new List<string>();
        for (var i = 0; i < input.Length; i += n)
        {
            var size = Math.Min(n, input.Length - i);
            parts.Add(input.Substring(i, size));
        }
        return parts;
    }

    List<int> getFactors(int n)
    {
        var result = new HashSet<int>();
        for(var i = 1; i < n; ++i)
        {
            if (n % i == 0)
            {
                result.Add(i);
            }
        }
        return result.ToList();
    }

    var str = $"{id}";
    var factors = getFactors(str.Length);
    foreach (var factor in factors)
    {
        var subparts = divide(str, factor);
        if (subparts.All(x => x == str.Substring(0, factor)))
        {
            return true;
        }
    }
    return false;
}

List<long> GetInvalidIds(List<(long, long)> list, Func<long, bool> isInvalid)
{
    var result = new List<long>();
    foreach (var (lower, upper) in list)
    {
        for(long i = lower; i <= upper; ++i)
        {
            if (isInvalid(i))
            {
                result.Add(i);
            }
        }
    }
    return result;
}

var text = await ReadFile("input.txt");
var data = MapData(text);

var invalidIds1 = GetInvalidIds(data, IsInvalidIdPart1);
var invalidIds2 = GetInvalidIds(data, IsInvalidIdPart2);

var sum1 = invalidIds1.Aggregate((long)0, (sum, num) => sum + num);
var sum2 = invalidIds2.Aggregate((long)0, (sum, num) => sum + num);

Console.WriteLine($"[Part 1] sum: {sum1}");
Console.WriteLine($"[Part 2] sum: {sum2}");