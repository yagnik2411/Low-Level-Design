import java.util.ArrayList;
import java.util.List;

interface CartItem {
    public double getPrice();

    public void display(String indent);
}

class Product implements  CartItem{
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void display(String indent) {
        System.out.println(indent + "product:" + name + " - " + price);
    }
}

class ProductBundle  implements CartItem{
    private String bundleName;
    private List<CartItem> products = new ArrayList<>();

    public ProductBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public void addProduct(CartItem product) {
        products.add(product);
    }

    public double getPrice() {
        double total = 0;
        for (CartItem p : products) {
            total += p.getPrice();
        }
        return total;
    }

    public void display(String indent) {
        System.out.println(indent + " bundle :" + bundleName);
        for (CartItem p : products) {
            p.display(indent + " ");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Individual Items
        CartItem book = new Product("Book", 500);
        CartItem headphones = new Product("Headphones", 1500);
        CartItem charger = new Product("Charger", 800);
        CartItem pen = new Product("Pen", 20);
        CartItem notebook = new Product("Notebook", 60);

        // Bundle: Iphone Combo
        ProductBundle iphoneCombo = new ProductBundle("iPhone Combo Pack");
        iphoneCombo.addProduct(headphones);
        iphoneCombo.addProduct(charger);

        // Bundle: School Kit
        ProductBundle schoolKit = new ProductBundle("School Kit");
        schoolKit.addProduct(pen);
        schoolKit.addProduct(notebook);

        // Add to cart logic
        List<CartItem> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display Cart
        double total = 0;
        System.out.println("Cart Details:\n");

        for (CartItem item : cart) {
            item.display(" ");
            total += item.getPrice();
        }

        System.out.println("\nTotal Price: ₹" + total);
    }
}
