package io.github.thatpreston.mermod.item.modifier;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.HashMap;

public record NecklaceModifiers(HashMap<String, NecklaceModifier> modifiers) {
    public static final NecklaceModifiers EMPTY = new NecklaceModifiers(new HashMap<>());
    public static final Codec<NecklaceModifiers> CODEC = Codec.unboundedMap(Codec.STRING, NecklaceModifier.CODEC).xmap(map -> new NecklaceModifiers(new HashMap<>(map)), NecklaceModifiers::modifiers);
    public static final StreamCodec<ByteBuf, NecklaceModifiers> STREAM_CODEC = ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, NecklaceModifier.STREAM_CODEC).map(NecklaceModifiers::new, NecklaceModifiers::modifiers);
    public NecklaceModifiers copy() {
        return new NecklaceModifiers(new HashMap<>(modifiers));
    }
    public void add(String type, NecklaceModifier modifier) {
        modifiers.put(type, modifier);
    }
    public ItemStack remove(NecklaceModifierItem item) {
        NecklaceModifier modifier = modifiers.remove(item.getType());
        if(modifier != null) {
            ItemStack stack = item.getDefaultInstance();
            if(stack.is(ItemTags.DYEABLE)) {
                stack.set(DataComponents.DYED_COLOR, new DyedItemColor(modifier.color(), false));
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }
    public boolean canRemove(NecklaceModifierItem item) {
        NecklaceModifier modifier = modifiers.get(item.getType());
        return modifier != null && modifier.is(item.getModifier());
    }
    public NecklaceModifier get(String type) {
        return modifiers.get(type);
    }
    public boolean contains(String type) {
        return modifiers.containsKey(type);
    }
}