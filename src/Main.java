import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    private static final Ingredient MILK = new Ingredient("Milk", "ml");
    private static final Ingredient POWDER = new Ingredient("Powder", "g");
    private static final Ingredient EGG = new Ingredient("Egg", "each");
    private static final Ingredient SUGAR = new Ingredient("Sugar", "g");
    private static final Ingredient OIL = new Ingredient("Oil", "ml");
    private static final Ingredient APPLE = new Ingredient("Apple", "each");
    private static final Ingredient CREATIVITY = new Ingredient("Creativity", "person");

    private static final List<Recipe> RECIPES = List.of(
            new Recipe("Apple juice",
                    pick(APPLE, 8)),
            new Recipe("Pancakes",
                    pick(POWDER, 400),
                    pick(SUGAR, 10),
                    pick(MILK, 1000),
                    pick(OIL, 30)),
            new Recipe("Omelette",
                    pick(MILK, 300),
                    pick(POWDER, 5),
                    pick(EGG, 5)),
            new Recipe("Apple pie",
                    pick(APPLE, 5),
                    pick(MILK, 100),
                    pick(POWDER, 300),
                    pick(EGG, 4)));

    public static void main(String[] args)
    {
        final var inventory = List.of(
                pick(MILK, 200),
                pick(POWDER, 5),
                pick(EGG, 3),
                pick(SUGAR, 5),
                pick(OIL, 30),
                pick(APPLE, 8));

        final var availableRecipes = RECIPES.stream()
                .filter(recipe -> recipe.ingredients.stream()
                        .allMatch(required -> inventory.stream()
                                .anyMatch(available -> available.isEnough(required))))
                .collect(Collectors.toList());

        if (availableRecipes.isEmpty())
        {
            System.out.println("Your ingredients are not enough for any recipe :(");
        }
        else
        {
            System.out.println("Good news, you can use your ingredients to make:");
            availableRecipes.forEach(recipe -> {
                System.out.println(recipe.name);
                recipe.ingredients.forEach(stack ->
                        System.out.printf(" - %s, %s %s%n",
                                stack.ingredient.name,
                                stack.amount,
                                stack.ingredient.measurements));
            });
        }
    }

//    public static void main(String[] args)
//    {
//        int milkAmount = 200; // ml
//        int powderAmount = 5; // g
//        int eggsCount = 3; // items
//        int sugarAmount = 5; // g
//        int oilAmount = 30; // ml
//        int appleCount = 8; // items
//
//        // Example
//        // apples - 5
//        if (appleCount >= 5)
//        {
//            System.out.println("Apple juice");
//        }
//
//        // powder - 400 g, sugar - 10 g, milk - 1 l, oil - 30 ml
//        System.out.println("Pancakes");
//
//        // milk - 300 ml, powder - 5 g, eggs - 5
//        System.out.println("Omelette");
//
//        // apples - 3, milk - 100 ml, powder - 300 g, eggs - 4
//        System.out.println("Apple pie");
//    }

    private static IngredientStack pick(Ingredient ingredient, int amount)
    {
        return new IngredientStack(ingredient, amount);
    }

    private static class Ingredient
    {
        private final String name;
        private final String measurements;

        private Ingredient(String name, String measurements)
        {
            this.name = name;
            this.measurements = measurements;
        }
    }

    private static class IngredientStack
    {
        private final Ingredient ingredient;
        private final int amount;

        private IngredientStack(Ingredient ingredient, int amount)
        {
            this.ingredient = ingredient;
            this.amount = amount;
        }

        private boolean isEnough(IngredientStack required)
        {
            return this.ingredient == required.ingredient
                    && this.amount >= required.amount;
        }
    }

    private static class Recipe
    {
        private final String name;
        private final List<IngredientStack> ingredients;

        private Recipe(String name, IngredientStack... ingredients)
        {
            this.name = name;
            this.ingredients = List.of(ingredients);
        }
    }
}