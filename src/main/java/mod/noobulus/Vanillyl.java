package mod.noobulus;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Vanillyl implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("vanillyl");

	public static final UUID REACH_MODIFIER_ID = UUID.fromString("361142ba-be9f-4dcd-82d1-da80c336d0e9");
	public static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("b68216ed-03de-46d8-8acd-31ded1fd956e");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Thanks for playing Vanilla Extract!");

		ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) -> {
			if (stack.isIn(VanillylTags.NETHERITE_TOOLS)) {
				attributeModifiers.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Tool modifier", 2.0, EntityAttributeModifier.Operation.ADDITION));
				attributeModifiers.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Tool modifier", 2.0, EntityAttributeModifier.Operation.ADDITION));
			}
		});
	}
}