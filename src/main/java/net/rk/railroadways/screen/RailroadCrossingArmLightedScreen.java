package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;
import net.rk.railroadways.menu.RailroadCrossingArmLightedMenu;
import net.rk.railroadways.network.record.RailroadCrossingArmLightedPayload;
import net.rk.thingamajigs.screen.widget.RevertedButton;

import java.util.HashMap;

public class RailroadCrossingArmLightedScreen extends AbstractContainerScreen<RailroadCrossingArmLightedMenu> {
    private final static HashMap<String, Object> guistate = RailroadCrossingArmLightedMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    private RailroadCrossingArmWithLights rcbe;

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.parse("thingamajigsrailroadways:textures/gui/rrc_component_setup_bg.png");

    public RailroadCrossingArmLightedScreen(RailroadCrossingArmLightedMenu container, Inventory inventory, Component text){
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;

        this.minecraft = Minecraft.getInstance();
        this.font = this.minecraft.font;

        int widthx = (this.width - this.imageWidth) / 2;
        int heighty = (this.height - this.imageHeight) / 2;

        this.rcbe = (RailroadCrossingArmWithLights)world.getBlockEntity(new BlockPos(x,y,z)); // be access
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, true);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1){
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        guiGraphics.blit(BG_TEXTURE,
                this.leftPos,this.topPos,0,0,
                this.imageWidth,this.imageHeight,this.imageWidth,this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if(key == 256){
            this.getMinecraft().player.closeContainer();
            return true;
        }
        return false;
    }

    private RevertedButton decreaseGateLength;
    private RevertedButton increaseGateLength;
    private RevertedButton decreaseGateOffset;
    private RevertedButton increaseGateOffset;

    private RevertedButton decreaseRotation;
    private RevertedButton increaseRotation;

    @Override
    protected void init() {
        super.init();
        setupWidgets();
        addRenderableWidget(decreaseGateLength);
        addRenderableWidget(increaseGateLength);
        addRenderableWidget(decreaseGateOffset);
        addRenderableWidget(increaseGateOffset);
        addRenderableWidget(decreaseRotation);
        addRenderableWidget(increaseRotation);
    }

    private void setupWidgets(){
        int horzLeftButtonPos = leftPos + 25;
        int topRowButtonY = topPos + 25;
        int spacingButtonWidth = 2;
        int spacingButtonHeight = spacingButtonWidth;
        float lowPitch = 0.95f;
        float normalPitch = 1.0f;

        // gate length

        decreaseGateLength = new RevertedButton(horzLeftButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength + 1.0f,
                    rcbe.armGateOffsetZ,
                    rcbe.yAngle
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        int horzRightButtonPos = horzLeftButtonPos + decreaseGateLength.getWidth() + spacingButtonWidth;

        increaseGateLength = new RevertedButton(horzRightButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength - 1.0f,
                    rcbe.armGateOffsetZ,
                    rcbe.yAngle
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        // gate offset

        int secondRowY = decreaseGateLength.getY() + decreaseGateLength.getHeight() + spacingButtonHeight;

        decreaseGateOffset = new RevertedButton(horzLeftButtonPos,secondRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_off"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength,
                    rcbe.armGateOffsetZ - 0.5f,
                    rcbe.yAngle
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseGateOffset = new RevertedButton(horzRightButtonPos,secondRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_off"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength,
                    rcbe.armGateOffsetZ + 0.5f,
                    rcbe.yAngle
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        // gate rotation

        int thirdRowY = decreaseGateOffset.getY() + decreaseGateOffset.getHeight() + spacingButtonHeight;

        decreaseRotation = new RevertedButton(horzLeftButtonPos,thirdRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength,
                    rcbe.armGateOffsetZ,
                    rcbe.yAngle - 0.05f
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseRotation = new RevertedButton(horzRightButtonPos,thirdRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new RailroadCrossingArmLightedPayload(
                    new BlockPos(x,y,z),
                    rcbe.startingArmAngle,
                    rcbe.endArmAngle,
                    rcbe.armLength,
                    rcbe.armGateOffsetZ,
                    rcbe.yAngle + 0.05f
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};
    }
}
