package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.WigWagBE;
import net.rk.railroadways.network.record.WigwagPayload;

import java.util.logging.Logger;

public class WigwagPacket{
    public static final WigwagPacket INSTANCE = new WigwagPacket();
    public static WigwagPacket get(){return INSTANCE;}

    public void handle(final WigwagPayload payload, final IPayloadContext context){
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

        WigWagBE wigWag = (WigWagBE)lvl.getBlockEntity(payload.bp());
        if(wigWag == null){
            Logger.getAnonymousLogger().warning("WigwagBE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }
        wigWag.yAngle = payload.rotation();
        wigWag.maxSwingAngle = Mth.clamp((float)payload.maxSwingAngle(),-85.0f,-45.0f);
        wigWag.signalDesignType = (byte)payload.signalDesign();
        wigWag.updateBlock();
    }
}
