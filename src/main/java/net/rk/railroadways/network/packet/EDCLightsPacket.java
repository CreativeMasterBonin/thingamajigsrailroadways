package net.rk.railroadways.network.packet;

import com.mojang.logging.LogUtils;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.railroadways.entity.blockentity.custom.EnhancedDirectionalCrossingLightBE;
import net.rk.railroadways.network.record.EDCLightsPayload;

public class EDCLightsPacket {
    public static final EDCLightsPacket INSTANCE = new EDCLightsPacket();
    public static EDCLightsPacket get(){return INSTANCE;}
    public void handle(final EDCLightsPayload payload, final IPayloadContext context) {
        if(payload == null){
            LogUtils.getLogger().error("EDCLightsPayload was null! REPORT THIS TO MOD AUTHOR!");
            return;
        }

        Player ply = context.player();
        Level lvl = ply.level();
        if(ply == null){
            return;
        }

        if(!lvl.hasChunk(
                SectionPos.blockToSectionCoord(payload.blockPos().getX()),
                SectionPos.blockToSectionCoord(payload.blockPos().getZ()))){
            return;
        }

        EnhancedDirectionalCrossingLightBE edcLights = (EnhancedDirectionalCrossingLightBE)lvl.getBlockEntity(payload.blockPos());

        if(edcLights == null){
            LogUtils.getLogger().warn("Enhanced Directional Crossing Light BE at: {} is null! This is not normal!", payload.blockPos());
            return;
        }

        switch((byte)payload.lightsConfig()){
            case 16, 64, 127:{
                edcLights.flasherConfiguration = payload.lightsConfig();
                break;
            }
            default:{
                LogUtils.getLogger().info("Strange byte value received for EDCLightsPacket lights config. Do not edit this value directly.");
                edcLights.flasherConfiguration = (byte)127;
            }
        }

        edcLights.yAngle = Math.clamp(payload.rotation(),0,359);
        edcLights.orangeLightsShowDirectionOfTravel = payload.showDir();
        edcLights.checksNorthSouthTrack = payload.checksNorthSouth();
        edcLights.swapNSEWcheck = payload.swapNSEW();
        edcLights.updateBlock();
    }
}
