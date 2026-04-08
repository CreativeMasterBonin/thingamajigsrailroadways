package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.PoleWithCrossingStopLightBE;
import net.rk.railroadways.network.record.PoleWithCrossingStopLightPayload;

public class PoleWithCrossingStopLightPacket {
    public static final PoleWithCrossingStopLightPacket INSTANCE = new PoleWithCrossingStopLightPacket();

    public static PoleWithCrossingStopLightPacket get(){
        return INSTANCE;
    }

    public void handle(final PoleWithCrossingStopLightPayload payload, final IPayloadContext context){
        Player player = context.player();
        if(player == null){
            return;
        }
        Level level = player.level();
        if(!level.hasChunk(SectionPos.blockToSectionCoord(payload.bp().getX()),SectionPos.blockToSectionCoord(payload.bp().getZ()))){
            return;
        }
        else{
            PoleWithCrossingStopLightBE blockEntity = (PoleWithCrossingStopLightBE) level.getBlockEntity(payload.bp());
            if(blockEntity == null){
                return;
            }
            float changeRotRounded = Math.round(payload.rotation());

            blockEntity.yAngle = Mth.clamp(payload.rotation(),-180.0f,180.0f);
            blockEntity.flashingInterval = Mth.clamp(payload.flashInterval(),5,80);
            blockEntity.flashing = payload.flashing();
            blockEntity.currentOrientation = payload.horizontal() ? PoleWithCrossingStopLightBE.Orientation.HORIZONTAL : PoleWithCrossingStopLightBE.Orientation.VERTICAL;
            blockEntity.updateBlock();
        }
    }
}
