package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
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
import net.rk.railroadways.menu.RevertedButton;
import net.rk.railroadways.menu.WigwagMenu;
import net.rk.railroadways.network.record.WigwagPayload;
import net.rk.railroadways.util.Utilities;

public class WigwagScreen extends AbstractContainerScreen<WigwagMenu> {
    public RevertedButton decreaseRotation;
    public RevertedButton increaseRotation;
    public RevertedButton roundRotation;
    public SpriteIconButton wigwagStandalone;
    public SpriteIconButton wigwagTwoRing;
    public SpriteIconButton wigwagPole;

    public RevertedButton decreaseMaxAngle;
    public RevertedButton increaseMaxAngle;

    public static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"textures/gui/multipurpose_sign_bg.png");

    public WigwagScreen(WigwagMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 320;
        this.imageHeight = 240;
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

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        guiGraphics.drawString(this.font,Component.translatable("title.railroadways.y_rotation")
                        .append(Component.literal(": "))
                        .append(Component.literal(String.valueOf(this.menu.be.yAngle))),
                this.titleLabelX + 20,this.titleLabelY + 160,
                Utilities.whiteColor,true);
        guiGraphics.drawString(this.font,Component.translatable("title.railroadways.max_angle_of_swing")
                        .append(Component.literal(": "))
                        .append(Component.literal(String.valueOf(this.menu.be.maxSwingAngle))),
                this.titleLabelX + 20,this.titleLabelY + 182,
                Utilities.whiteColor,true);
    }

    @Override
    protected void init() {
        super.init();
        setup();
        addRenderableWidget(decreaseRotation);
        addRenderableWidget(increaseRotation);
        addRenderableWidget(roundRotation);
        addRenderableWidget(wigwagStandalone);
        addRenderableWidget(wigwagTwoRing);
        addRenderableWidget(wigwagPole);
        addRenderableWidget(decreaseMaxAngle);
        addRenderableWidget(increaseMaxAngle);
    }

    public void setup(){
        int horzLeftButtonPos = leftPos + 36;
        int topRowButtonY = topPos + 64;
        int spacingButtonWidth = 8;
        int spacingButtonHeight = 8;

        decreaseRotation = new RevertedButton(horzLeftButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle - 1,
                    menu.be.maxSwingAngle,
                    menu.be.signalDesignType
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 0.95f));
            }
        };

        increaseRotation = new RevertedButton(decreaseRotation.getX() + decreaseRotation.getWidth() + spacingButtonWidth,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle + 1,
                    menu.be.maxSwingAngle,
                    menu.be.signalDesignType
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 1.0f));
            }
        };

        roundRotation = new RevertedButton(decreaseRotation.getX() + 36,decreaseRotation.getY() + 72,90,16,
                Component.translatable("button.thingamajigsrailroadways.update_rotation"),(handler)->{
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    Math.round(menu.be.yAngle),
                    menu.be.maxSwingAngle,
                    menu.be.signalDesignType

            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.WIND_CHARGE_BURST, 1.0f));
            }
        };

        // angle of movement for wigwag arm (values are reversed to make more sense logically)
        decreaseMaxAngle = new RevertedButton(decreaseRotation.getX(),decreaseRotation.getY() + decreaseRotation.getHeight() + 6,64,16,
                Component.translatable("button.railroadways.dec_max_swing"),(handler) -> {
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.maxSwingAngle + 1,
                    menu.be.signalDesignType
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 0.95f));
            }
        };
        increaseMaxAngle = new RevertedButton(decreaseMaxAngle.getX() + decreaseMaxAngle.getWidth() + spacingButtonWidth,decreaseMaxAngle.getY(),64,16,
                Component.translatable("button.railroadways.inc_max_swing"),(handler) -> {
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.maxSwingAngle - 1,
                    menu.be.signalDesignType
            ));
        }){
            @Override
            public void playDownSound(SoundManager handler) {
                handler.play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP, 1.0f));
            }
        };

        // icon buttons
        wigwagStandalone = TRRClient.wigWagStandalone(16,(handler)->{
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.maxSwingAngle,
                    (byte)16
            ));
        },true);
        wigwagTwoRing = TRRClient.wigWagTwoRing(16,(handler)->{
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.maxSwingAngle,
                    (byte)64
            ));
        },true);
        wigwagPole = TRRClient.wigWagPole(16,(handler)->{
            PacketDistributor.sendToServer(new WigwagPayload(
                    menu.be.getBlockPos(),
                    menu.be.yAngle,
                    menu.be.maxSwingAngle,
                    (byte)127
            ));
        },true);

        wigwagStandalone.setPosition(increaseRotation.getX() + increaseRotation.getWidth() + 36,increaseRotation.getY());
        wigwagTwoRing.setPosition(wigwagStandalone.getX() + spacingButtonWidth + 16,wigwagStandalone.getY());
        wigwagPole.setPosition(wigwagTwoRing.getX() + spacingButtonWidth + 16,wigwagTwoRing.getY());
        wigwagStandalone.setSize(20,20);
        wigwagTwoRing.setSize(20,20);
        wigwagPole.setSize(20,20);
    }
}
