package io.github.thatpreston.mermod.item.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record NecklaceModifier(String id, int color, String texture, int model) {
    public static final Codec<NecklaceModifier> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(NecklaceModifier::id),
                    Codec.INT.fieldOf("color").forGetter(NecklaceModifier::color),
                    Codec.STRING.fieldOf("texture").forGetter(NecklaceModifier::texture),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("model").forGetter(NecklaceModifier::model)
            ).apply(instance, NecklaceModifier::new)
    );
    public static final StreamCodec<ByteBuf, NecklaceModifier> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, NecklaceModifier::id,
            ByteBufCodecs.INT, NecklaceModifier::color,
            ByteBufCodecs.STRING_UTF8, NecklaceModifier::texture,
            ByteBufCodecs.INT, NecklaceModifier::model,
            NecklaceModifier::new
    );
    public NecklaceModifier(String id, int color) {
        this(id, color, "", 0);
    }
    public NecklaceModifier withColor(int color) {
        return new NecklaceModifier(id, color, texture, model);
    }
    public boolean is(NecklaceModifier modifier) {
        return modifier.id().equals(id);
    }
}