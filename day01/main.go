package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type rotation struct {
	direction string
	distance int
}

func newRotation(line string) *rotation {
	var dir = string(line[0])
	var dist, _ = strconv.Atoi(line[1:])
	r := rotation {
		direction: dir,
		distance:  dist,
	}
	return &r
}

func readFile(path string) ([]string, error) {
	var file, err = os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var lines []string

	for scanner.Scan() {
		var line = scanner.Text()
		lines = append(lines, line)
	}
	return lines, nil
}

func part1(rotations []string) (int, error) {
	var pos = 50
	var counter = 0
	var dialSize = 100

	for _, strRotation := range rotations {
		rotation := newRotation(strRotation)
		if rotation.direction == "L" {
			pos -= rotation.distance
		} else {
			pos += rotation.distance
		}
		for pos < 0 {
			pos += dialSize
		}
		for pos > dialSize - 1 {
			pos -= dialSize
		}
		if pos == 0 {
			counter++
		}
	}
	return counter, nil
}

func part2(rotations []string) (int, error) {
	var pos = 50
	var counter = 0
	var dialSize = 100

	for _, strRotation := range rotations {
		rotation := newRotation(strRotation)
		if rotation.direction == "L" {
			pos -= rotation.distance
		} else {
			pos += rotation.distance
		}
		for pos < 0 {
			pos += dialSize
			counter++
		}
		for pos > dialSize - 1 {
			pos -= dialSize
			counter++
		}
	}
	return counter, nil
}

func main() {
	var lines, err = readFile("input.txt")
	if err != nil {
		fmt.Println(err.Error())
	}

	var result1, errPart1 = part1(lines)
	if errPart1 == nil {
		fmt.Println("Part 1: ", result1)
	} else {
		fmt.Println(errPart1.Error())
	}

	var result2, errPart2 = part2(lines)
	if errPart2 == nil {
		fmt.Println("Part 2: ", result2)
	} else {
		fmt.Println(errPart2.Error())
	}
}