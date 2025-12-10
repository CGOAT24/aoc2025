#include <iostream>
#include <fstream>
#include <regex>
#include <set>
#include <vector>
#include <string>
#include <sstream>

struct Point {
    size_t x, y;

    Point(const size_t x, const size_t y) : x(x), y(y) {}

    bool operator<(const Point& other) const {
        if (this->x != other.x) return this->x < other.x;
        return this->y < other.y;
    }
};

std::vector<std::string> readFile(const std::string& fileName) {
    std::ifstream f(fileName);

    if (!f.is_open()) {
        std::cerr << "Error opening the file!" << std::endl;
    }

    std::vector<std::string> v{};
    std::string s;
    while (std::getline(f, s)) {
        v.push_back(s);
    }

    f.close();
    return v;
}

std::set<Point> mapData(const std::vector<std::string> &lines) {
    std::set<Point> points{};

    for (const auto &line : lines) {
        std::istringstream iss(line);
        std::string token;

        std::getline(iss, token, ',');
        const size_t x = std::stoul(token);

        std::getline(iss, token, ',');
        const size_t y = std::stoul(token);

        Point point{x, y};
        points.insert(point);
    }
    return points;
}

long calculateArea(const Point p1, const Point p2) {
    return (std::abs(static_cast<ptrdiff_t>(p2.y - p1.y)) + 1)
        * (std::abs(static_cast<ptrdiff_t>(p2.x - p1.x)) + 1);
}

long findLargestArea(const std::vector<Point> &points) {
    long maxArea(0);
    for (int i(0); i < points.size(); ++i) {
        for (int j(0); j < points.size(); ++j) {
            if (i == j) { continue; }

            if (const auto area = calculateArea(points[i], points[j]); area > maxArea) {
                maxArea = area;
            }
        }
    }
    return maxArea;
}

bool isPointInPolygon(const Point p, const std::vector<Point> &polygon) {
    //TODO
    return false;
}

bool arePointsInPolygon(const Point p1, const Point p2, const std::vector<Point>& polygon) {
    const auto startX = std::min(p1.x, p2.x);
    const auto startY = std::min(p1.y, p2.y);
    const auto endX = std::max(p1.x, p2.x);
    const auto endY = std::max(p1.y, p2.y);

    for (auto i(startX); i <= endX; ++i) {
        for (auto j(startY); j <= endY; ++j) {
            Point p{i, j};
            if (!isPointInPolygon(p, polygon)) {
                return false;
            }
        }
    }
    return true;
}

long findLargestColoredArea(const std::vector<Point> &points) {
    long maxArea(0);
    for (int i(0); i < points.size(); ++i) {
        for (int j(0); j < points.size(); ++j) {
            if (i == j) { continue; }
            if (!arePointsInPolygon(points[i], points[j], points)) { continue; }
            if (const auto area = calculateArea(points[i], points[j]); area > maxArea ) {
                maxArea = area;
            }
        }
    }
    return maxArea;
}

int main() {
    auto lines = readFile("input.txt");
    auto points = mapData(lines);
    const std::vector vec(points.begin(), points.end());
    const auto largest = findLargestArea(vec);

    std::cout << "Part 1: " << largest << std::endl;
    std::cout << "Part 2: " << findLargestColoredArea(vec) << std::endl;

    return 0;
}