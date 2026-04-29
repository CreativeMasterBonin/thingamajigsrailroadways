package net.rk.railroadways.network.packet;

import com.mojang.logging.LogUtils;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.MultipurposeSignBE;
import net.rk.railroadways.network.record.MultipurposeSignTypeUpdatePayload;

public class MultipurposeSignTypeUpdatePacket {
    public static final MultipurposeSignTypeUpdatePacket INSTANCE = new MultipurposeSignTypeUpdatePacket();

    public static MultipurposeSignTypeUpdatePacket get(){return INSTANCE;}

    public void handle(final MultipurposeSignTypeUpdatePayload payload, final IPayloadContext context){
        Player ply = context.player();
        Level lvl = ply.level();
        if(ply == null){
            return;
        }

        if(!lvl.hasChunk(
                SectionPos.blockToSectionCoord(payload.bp().getX()),
                SectionPos.blockToSectionCoord(payload.bp().getZ()))){
            return;
        }

        MultipurposeSignBE dsbe = (MultipurposeSignBE)lvl.getBlockEntity(payload.bp());

        if(dsbe == null){
            LogUtils.getLogger().warn("Multipurpose Sign BE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }

        if(payload.updateSelf()){
            dsbe.indexId = payload.id();
            dsbe.updateSign();
            dsbe.updateBlock();
        }
        else{
            dsbe.yAngle = payload.rotation();
            dsbe.updateBlock();
        }
        return;
    }
}
