ArrayList<String> readFile(String filepath) {
    ArrayList<String> lines = new ArrayList<>();
    File myObj = new File(filepath);

    try (Scanner myReader = new Scanner(myObj)) {
        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            lines.add(line);
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return lines;
}

void main() {
    ArrayList<String> lines = readFile("input.txt");
    Grid grid = new Grid(lines);
    System.out.println("Part 1: " + grid.countDirectlyAccessible());
    System.out.println("Part 2: " + grid.countAccessibleRecursively());
}
