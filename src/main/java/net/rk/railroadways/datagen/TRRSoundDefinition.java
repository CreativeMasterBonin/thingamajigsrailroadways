package net.rk.railroadways.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.util.TRRSound;

public class TRRSoundDefinition extends SoundDefinitionsProvider {
    protected TRRSoundDefinition(PackOutput output, ExistingFileHelper helper) {
        super(output, Thingamajigsrailroadways.MODID, helper);
    }

    @Override
    public String getName() {
        return "Railroadways Sound Definitions";
    }

    @Override
    public void registerSounds() {
        this.add(TRRSound.NEW_EBELL_ONE, SoundDefinition.definition().with(
                sound("thingamajigsrailroadways:block/ebell_one")
                        .volume(1.0f)
                        .pitch(1.0f)
                        .attenuationDistance(12)
                        .stream(false)
                        .preload(true)
        ).subtitle("thingamajigsrailroadways.subtitle.ebell_ring"));

        this.add(TRRSound.NEW_EBELL_TWO, SoundDefinition.definition().with(
                sound("thingamajigsrailroadways:block/ebell_two")
                        .volume(1.0f)
                        .pitch(1.0f)
                        .attenuationDistance(12)
                        .stream(false)
                        .preload(true)
        ).subtitle("thingamajigsrailroadways.subtitle.ebell_ring"));

        this.add(TRRSound.EBELL_THREE, SoundDefinition.definition().with(
                sound("thingamajigsrailroadways:block/ebell_three")
                        .volume(1.0f)
                        .pitch(1.0f)
                        .attenuationDistance(12)
                        .stream(false)
                        .preload(true)
        ).subtitle("thingamajigsrailroadways.subtitle.ebell_ring"));

        this.add(TRRSound.EBELL_FOUR, SoundDefinition.definition().with(
                sound("thingamajigsrailroadways:block/ebell_four")
                        .volume(1.0f)
                        .pitch(1.0f)
                        .attenuationDistance(12)
                        .stream(false)
                        .preload(true)
        ).subtitle("thingamajigsrailroadways.subtitle.ebell_ring"));

        this.add(TRRSound.CLICKY_MECHANICAL_BELL, SoundDefinition.definition().with(
                sound("thingamajigsrailroadways:block/clicky_bell")
                        .volume(1.0f)
                        .pitch(1.0f)
                        .attenuationDistance(16)
                        .stream(false)
                        .preload(true)
        ).subtitle("thingamajigsrailroadways.subtitle.clicky_mechanical_bell_ring"));
    }
}
