package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record RailroadCrossingArmLightedPayload(
        BlockPos bp, float startArmAngle, float endArmAngle, float gateLength, float gateOffset,
        float rotation
) implements CustomPacketPayload{
    public static final Type<RailroadCrossingArmLightedPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"rr_arm_lighted_update"));

    public static final StreamCodec<FriendlyByteBuf, RailroadCrossingArmLightedPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, RailroadCrossingArmLightedPayload::bp,
            ByteBufCodecs.FLOAT, RailroadCrossingArmLightedPayload::startArmAngle,
            ByteBufCodecs.FLOAT, RailroadCrossingArmLightedPayload::endArmAngle,
            ByteBufCodecs.FLOAT, RailroadCrossingArmLightedPayload::gateLength,
            ByteBufCodecs.FLOAT, RailroadCrossingArmLightedPayload::gateOffset,
            ByteBufCodecs.FLOAT, RailroadCrossingArmLightedPayload::rotation,
            RailroadCrossingArmLightedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
