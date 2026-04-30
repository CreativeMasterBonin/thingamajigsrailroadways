package net.rk.railroadways.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.Thingamajigsrailroadways;

public class TRRRegistries{
    public static final ResourceKey<Registry<MultipurposeSignType>> MULTIPURPOSE_SIGN_TYPE = key("multipurpose_sign_type");

    // resource keys
    public static final ResourceKey<MultipurposeSignType> PlACEHOLDER = ResourceKey.create(
            MULTIPURPOSE_SIGN_TYPE,
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"placeholder")
    );
    public static final ResourceKey<MultipurposeSignType> ANOTHER_TRAIN_COMING = ResourceKey.create(
            MULTIPURPOSE_SIGN_TYPE,
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"another_train_coming")
    );
    public static final ResourceKey<MultipurposeSignType> DANGER_ANOTHER_TRAIN_COMING = ResourceKey.create(
            MULTIPURPOSE_SIGN_TYPE,
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"danger_another_train_coming")
    );
    public static final ResourceKey<MultipurposeSignType> RXR_STOP_LOOK_LISTEN = ResourceKey.create(
            MULTIPURPOSE_SIGN_TYPE,
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"rxr_stop_look_listen")
    );

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID, name));
    }
}
