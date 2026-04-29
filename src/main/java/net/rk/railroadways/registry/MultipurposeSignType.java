package net.rk.railroadways.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

public record MultipurposeSignType(ResourceLocation assetId,String translationKey, int alternatingInterval,
                                   String offStateTexture, String onStateTexture, String alternateOnStateTexture) {
    public static final Codec<MultipurposeSignType> CODEC = RecordCodecBuilder.create(
            codecBuilderInstance -> codecBuilderInstance.group(
                    ResourceLocation.CODEC.fieldOf("asset_id").forGetter(MultipurposeSignType::assetId),
                    Codec.STRING.fieldOf("translation_key").forGetter(MultipurposeSignType::translationKey),
                    Codec.INT.fieldOf("alternating_interval").forGetter(MultipurposeSignType::alternatingInterval),
                    Codec.STRING.fieldOf("off_state_texture").forGetter(MultipurposeSignType::offStateTexture),
                    Codec.STRING.fieldOf("on_state_texture").forGetter(MultipurposeSignType::onStateTexture),
                    Codec.STRING.fieldOf("alternate_on_state_texture").forGetter(MultipurposeSignType::alternateOnStateTexture)
            ).apply(codecBuilderInstance,MultipurposeSignType::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MultipurposeSignType> DIRECT_STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, MultipurposeSignType::assetId,
                    ByteBufCodecs.STRING_UTF8, MultipurposeSignType::translationKey,
                    ByteBufCodecs.INT, MultipurposeSignType::alternatingInterval,
                    ByteBufCodecs.STRING_UTF8, MultipurposeSignType::offStateTexture,
                    ByteBufCodecs.STRING_UTF8, MultipurposeSignType::onStateTexture,
                    ByteBufCodecs.STRING_UTF8, MultipurposeSignType::alternateOnStateTexture,
                    MultipurposeSignType::new);

    public static final Codec<Holder<MultipurposeSignType>> HOLDER_CODEC =
            RegistryFileCodec.create(TRRRegistries.MULTIPURPOSE_SIGN_TYPE, CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<MultipurposeSignType>> STREAM_CODEC =
            ByteBufCodecs.holder(TRRRegistries.MULTIPURPOSE_SIGN_TYPE, DIRECT_STREAM_CODEC);

    @Override
    public ResourceLocation assetId() {
        return assetId;
    }

    @Override
    public String translationKey() {
        return translationKey;
    }

    @Override
    public int alternatingInterval() {
        return alternatingInterval;
    }

    @Override
    public String offStateTexture() {
        return offStateTexture;
    }

    @Override
    public String onStateTexture() {
        return onStateTexture;
    }

    @Override
    public String alternateOnStateTexture() {
        return alternateOnStateTexture;
    }
}
