defmodule FileReader do
  def map_data(file) do
    grouped = Enum.group_by(split_lines(read(file)), fn str -> {
      if str == "" do
        :ignore
      else
        if String.contains?(str, "-") do
          :range
        else
          :ids
        end
      end
    } end)
    {
      Enum.map(grouped[{:range}], fn x ->
        Enum.map(String.split(x, "-"), fn y -> elem(Integer.parse(y), 0) end)
      end),
      Enum.map(grouped[{:ids}], fn x -> elem(Integer.parse(x), 0) end)
    }
  end

  defp split_lines(text) do
    String.split(text, ~r{\n})
  end

  defp read(file) do
    case File.read(file) do
      {:ok, body} -> body
      {:error, reason} -> IO.puts(reason)
    end
  end
end

defmodule IMS do
  def total_fresh_ids(ranges) do
    Enum.reduce(Enum.map(merge(ranges), fn x -> Enum.at(x, 1) - Enum.at(x, 0) + 1 end), 0, fn x, acc -> acc + x end)
  end

  def count_fresh(data) do
    Enum.count(Enum.filter(elem(data, 1), fn x -> is_fresh?(x, elem(data, 0)) end))
  end

  def is_fresh?(id, ranges) do
    Enum.any?(ranges, fn range -> id >= Enum.at(range, 0) && id <= Enum.at(range, 1) end)
  end

  defp merge(ranges) do
    ranges
    |> Enum.sort_by(fn [start, _end] -> start end)
    |> merge_sorted_ranges([])
  end

  defp merge_sorted_ranges([], acc), do: acc

  defp merge_sorted_ranges([range | rest], []) do
    merge_sorted_ranges(rest, [range])
  end

  defp merge_sorted_ranges([range | rest], [prev | acc]) do
    [p_start, p_end] = prev
    [c_start, c_end] = range

    if c_start <= p_end do
      merged = [p_start, max(p_end, c_end)]
      merge_sorted_ranges(rest, [merged | acc])
    else
      merge_sorted_ranges(rest, [range, prev | acc])
    end
  end
end

data = FileReader.map_data("input.txt")
fresh_count = IMS.count_fresh(data)
total_fresh = IMS.total_fresh_ids(elem(data, 0))

IO.puts("part 1: #{fresh_count}")
IO.puts("part 2: #{total_fresh}")
