package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record DynamicSignPayload(BlockPos bp, float rotation, int sign_type, boolean updateSelf, boolean customTexture, String customTextureLoc) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<DynamicSignPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"dynamic_rr_sign_update"));

    public static final StreamCodec<FriendlyByteBuf, DynamicSignPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, DynamicSignPayload::bp,
            ByteBufCodecs.FLOAT, DynamicSignPayload::rotation,
            ByteBufCodecs.INT, DynamicSignPayload::sign_type,
            ByteBufCodecs.BOOL,DynamicSignPayload::updateSelf,
            ByteBufCodecs.BOOL,DynamicSignPayload::customTexture,
            ByteBufCodecs.STRING_UTF8,DynamicSignPayload::customTextureLoc,
            DynamicSignPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
