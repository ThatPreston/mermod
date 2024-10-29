package io.github.thatpreston.mermod.fabric;

import io.github.thatpreston.mermod.client.render.TailStyle;
import io.github.thatpreston.mermod.fabric.compat.TrinketsCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MermodPlatformImpl {
    public static ItemStack getNecklaceFromAccessorySlot(Player player) {
        if(MermodFabric.trinketsLoaded) {
            return TrinketsCompat.getNecklace(player);
        }
        return ItemStack.EMPTY;
    }
    public static TailStyle getTailStyle(Player player) {
        return null;
    }
    public static boolean hasTailStyle(Player player) {
        return false;
    }
    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}