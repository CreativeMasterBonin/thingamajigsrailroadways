package net.rk.railroadways.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;

import java.util.function.Supplier;

public class TRRSound{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "thingamajigsrailroadways");

    public static final Supplier<SoundEvent> NEW_EBELL_ONE = SOUND_EVENTS.register("new_ebell_one",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, "new_ebell_one")));

    public static final Supplier<SoundEvent> NEW_EBELL_TWO = SOUND_EVENTS.register("new_ebell_two",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, "new_ebell_two")));

    public static final Supplier<SoundEvent> EBELL_THREE = SOUND_EVENTS.register("ebell_three",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, "ebell_three")));

    public static final Supplier<SoundEvent> EBELL_FOUR = SOUND_EVENTS.register("ebell_four",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, "ebell_four")));

    public static final Supplier<SoundEvent> CLICKY_MECHANICAL_BELL = SOUND_EVENTS.register("clicky_mechanical_bell",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, "clicky_mechanical_bell")));

    public static void register(IEventBus eventBus){SOUND_EVENTS.register(eventBus);}
}
