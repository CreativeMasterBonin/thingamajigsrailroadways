package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.railroadways.TRRClient;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.menu.EnhancedDirectionalCrossingLightMenu;
import net.rk.railroadways.menu.RevertedButton;
import net.rk.railroadways.network.record.EDCLightsPayload;
import net.rk.railroadways.screen.widget.ActionCheckbox;
import net.rk.railroadways.util.Utilities;

public class EnhancedDirectionalCrossingLightScreen extends AbstractContainerScreen<EnhancedDirectionalCrossingLightMenu> {
    public RevertedButton decreaseRotation;
    public RevertedButton increaseRotation;
    public RevertedButton roundRotation;
    public SpriteIconButton edcAll;
    public SpriteIconButton edcRed;
    public SpriteIconButton edcOrange;
    private ActionCheckbox orangeLightsShowDir;
    private ActionCheckbox northSouthChecking;
    private ActionCheckbox showOtherDirForNSEW;

    public static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"textures/gui/multipurpose_sign_bg.png");

    public EnhancedDirectionalCrossingLightScreen(EnhancedDirectionalCrossingLightMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 320;
        this.imageHeight = 240;
    }

    @Override
    public void init() {
        super.init();
        setup();
        addRenderableWidget(decreaseRotation);
        addRenderableWidget(increaseRotation);
        addRenderableWidget(roundRotation);
        addRenderableWidget(edcRed);
        addRenderableWidget(edcOrange);
        addRenderableWidget(edcAll);
        addRenderableWidget(orangeLightsShowDir);
        addRenderableWidget(northSouthChecking);
        addRenderableWidget(showOtherDirForNSEW);
    }

    public void setup(){
        int horzLeftButtonPos = leftPos + 36;
        int topRowButtonY = topPos + 64;
        int spacingButtonWidth = 8;
        int spacingButtonHeight = 8;

        decreaseRotation = new RevertedButton(horzLeftButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new EDCLightsPayload(
                menu.be.getBlockPos(),
                    menu.be.yAngle - 1,
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    menu.be.flasherConfiguration
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 0.95f));
            }
        };

        increaseRotation = new RevertedButton(decreaseRotation.getX() + decreaseRotation.getWidth() + spacingButtonWidth,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new EDCLightsPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle + 1,
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    menu.be.flasherConfiguration
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 1.0f));
            }
        };

        roundRotation = new RevertedButton(decreaseRotation.getX() + 36,decreaseRotation.getY() + 72,90,16,
                Component.translatable("button.thingamajigsrailroadways.update_rotation"),(handler)->{
            PacketDistributor.sendToServer(new EDCLightsPayload(
                    menu.be.getBlockPos(),
                    Math.round(menu.be.yAngle),
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    menu.be.flasherConfiguration
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.WIND_CHARGE_BURST, 1.0f));
            }
        };

        edcRed = TRRClient.edcRedOnly(22,(handler)->{
            PacketDistributor.sendToServer(new EDCLightsPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    (byte)16
            ));
        },true);
        edcOrange = TRRClient.edcOrangeOnly(22,(handler)->{
            PacketDistributor.sendToServer(new EDCLightsPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    (byte)64
            ));
        },true);
        edcAll = TRRClient.edcAll(22,(handler)->{
            PacketDistributor.sendToServer(new EDCLightsPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.orangeLightsShowDirectionOfTravel,
                    menu.be.checksNorthSouthTrack,
                    menu.be.swapNSEWcheck,
                    (byte)127
            ));
        },true);

        edcRed.setPosition(increaseRotation.getX() + increaseRotation.getWidth() + 36,increaseRotation.getY());
        edcOrange.setPosition(edcRed.getX() + spacingButtonWidth + 16,edcRed.getY());
        edcAll.setPosition(edcOrange.getX() + spacingButtonWidth + 16,edcOrange.getY());

        orangeLightsShowDir = new ActionCheckbox(decreaseRotation.getX(),decreaseRotation.getY() + decreaseRotation.getHeight() + 23,56,Component.translatable("checkbox.title.show_dir_of_travel")
                .withStyle(ChatFormatting.WHITE),this.font,this.menu.be.orangeLightsShowDirectionOfTravel){
            @Override
            public void onClick(double mouseX, double mouseY, int button) {
                this.onPress();
                if(this.selected()){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON,1.0f));
                }
                else{
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,1.0f));
                }
                PacketDistributor.sendToServer(new EDCLightsPayload(
                        menu.be.getBlockPos(),
                        menu.be.yAngle,
                        this.selected(),
                        menu.be.checksNorthSouthTrack,
                        menu.be.swapNSEWcheck,
                        menu.be.flasherConfiguration
                ));
            }
        };
        northSouthChecking = new ActionCheckbox(orangeLightsShowDir.getX() + orangeLightsShowDir.getWidth() + 32,orangeLightsShowDir.getY(),56,Component.translatable("checkbox.title.north_south_check")
                .withStyle(ChatFormatting.WHITE),this.font,this.menu.be.checksNorthSouthTrack){
            @Override
            public void onClick(double mouseX, double mouseY, int button) {
                this.onPress();
                if(this.selected()){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON,1.0f));
                }
                else{
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,1.0f));
                }
                PacketDistributor.sendToServer(new EDCLightsPayload(
                        menu.be.getBlockPos(),
                        menu.be.yAngle,
                        menu.be.orangeLightsShowDirectionOfTravel,
                        this.selected(),
                        menu.be.swapNSEWcheck,
                        menu.be.flasherConfiguration
                ));
            }
        };
        showOtherDirForNSEW = new ActionCheckbox(northSouthChecking.getX() + northSouthChecking.getWidth() + 24,northSouthChecking.getY(),56,Component.translatable("checkbox.title.swap_dirs")
                .withStyle(ChatFormatting.WHITE),this.font,this.menu.be.swapNSEWcheck){
            @Override
            public void onClick(double mouseX, double mouseY, int button) {
                this.onPress();
                if(this.selected()){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON,1.0f));
                }
                else{
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,1.0f));
                }
                PacketDistributor.sendToServer(new EDCLightsPayload(
                        menu.be.getBlockPos(),
                        menu.be.yAngle,
                        menu.be.orangeLightsShowDirectionOfTravel,
                        menu.be.checksNorthSouthTrack,
                        this.selected(),
                        menu.be.flasherConfiguration
                ));
            }
        };
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        guiGraphics.drawString(this.font,Component.translatable("title.railroadways.y_rotation")
                        .append(Component.literal(": "))
                        .append(Component.literal(String.valueOf(this.menu.be.yAngle))),
                this.titleLabelX + 20,this.titleLabelY + 160,
                Utilities.whiteColor,true);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        guiGraphics.blit(BG_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }
}
