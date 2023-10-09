package mod.noobulus;

import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class VanillylTags {

    public static final TagKey<Item> NETHERITE_TOOLS;
    public static final TagKey<Item> GENERIC_BREEDING_ITEMS;
    public static final TagKey<Item> CHICKEN_BREEDING_ITEMS;
    public static final TagKey<Item> HORSE_BREEDING_ITEMS;
    public static final TagKey<Item> HORSE_TREATS;
    public static final TagKey<Item> HORSE_TREATS_SMALL;
    public static final TagKey<Item> HORSE_TREATS_LARGE;
    public static final TagKey<Item> LLAMA_BREEDING_ITEMS;
    public static final TagKey<Item> LLAMA_TREATS;
    public static final TagKey<Item> PARROT_BREEDING_ITEMS;
    public static final TagKey<Item> PIG_BREEDING_ITEMS;
    public static final TagKey<Item> RABBIT_BREEDING_ITEMS;

    private static <E> TagKey<E> create(String pathName, RegistryKey<? extends Registry<E>> registry) {
        return TagKey.of(registry, new Identifier("vanillyl", pathName));
    }

    private VanillylTags() throws InstantiationException {
        throw new InstantiationException("Constant class cannot be instantiate");
    }

    static {
        NETHERITE_TOOLS = create("netherite_tools", RegistryKeys.ITEM);
        GENERIC_BREEDING_ITEMS = create("generic_breeding_items", RegistryKeys.ITEM);
        CHICKEN_BREEDING_ITEMS = create("chicken_breeding_items", RegistryKeys.ITEM);
        HORSE_BREEDING_ITEMS = create("horse_breeding_items", RegistryKeys.ITEM);
        HORSE_TREATS = create("horse_treats", RegistryKeys.ITEM);
        HORSE_TREATS_SMALL = create("horse_treats_small", RegistryKeys.ITEM);
        HORSE_TREATS_LARGE = create("horse_treats_large", RegistryKeys.ITEM);
        LLAMA_BREEDING_ITEMS = create("llama_breeding_items", RegistryKeys.ITEM);
        LLAMA_TREATS = create("llama_treats", RegistryKeys.ITEM);
        PARROT_BREEDING_ITEMS = create("parrot_breeding_items", RegistryKeys.ITEM);
        PIG_BREEDING_ITEMS = create("pig_breeding_items", RegistryKeys.ITEM);
        RABBIT_BREEDING_ITEMS = create("rabbit_breeding_items", RegistryKeys.ITEM);
    }
}
