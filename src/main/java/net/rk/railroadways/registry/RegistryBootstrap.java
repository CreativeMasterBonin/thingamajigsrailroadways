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
                        20,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign"));

        bootstrapContext.register(TRRRegistries.ANOTHER_TRAIN_COMING,
                new MultipurposeSignType(ResourceLocation.parse("thingamajigsrailroadways:another_train_coming"),
                        "multipurpose_sign_type.atc.name",
                        20,
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign_atc_left",
                        "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign_atc_right"));
    }
}
