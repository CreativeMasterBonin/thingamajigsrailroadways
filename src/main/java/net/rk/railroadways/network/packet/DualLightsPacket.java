package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.DualRailwayLightsBE;
import net.rk.railroadways.network.record.DualLightsPayload;

import java.util.logging.Logger;

public class DualLightsPacket{
    public static final DualLightsPacket INSTANCE = new DualLightsPacket();

    public static DualLightsPacket get(){return INSTANCE;}

    public void handle(final DualLightsPayload payload, final IPayloadContext context){
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

        DualRailwayLightsBE brlbe = (DualRailwayLightsBE)lvl.getBlockEntity(payload.bp());

        if(brlbe == null){
            Logger.getAnonymousLogger().warning("DualRailwayLightsBE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }

        brlbe.yAngle = payload.rotation();

        brlbe.updateBlock();
    }
}
