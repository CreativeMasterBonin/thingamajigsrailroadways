package net.rk.railroadways;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.entity.blockentity.TRRBlockEntity;
import net.rk.railroadways.entity.blockentity.model.*;
import net.rk.railroadways.menu.TRRMenu;
import net.rk.railroadways.render.*;
import net.rk.railroadways.screen.*;

@Mod(value = Thingamajigsrailroadways.MODID,dist = {Dist.CLIENT})
public class TRRClient{
    public TRRClient(IEventBus eventBus, ModContainer container){
        eventBus.addListener(this::clientExtensions);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::layerSetup);
        eventBus.addListener(this::setupMenuTypes);
        eventBus.addListener(this::addCreative);
    }

    public void clientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(TRRBlockEntity.RAILROAD_CROSSING_ARM_BE.get(), RailroadCrossingArmBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.DYNAMIC_SIGN_BE.get(), DynamicSignBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.RR_LIGHTS_BE.get(), RailroadCrossingLightsBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.BRIT_RR_LIGHTS_BE.get(), BritRailwayLightsBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.DUAL_RR_LIGHTS_BE.get(), DualRailwayLightsBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.TRI_RR_LIGHTS_BE.get(),TriRailwayLightsBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.RR_CANTILEVER_LIGHTS_BE.get(),RRCantLightsBERenderer::new);
        BlockEntityRenderers.register(TRRBlockEntity.RAILROAD_CROSSING_ARM_LIGHTED_BE.get(),RailroadCrossingArmWithLightsRenderer::new);
    }

    public void layerSetup(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(RRArmModel.LAYER_LOCATION,RRArmModel::createBodyLayer);
        event.registerLayerDefinition(DynamicSignModel.SIGN_TEXTURE_LOCATION,DynamicSignModel::createBodyLayer);
        event.registerLayerDefinition(RRLightsModel.LIGHTS_TEXTURE_LOCATION,RRLightsModel::createBodyLayer);
        event.registerLayerDefinition(BritRRLightsModel.BRIT_LIGHTS_OFF_LOC,BritRRLightsModel::createBodyLayer);
        event.registerLayerDefinition(DualLightsModel.DUAL_TEXTURE_LOC,DualLightsModel::createBodyLayer);
        event.registerLayerDefinition(TriLightsModel.TRI_TEXTURE_LOC,TriLightsModel::createBodyLayer);
        event.registerLayerDefinition(RRCantLightsModel.CANT_LIGHTS_TEXTURE_LOCATION,RRCantLightsModel::createBodyLayer);
        event.registerLayerDefinition(RRArmLightsModel.RAILROAD_CROSSING_ARM_WITH_LIGHTS,RRArmLightsModel::createBodyLayer);
        event.registerLayerDefinition(RRCantLightsBigModel.DEFAULT_TEXTURE,RRCantLightsBigModel::createBodyLayer);
        event.registerLayerDefinition(RRLightsBigModel.DEFAULT_TEXTURE,RRLightsBigModel::createBodyLayer);
    }

    public void setupMenuTypes(RegisterMenuScreensEvent event){
        event.register(TRRMenu.RAILROAD_CROSSING_MENU.get(), RailroadCrossingArmScreen::new);
        event.register(TRRMenu.DYNAMIC_RR_SIGN_MENU.get(), DynamicSignScreen::new);
        event.register(TRRMenu.RR_LIGHTS_MENU.get(), RailroadCrossingLightsScreen::new);
        event.register(TRRMenu.BRIT_LIGHTS_MENU.get(), BritLightsScreen::new);
        event.register(TRRMenu.DUAL_LIGHTS_MENU.get(), DualLightsScreen::new);
        event.register(TRRMenu.TRI_LIGHTS_MENU.get(),TriLightsScreen::new);
        event.register(TRRMenu.RR_CANT_LIGHTS_MENU.get(),RRCantLightsScreen::new);
        event.register(TRRMenu.RAILROAD_CROSSING_LIGHTS_MENU.get(),RailroadCrossingArmLightedScreen::new);
    }

    public void clientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new TRRBEWLR(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
            }
        },TRRBlocks.BRITISH_RAILWAY_LIGHTS.asItem(),TRRBlocks.DUAL_RAILWAY_LIGHTS.asItem(),TRRBlocks.CROSSBUCK.asItem(),
                TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.asItem(),TRRBlocks.RAILROAD_CROSSING_ARM.asItem(),TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem(),
                TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem(),TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem(),TRRBlocks.TRI_RAILWAY_LIGHTS.asItem(),
                TRRBlocks.CROSSBUCK_WITH_LADDER.asItem());
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == Thingamajigsrailroadways.TRR_TAB.getKey()){
            event.accept(TRRBlocks.PURPLE_RAIL.asItem());
            event.accept(TRRBlocks.PURPLE_POWERED_RAIL.asItem());
            event.accept(TRRBlocks.PURPLE_DETECTOR_RAIL.asItem());
            event.accept(TRRBlocks.PURPLE_ACTIVATOR_RAIL.asItem());
            event.accept(TRRBlocks.POLE_CAP.asItem());
            event.accept(TRRBlocks.RR_LADDER_POLE.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem());
            event.accept(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_ARM.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.asItem());
            event.accept(TRRBlocks.CROSSBUCK.asItem());
            event.accept(TRRBlocks.CROSSBUCK_WITH_LADDER.asItem());
            event.accept(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem());
            event.accept(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.asItem());
            event.accept(TRRBlocks.BRITISH_RAILWAY_LIGHTS.asItem());
            event.accept(TRRBlocks.TRI_RAILWAY_LIGHTS.asItem());
            event.accept(TRRBlocks.DUAL_RAILWAY_LIGHTS.asItem());
            event.accept(TRRBlocks.BRITISH_RAILWAY_ALARM.asItem());
            event.accept(TRRBlocks.MECHANICAL_BELL_TYPE_1.asItem());
            event.accept(TRRBlocks.MECHANICAL_BELL_TYPE_2.asItem());
            event.accept(TRRBlocks.CLICKY_MECHANICAL_BELL.asItem());
            event.accept(TRRBlocks.ELECTRONIC_BELL_TYPE_1.asItem());
            event.accept(TRRBlocks.ELECTRONIC_BELL_TYPE_2.asItem());
            event.accept(TRRBlocks.ELECTRONIC_BELL_TYPE_3.asItem());
            event.accept(TRRBlocks.ELECTRONIC_BELL_TYPE_4.asItem());
        }
    }
}