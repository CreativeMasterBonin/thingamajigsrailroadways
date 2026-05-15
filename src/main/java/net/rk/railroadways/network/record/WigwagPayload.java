package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record WigwagPayload(BlockPos bp, float rotation, float maxSwingAngle, byte signalDesign) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WigwagPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"wigwag_update"));

    public static final StreamCodec<FriendlyByteBuf, WigwagPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, WigwagPayload::bp,
            ByteBufCodecs.FLOAT, WigwagPayload::rotation,
            ByteBufCodecs.FLOAT, WigwagPayload::maxSwingAngle,
            ByteBufCodecs.BYTE,WigwagPayload::signalDesign,
            WigwagPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
