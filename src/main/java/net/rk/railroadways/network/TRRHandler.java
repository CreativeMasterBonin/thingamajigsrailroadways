package net.rk.railroadways.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.network.packet.*;
import net.rk.railroadways.network.record.*;

public class TRRHandler {
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar regex_reg = event.registrar(Thingamajigsrailroadways.MODID);
        regex_reg.playToServer(
                RailroadCrossingArmPayload.TYPE,
                RailroadCrossingArmPayload.STREAM_CODEC,
                RailroadCrossingArmPacket.get()::handle);

        regex_reg.playToServer(
                DynamicSignPayload.TYPE,
                DynamicSignPayload.STREAM_CODEC,
                DynamicSignPacket.get()::handle);

        regex_reg.playToServer(
                RailroadCrossingLightsPayload.TYPE,
                RailroadCrossingLightsPayload.STREAM_CODEC,
                RailroadCrossingLightsPacket.get()::handle);

        regex_reg.playToServer(
                BritLightsPayload.TYPE,
                BritLightsPayload.STREAM_CODEC,
                BritLightsPacket.get()::handle);

        regex_reg.playToServer(
                DualLightsPayload.TYPE,
                DualLightsPayload.STREAM_CODEC,
                DualLightsPacket.get()::handle);

        regex_reg.playToServer(
                TriLightsPayload.TYPE,
                TriLightsPayload.STREAM_CODEC,
                TriLightsPacket.get()::handle);

        regex_reg.playToServer(
                RRCantLightsPayload.TYPE,
                RRCantLightsPayload.STREAM_CODEC,
                RRCantLightsPacket.get()::handle);

        regex_reg.playToServer(
                RailroadCrossingArmLightedPayload.TYPE,
                RailroadCrossingArmLightedPayload.STREAM_CODEC,
                RailroadCrossingArmLightedPacket.get()::handle);
    }
}
