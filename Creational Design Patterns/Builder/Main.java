import java.util.List;

class BurgerMeal {
    // required
    private final String bun;
    private final String patty;
    // optional
    private final String side;
    private final String drink;
    private final boolean isExtraCheese;
    private final List<String> toppings;

    private BurgerMeal(BurgerBuilder burgerBuilder) {
        this.bun = burgerBuilder.bun;
        this.patty = burgerBuilder.patty;
        this.side = burgerBuilder.side;
        this.drink = burgerBuilder.drink;
        this.isExtraCheese = burgerBuilder.isExtraCheese;
        this.toppings = burgerBuilder.toppings;
    }

    public static class BurgerBuilder {
        // required
        private final String bun;
        private final String patty;
        // optional
        private String side;
        private String drink;
        private boolean isExtraCheese;
        private List<String> toppings;

        BurgerBuilder(String bun, String patty) {
            this.bun = bun;
            this.patty = patty;
        }

        public BurgerBuilder addDrink(String drink) {
            this.drink = drink;
            return this;
        }

        public BurgerBuilder addSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerBuilder addIsExtraCheese(boolean isExtraCheese) {
            this.isExtraCheese = isExtraCheese;
            return this;
        }

        public BurgerBuilder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        public BurgerMeal build() {
            return new BurgerMeal(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BurgerMeal burgerMeal = new BurgerMeal.BurgerBuilder("wheat", "veg").build();
    }
}
