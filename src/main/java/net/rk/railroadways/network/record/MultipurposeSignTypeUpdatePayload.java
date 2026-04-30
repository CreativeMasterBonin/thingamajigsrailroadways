package net.rk.railroadways.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public record MultipurposeSignTypeUpdatePayload(BlockPos bp, float rotation, float zrot, int id, boolean updateSelf,boolean alternatingTextures) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<MultipurposeSignTypeUpdatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"multipurpose_sign_update"));

    public static final StreamCodec<FriendlyByteBuf, MultipurposeSignTypeUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, MultipurposeSignTypeUpdatePayload::bp,
            ByteBufCodecs.FLOAT, MultipurposeSignTypeUpdatePayload::rotation,
            ByteBufCodecs.FLOAT, MultipurposeSignTypeUpdatePayload::zrot,
            ByteBufCodecs.INT, MultipurposeSignTypeUpdatePayload::id,
            ByteBufCodecs.BOOL,MultipurposeSignTypeUpdatePayload::updateSelf,
            ByteBufCodecs.BOOL,MultipurposeSignTypeUpdatePayload::alternatingTextures,
            MultipurposeSignTypeUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
