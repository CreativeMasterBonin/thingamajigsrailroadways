package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record PoleWithCrossingStopLightPayload(BlockPos bp, float rotation, int flashInterval,boolean flashing,boolean horizontal) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PoleWithCrossingStopLightPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"pole_rr_stop_light_update"));

    public static final StreamCodec<FriendlyByteBuf,PoleWithCrossingStopLightPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,PoleWithCrossingStopLightPayload::bp,
            ByteBufCodecs.FLOAT,PoleWithCrossingStopLightPayload::rotation,
            ByteBufCodecs.INT,PoleWithCrossingStopLightPayload::flashInterval,
            ByteBufCodecs.BOOL,PoleWithCrossingStopLightPayload::flashing,
            ByteBufCodecs.BOOL,PoleWithCrossingStopLightPayload::horizontal,
            PoleWithCrossingStopLightPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
