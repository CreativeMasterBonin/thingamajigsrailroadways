package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingLightsBE;
import net.rk.railroadways.network.record.RailroadCrossingLightsPayload;

import java.util.logging.Logger;

public class RailroadCrossingLightsPacket{
    public static final RailroadCrossingLightsPacket INSTANCE = new RailroadCrossingLightsPacket();

    public static RailroadCrossingLightsPacket get(){return INSTANCE;}

    public void handle(final RailroadCrossingLightsPayload payload, final IPayloadContext context){
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

        RailroadCrossingLightsBE rrclbe = (RailroadCrossingLightsBE)lvl.getBlockEntity(payload.bp());

        if(rrclbe == null){
            Logger.getAnonymousLogger().warning("Railroad Crossing Lights BE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }

        rrclbe.showFrontLights = payload.showFrontLights();
        rrclbe.showBackLights = payload.showBackLights();

        rrclbe.yAngle = payload.yAngle();
        rrclbe.frontLeftAngle = payload.frontLeftAngle();
        rrclbe.frontRightAngle = payload.frontRightAngle();
        rrclbe.backLeftAngle = payload.backLeftAngle();
        rrclbe.backRightAngle = payload.backRightAngle();


        rrclbe.updateBlock();
        return;
    }
}
