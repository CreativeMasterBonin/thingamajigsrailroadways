package net.rk.railroadways.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.entity.custom.AbstractLocomotive;

public class LocomotiveRenderer extends EntityRenderer<AbstractLocomotive> {
    public LocomotiveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractLocomotive abstractLocomotive) {
        return null;
    }
}
