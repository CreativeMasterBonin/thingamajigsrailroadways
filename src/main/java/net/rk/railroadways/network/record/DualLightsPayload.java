package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record DualLightsPayload(BlockPos bp, float rotation) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<DualLightsPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"dual_lights_update"));

    public static final StreamCodec<FriendlyByteBuf, DualLightsPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, DualLightsPayload::bp,
            ByteBufCodecs.FLOAT, DualLightsPayload::rotation,
            DualLightsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
