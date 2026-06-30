
interface Pizza {
    double getCost();

    String getDescription();
}

class MargaritaPizza implements Pizza {
    public double getCost() {
        return 200;
    }

    @Override
    public String getDescription() {
        return "MargaritaPizza";
    }
}

abstract class PizzaDecorator implements Pizza {

    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

}

class ExtraCheese extends PizzaDecorator {
    ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getCost() {
        return 40 + pizza.getCost();
    }

    @Override
    public String getDescription() {
        return "extra cheese with" + pizza.getDescription();
    }

}

public class Main {

    public static void main(String[] args) {
        
    }
}