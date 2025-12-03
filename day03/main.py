def read_file(filepath):
    file = open(filepath, "r")
    arr = []
    for line in file:
        arr.append(line.strip())
    file.close()
    return arr

def map_data(lines):
    banks = []
    for line in lines:
        bank = []
        for battery in line:
            bank.append(int(battery))
        banks.append(bank)
    return banks

def find_largest_joltage_part1(bank):
    largestIndex = bank.index(max(bank[: -1]))
    largest = bank[largestIndex]
    second = max(bank[largestIndex + 1:])
    return arr_to_num([largest, second])

def find_largest_joltage_part2(bank):
    result = []
    global_index = 0

    for i in range(1, 13):
        possible_range = bank[global_index : len(bank) - 12 + i]
        index = possible_range.index(max(possible_range))
        result.append(bank[global_index + index])
        global_index += index + 1
    return arr_to_num(result)

def arr_to_num(arr):
    num = 0
    for i in range(0, len(arr)):
        num += arr[len(arr) - 1 - i] * 10 ** i
    return num

def main():
    lines = read_file("input.txt")
    banks = map_data(lines)

    sum = 0
    for bank in banks:
        sum += find_largest_joltage_part1(bank)
    print("part1: " + str(sum))

    sum = 0
    for bank in banks:
        sum += find_largest_joltage_part2(bank)
    print("part2: " + str(sum))

main()