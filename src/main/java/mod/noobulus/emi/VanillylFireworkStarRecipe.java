package mod.noobulus.emi;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import com.google.common.collect.Lists;

import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;


public class VanillylFireworkStarRecipe extends EmiPatternCraftingRecipe {
    private static final List<DyeItem> DYES = Stream.of(DyeColor.values()).map(DyeItem::byColor).toList();

    private static final List<Item> SHAPES = List.of(Items.FIRE_CHARGE, Items.FLINT, Items.GOLD_NUGGET, Items.CARVED_PUMPKIN);

    private static final List<Item> EFFECTS = List.of(Items.REDSTONE, Items.AMETHYST_SHARD);

    public VanillylFireworkStarRecipe(Identifier id) {
        super(List.of(
                        EmiIngredient.of(DYES.stream().map(i -> (EmiIngredient) EmiStack.of(i)).collect(Collectors.toList())),
                        EmiIngredient.of(SHAPES.stream().map(i -> (EmiIngredient) EmiStack.of(i)).collect(Collectors.toList())),
                        EmiIngredient.of(EFFECTS.stream().map(i -> (EmiIngredient) EmiStack.of(i)).collect(Collectors.toList())),
                        EmiStack.of(Items.GUNPOWDER)),
                EmiStack.of(Items.FIREWORK_STAR), id);
    }

    @Override
    public SlotWidget getInputWidget(int slot, int x, int y) {
        if (slot == 0) {
            return new SlotWidget(EmiStack.of(Items.GUNPOWDER), x, y);
        } else {
            final int s = slot - 1;
            return new GeneratedSlotWidget(r -> {
                List<Item> items = getItems(r);
                if (s < items.size()) {
                    return EmiStack.of(items.get(s));
                }
                return EmiStack.EMPTY;
            }, unique, x, y);
        }
    }

    @Override
    public SlotWidget getOutputWidget(int x, int y) {
        return new GeneratedSlotWidget(this::getFireworkStar, unique, x, y);
    }
    private List<DyeItem> getDyes(Random random, int max) {
        List<DyeItem> dyes = Lists.newArrayList();
        int amount = 1 + random.nextInt(max);
        for (int i = 0; i < amount; i++) {
            dyes.add(DYES.get(random.nextInt(DYES.size())));
        }
        return dyes;
    }

    private List<Item> getItems(Random random) {
        List<Item> items = Lists.newArrayList();
        int amount = random.nextInt(4);
        if (amount < 2) {
            items.add(EFFECTS.get(amount));
        } else if (amount == 2) {
            items.add(EFFECTS.get(0));
            items.add(EFFECTS.get(1));
        }
        amount = random.nextInt(5);
        if (amount < 4) {
            items.add(SHAPES.get(amount));
        }

        items.addAll(getDyes(random, 8-items.size()));

        return items;
    }

    private EmiStack getFireworkStar(Random random) {
        ItemStack stack = new ItemStack(Items.FIREWORK_STAR);
        NbtCompound tag = new NbtCompound();
        NbtCompound explosion = new NbtCompound();
        boolean hasShape = false;

        List<Item> items = getItems(random);
        byte smallBall = 0;
        byte largeBall = 1;
        byte star = 2;
        byte creeper = 3;
        byte burst = 4;
        List<Integer> colors = Lists.newArrayList();

        for (Item item : items) {
            if (Items.AMETHYST_SHARD.equals(item)) {
                explosion.putByte("Flicker", largeBall);
            } else if (Items.REDSTONE.equals(item)) {
                explosion.putByte("Trail", largeBall);
            } else if (Items.FIRE_CHARGE.equals(item)) {
                explosion.putByte("Type", largeBall);
                hasShape = true;
            } else if (Items.GOLD_NUGGET.equals(item)) {
                explosion.putByte("Type", star);
                hasShape = true;
            } else if (Items.FLINT.equals(item)) {
                explosion.putByte("Type", burst);
                hasShape = true;
            } else if (SHAPES.contains(item)) {
                explosion.putByte("Type", creeper);
                hasShape = true;
            } else {
                DyeItem dyeItem = (DyeItem) item;
                colors.add(dyeItem.getColor().getFireworkColor());
            }
        }
        if (!hasShape) {
            explosion.putByte("Type", smallBall);
        }

        explosion.putIntArray("Colors", colors);
        tag.put("Explosion", explosion);
        stack.setNbt(tag);
        return EmiStack.of(stack);
    }
}

