use std::fs::read_to_string;

fn read_file(filename: &str) -> Vec<String> {
    read_to_string(filename)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}

fn find_starting_point(line: &String) -> usize {
    line.find("S").unwrap()
}

fn count_splits(lines: &Vec<String>) -> u64 {
    let mut result = 0;
    let mut manifold_end: Vec<u64> = vec![0; lines[0].len()];
    manifold_end[find_starting_point(&lines[0])] = 1;

    for line in lines.iter().skip(1) {
        for i in 0..line.len() {
            if manifold_end[i] != 0 && line.chars().nth(i) == Some('^') {
                result += 1;
                manifold_end[i - 1] = manifold_end[i - 1].wrapping_add(manifold_end[i]);
                manifold_end[i + 1] = manifold_end[i + 1].wrapping_add(manifold_end[i]);
                manifold_end[i] = 0;
            }
        }
    }
    result
}

fn count_timelines(lines: &Vec<String>) -> u64 {
    let mut manifold_end: Vec<u64> = vec![0; lines[0].len()];
    manifold_end[find_starting_point(&lines[0])] = 1;

    for line in lines.iter().skip(1) {
        for i in 0..line.len() {
            if manifold_end[i] != 0 && line.chars().nth(i) == Some('^') {
                manifold_end[i - 1] += manifold_end[i];
                manifold_end[i + 1] += manifold_end[i];
                manifold_end[i] = 0;
            }
        }
    }
    manifold_end.iter().map(|&x| x).sum()
}

fn main() {
    let lines = read_file("input.txt");
    let part1 = count_splits(&lines);
    let part2 = count_timelines(&lines);

    println!("Part 1: {}", part1);
    println!("Part 2: {}", part2);
}
