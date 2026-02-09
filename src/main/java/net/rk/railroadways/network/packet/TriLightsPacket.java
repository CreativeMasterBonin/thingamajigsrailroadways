package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.TriRailwayLightsBE;
import net.rk.railroadways.network.record.TriLightsPayload;

import java.util.logging.Logger;

public class TriLightsPacket{
    public static final TriLightsPacket INSTANCE = new TriLightsPacket();

    public static TriLightsPacket get(){return INSTANCE;}

    public void handle(final TriLightsPayload payload, final IPayloadContext context){
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

        TriRailwayLightsBE brlbe = (TriRailwayLightsBE)lvl.getBlockEntity(payload.bp());

        if(brlbe == null){
            Logger.getAnonymousLogger().warning("TriRailwayLightsBE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }

        brlbe.yAngle = payload.rotation();

        brlbe.updateBlock();
    }
}
