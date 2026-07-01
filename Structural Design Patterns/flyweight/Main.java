import java.util.*;

class TreeType {
    private String name;
    private String color;
    private String texture;

    public TreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw(int x, int y) {
        System.out.println("Drawing tree at (" + x + ", " + y + ") with type " + name);
    }
}

class Tree {
    private int x;
    private int y;
    private TreeType treeType;

    public Tree(int x, int y, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }

    public void draw() {
        treeType.draw(x, y);
    }
}

class TreeTypeFactory {
    private static Map<String, TreeType> treeTypeMap = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture) {
        String key = name + "-" + color + "-" + texture;
        if (!treeTypeMap.containsKey(key)) {
            treeTypeMap.put(key, new TreeType(name, color, texture));
        }

        return treeTypeMap.get(key);
    }

}

class Forest {

    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture) {
        Tree tree = new Tree(x, y, TreeTypeFactory.getTreeType(name, color, texture));
        trees.add(tree);
    }

    public void draw() {
        for (Tree tree : trees) {
            tree.draw();
        }
    }
}

class Main {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // Planting 1 million trees
        for (int i = 0; i < 1000000; i++) {
            forest.plantTree(i, i, "Oak", "Green", "Rough");
        }

        System.out.println("Planted 1 million trees.");
    }
}