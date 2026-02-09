package net.rk.railroadways.network.packet;

import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;
import net.rk.railroadways.network.record.RailroadCrossingArmLightedPayload;

import java.util.logging.Logger;

public class RailroadCrossingArmLightedPacket {
    public static final RailroadCrossingArmLightedPacket INSTANCE = new RailroadCrossingArmLightedPacket();

    public static RailroadCrossingArmLightedPacket get(){return INSTANCE;}

    public void handle(final RailroadCrossingArmLightedPayload payload, final IPayloadContext context){
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

        RailroadCrossingArmWithLights rcbe = (RailroadCrossingArmWithLights)lvl.getBlockEntity(payload.bp());

        if(rcbe == null){
            Logger.getAnonymousLogger().warning("Railroad Crossing Arm Lighted BE at: " + payload.bp() + " is null! This is not normal!");
            return;
        }

        rcbe.armLength = payload.gateLength();
        rcbe.yAngle = payload.rotation();
        rcbe.startingArmAngle = payload.startArmAngle();
        rcbe.endArmAngle = payload.endArmAngle();
        rcbe.armGateOffsetZ = payload.gateOffset();

        rcbe.updateBlock();
        return;
    }
}
