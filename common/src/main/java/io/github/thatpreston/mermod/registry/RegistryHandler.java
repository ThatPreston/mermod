package io.github.thatpreston.mermod.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifiers;
import io.github.thatpreston.mermod.item.SeaNecklaceItem;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifierItem;
import io.github.thatpreston.mermod.recipe.NecklaceModifierRecipe;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.UnaryOperator;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Mermod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> SEA_NECKLACE = ITEMS.register("sea_necklace", SeaNecklaceItem::new);
    public static final RegistrySupplier<Item> SEA_CRYSTAL = ITEMS.register("sea_crystal", () -> new Item(new Item.Properties().arch$tab(CreativeModeTabs.INGREDIENTS)));
    public static final RegistrySupplier<Item> MERMAID_BRA_MODIFIER = registerModifier("bra", new NecklaceModifier("mermaid_bra", 0));
    public static final RegistrySupplier<Item> GLOWING_PEARL_MODIFIER = registerModifier("glint", new NecklaceModifier("glowing_pearl", 16777060));
    public static final RegistrySupplier<Item> TAIL_GRADIENT_MODIFIER = registerModifier("gradient", new NecklaceModifier("tail_gradient", 0));
    public static final RegistrySupplier<Item> TAIL_MOISTURIZER_MODIFIER = registerModifier("permanent", new NecklaceModifier("tail_moisturizer", 15723519));
    public static final RegistrySupplier<Item> MOON_ROCK_MODIFIER = registerModifier("tail", new NecklaceModifier("moon_rock", 16755968, "h2o", 1));
    public static final RegistrySupplier<Item> URSULA_SHELL_MODIFIER = registerModifier("tail", new NecklaceModifier("ursula_shell", 16768000, "ariel", 0));
    public static final RegistrySupplier<Item> DEEP_SEA_CRYSTAL_MODIFIER = registerModifier("tail", new NecklaceModifier("deep_sea_crystal", 4608611, "siren", 2));
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Mermod.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final RegistrySupplier<RecipeSerializer<NecklaceModifierRecipe>> NECKLACE_MODIFIER_SERIALIZER = RECIPES.register("crafting_special_necklace_modifier", () -> new SimpleCraftingRecipeSerializer<>(NecklaceModifierRecipe::new));
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(Mermod.MOD_ID, Registries.DATA_COMPONENT_TYPE);
    public static final RegistrySupplier<DataComponentType<NecklaceModifiers>> NECKLACE_MODIFIERS = registerComponentType("necklace_modifiers", builder -> builder.persistent(NecklaceModifiers.CODEC).networkSynchronized(NecklaceModifiers.STREAM_CODEC));
    public static void register() {
        ITEMS.register();
        RECIPES.register();
        COMPONENT_TYPES.register();
    }
    private static RegistrySupplier<Item> registerModifier(String type, NecklaceModifier modifier) {
        return ITEMS.register(modifier.id() + "_modifier", () -> new NecklaceModifierItem(type, modifier));
    }
    public static <T> RegistrySupplier<DataComponentType<T>> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}