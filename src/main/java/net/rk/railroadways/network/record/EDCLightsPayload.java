package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record EDCLightsPayload(BlockPos blockPos,float rotation,boolean showDir,boolean checksNorthSouth,boolean swapNSEW,byte lightsConfig) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EDCLightsPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"edc_lights_update"));

    public static final StreamCodec<FriendlyByteBuf,EDCLightsPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, EDCLightsPayload::blockPos,
            ByteBufCodecs.FLOAT,EDCLightsPayload::rotation,
            ByteBufCodecs.BOOL,EDCLightsPayload::showDir,
            ByteBufCodecs.BOOL,EDCLightsPayload::checksNorthSouth,
            ByteBufCodecs.BOOL,EDCLightsPayload::swapNSEW,
            ByteBufCodecs.BYTE,EDCLightsPayload::lightsConfig,
            EDCLightsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
