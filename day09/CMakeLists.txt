cmake_minimum_required(VERSION 4.0)
project(day09)

set(CMAKE_CXX_STANDARD 20)

configure_file(input.txt ${CMAKE_BINARY_DIR}/input.txt COPYONLY)

add_executable(day09 main.cpp)
