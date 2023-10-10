package mod.noobulus.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.util.Identifier;

public class VanillylEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.removeRecipes(new Identifier("minecraft", "firework_star"));
        registry.removeRecipes(new Identifier("minecraft", "firework_rocket"));
        registry.addRecipe(new VanillylFireworkStarRecipe(new Identifier("vanillyl", "firework_star")));
        registry.addRecipe(new VanillylFireworkRocketRecipe(new Identifier("vanillyl", "firework_rocket")));
    }
}
