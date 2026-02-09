package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record RailroadCrossingArmPayload(
        BlockPos bp, float startArmAngle, float endArmAngle, float gateLength, float gateOffset,
        float rotation
) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<RailroadCrossingArmPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"rr_arm_update"));

    public static final StreamCodec<FriendlyByteBuf, RailroadCrossingArmPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, RailroadCrossingArmPayload::bp,
            ByteBufCodecs.FLOAT, RailroadCrossingArmPayload::startArmAngle,
            ByteBufCodecs.FLOAT, RailroadCrossingArmPayload::endArmAngle,
            ByteBufCodecs.FLOAT, RailroadCrossingArmPayload::gateLength,
            ByteBufCodecs.FLOAT, RailroadCrossingArmPayload::gateOffset,
            ByteBufCodecs.FLOAT, RailroadCrossingArmPayload::rotation,
            RailroadCrossingArmPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
