package net.rk.railroadways.registry;

import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RegistryBootstrap{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void bootstrap(BootstrapContext<MultipurposeSignType> bootstrapContext){
        LOGGER.info("Long Roads' Bootstrap is adding internal data driven sign types");
        bootstrapContext.register(TRRRegistries.PlACEHOLDER,
                new MultipurposeSignType(ResourceLocation.parse("thingamajigsrailroadways:placeholder_sign"),
                        "multipurpose_sign_type.placeholder.name",
                        200,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign"));

        bootstrapContext.register(TRRRegistries.ANOTHER_TRAIN_COMING,
                new MultipurposeSignType(ResourceLocation.parse("thingamajigsrailroadways:another_train_coming"),
                        "multipurpose_sign_type.atc.name",
                        18,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_atc_off",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign_atc_left",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign_atc_right"));
        bootstrapContext.register(TRRRegistries.DANGER_ANOTHER_TRAIN_COMING,
                new MultipurposeSignType(ResourceLocation.parse("thingamajigsrailroadways:danger_another_train_coming"),
                        "multipurpose_sign_type.danger_atc.name",
                        30,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/atc_off",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/atc_on",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/atc_on_alt"));
        bootstrapContext.register(TRRRegistries.RXR_STOP_LOOK_LISTEN,
                new MultipurposeSignType(ResourceLocation.parse("thingamajigsrailroadways:rxr_stop_look_listen"),
                        "multipurpose_sign_type.rxr_stop_look_listen.name",
                        120,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/rxr_stoplooklisten_off",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/rxr_stoplooklisten",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/rxr_stoplooklisten"));
    }
}
