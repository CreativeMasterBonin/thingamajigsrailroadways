package net.rk.railroadways.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.entity.custom.DieselLocomotive;

import java.util.function.Supplier;

public class TRREntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE,
            Thingamajigsrailroadways.MODID);


    public static final DeferredHolder<EntityType<?>,EntityType<DieselLocomotive>> DIESEL_LOCOMOTIVE = register("diesel_locomotive",
            () -> EntityType.Builder.<DieselLocomotive>of(DieselLocomotive::new,MobCategory.MISC)
                    .sized(0.98f, 0.815f).passengerAttachments(0.1881f).clientTrackingRange(12));


    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder) {
        return ENTITY_TYPES.register(name, () -> builder.get().build(name));
    }
}
