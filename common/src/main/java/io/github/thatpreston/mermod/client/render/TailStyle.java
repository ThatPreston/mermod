package io.github.thatpreston.mermod.client.render;

import io.github.thatpreston.mermod.Mermod;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifier;
import io.github.thatpreston.mermod.item.modifier.NecklaceModifiers;
import io.github.thatpreston.mermod.registry.RegistryHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.HashMap;

public record TailStyle(ResourceLocation texture, int model, int tailColor, boolean hasBra, int braColor, boolean hasGradient, int gradientColor, boolean hasGlint, boolean permanent) {
    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Mermod.MOD_ID, "textures/tail/tail.png");
    private static final HashMap<String, ResourceLocation> TAIL_LOCATION_CACHE = new HashMap<>();
    public static TailStyle fromNecklace(ItemStack necklace) {
        NecklaceModifiers component = necklace.get(RegistryHandler.NECKLACE_MODIFIERS.get());
        String textureName = "";
        int model = 0;
        boolean hasBra = false;
        int braColor = 16777215;
        boolean hasGradient = false;
        int gradientColor = 16777215;
        boolean hasGlint = false;
        boolean permanent = false;
        if(component != null) {
            NecklaceModifier tail = component.get("tail");
            NecklaceModifier bra = component.get("bra");
            NecklaceModifier gradient = component.get("gradient");
            if(tail != null) {
                textureName = tail.texture();
                model = tail.model();
            }
            if(bra != null) {
                hasBra = true;
                braColor = bra.color();
            }
            if(gradient != null) {
                hasGradient = true;
                gradientColor = gradient.color();
            }
            hasGlint = component.contains("glint");
            permanent = component.contains("permanent");
        }
        int tailColor = DyedItemColor.getOrDefault(necklace, 16777215);
        ResourceLocation texture = textureName.isEmpty() ? DEFAULT_TEXTURE : getTailLocation(textureName, tailColor != 16777215 || braColor != 16777215 || hasGradient);
        return new TailStyle(texture, model, tailColor, hasBra, braColor, hasGradient, gradientColor, hasGlint, permanent);
    }
    private static ResourceLocation getTailLocation(String name, boolean colorable) {
        String string = "textures/tail/" + name + (colorable ? "_colorable" : "") + ".png";
        return TAIL_LOCATION_CACHE.computeIfAbsent(string, path -> new ResourceLocation(Mermod.MOD_ID, path));
    }
}