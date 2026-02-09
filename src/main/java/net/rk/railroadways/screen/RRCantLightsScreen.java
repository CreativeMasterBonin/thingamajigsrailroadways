package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.floats.FloatConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingCantLightsBE;
import net.rk.railroadways.menu.RRCantLightsMenu;
import net.rk.railroadways.network.record.RRCantLightsPayload;
import net.rk.railroadways.screen.widget.ActionCheckbox;
import net.rk.thingamajigs.screen.widget.RevertedButton;
import net.rk.thingamajigs.xtras.TColors;

import java.util.HashMap;
import java.util.Map;

public class RRCantLightsScreen extends AbstractContainerScreen<RRCantLightsMenu>{
    private final static HashMap<String, Object> guistate = RRCantLightsMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public float flrot = 0;
    public float frrot = 0;
    public float blrot = 0;
    public float brrot = 0;

    private RailroadCrossingCantLightsBE rrclbe;

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.parse("thingamajigsrailroadways:textures/gui/rrc_component_setup_bg.png");

    public RRCantLightsScreen(RRCantLightsMenu container, Inventory inventory, Component text){
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

        this.rrclbe = (RailroadCrossingCantLightsBE)world.getBlockEntity(new BlockPos(x,y,z)); // be access
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, true);
        guiGraphics.drawString(this.font, Component.translatable("title.thingamajigsrailroadways.lights_screen_data",
                        rrclbe.frontLeftAngle,
                        rrclbe.frontRightAngle,
                        rrclbe.backLeftAngle,
                        rrclbe.backRightAngle),
                this.titleLabelX, this.titleLabelY + 80, TColors.getWhite(), true);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
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

    public RevertedButton decLightsRot;
    public RevertedButton incLightsRot;
    public ExtendedSlider frontLeftRot;
    public ExtendedSlider frontRightRot;
    public ExtendedSlider backLeftRot;
    public ExtendedSlider backRightRot;
    public RevertedButton updateRotations;

    public ActionCheckbox frontLightsCheck;
    public ActionCheckbox backLightsCheck;

    @Override
    protected void init() {
        super.init();
        setupWidgets();
        addRenderableWidget(decLightsRot);
        addRenderableWidget(incLightsRot);
        addRenderableWidget(frontLeftRot);
        addRenderableWidget(frontRightRot);
        addRenderableWidget(backLeftRot);
        addRenderableWidget(backRightRot);
        addRenderableWidget(updateRotations);
        addRenderableWidget(frontLightsCheck);
        addRenderableWidget(backLightsCheck);
    }

    private Map<ExtendedSlider, FloatConsumer> sliderMappingMouseMovement = new HashMap<>();

    private void setupWidgets(){
        int horzLeftButtonPos = leftPos + 25 - 7;
        int topRowButtonY = topPos + 25;
        int spacingButtonWidth = 2;
        int spacingButtonHeight = spacingButtonWidth;
        float lowPitch = 0.95f;
        float normalPitch = 1.0f;

        float minValue = 0.00f;
        float maxValue = 32.05f;

        this.flrot = rrclbe.frontLeftAngle;
        this.frrot = rrclbe.frontRightAngle;
        this.blrot = rrclbe.backLeftAngle;
        this.brrot = rrclbe.backRightAngle;

        decLightsRot = new RevertedButton(horzLeftButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new RRCantLightsPayload(
                    new BlockPos(x,y,z),
                    rrclbe.yAngle - 0.1f,
                    rrclbe.frontLeftAngle,
                    rrclbe.frontRightAngle,
                    rrclbe.backLeftAngle,
                    rrclbe.backRightAngle,
                    rrclbe.showFrontLights,
                    rrclbe.showBackLights
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        int horzRightButtonPos = horzLeftButtonPos + decLightsRot.getWidth() + spacingButtonWidth;

        incLightsRot = new RevertedButton(horzRightButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new RRCantLightsPayload(
                    new BlockPos(x,y,z),
                    rrclbe.yAngle + 0.1f,
                    rrclbe.frontLeftAngle,
                    rrclbe.frontRightAngle,
                    rrclbe.backLeftAngle,
                    rrclbe.backRightAngle,
                    rrclbe.showFrontLights,
                    rrclbe.showBackLights
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        int precise = 1;
        float step = 0.05f;

        frontLeftRot = new ExtendedSlider(horzLeftButtonPos,
                topRowButtonY + decLightsRot.getHeight() + spacingButtonHeight,
                70, 15,
                Component.translatable("slider.thingamajigsrailroadways.front_left_light_rot"),
                Component.empty(), minValue, maxValue, this.flrot, step, precise, false){
            @Override
            protected void applyValue() {
                RRCantLightsScreen.this.flrot = (float) Mth.clampedLerp(minValue,maxValue,this.value);
            }

            @Override
            public void onRelease(double mouseX, double mouseY){
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        flrot,
                        rrclbe.frontRightAngle,
                        rrclbe.backLeftAngle,
                        rrclbe.backRightAngle,
                        rrclbe.showFrontLights,
                        rrclbe.showBackLights
                ));
                rrclbe.updateBlock();
            }

            @Override
            protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
                super.onDrag(mouseX, mouseY, dragX, dragY);
                applyValue();
            }
        };

        frontRightRot = new ExtendedSlider(horzRightButtonPos + spacingButtonWidth + 7,
                topRowButtonY + incLightsRot.getHeight() + spacingButtonHeight,
                70, 15,
                Component.translatable("slider.thingamajigsrailroadways.front_right_light_rot"),
                Component.empty(), minValue, maxValue, this.frrot, step, precise, false){
            @Override
            protected void applyValue() {
                RRCantLightsScreen.this.frrot = (float)(Mth.clampedLerp(minValue,maxValue,this.value));
            }

            @Override
            public void onRelease(double mouseX, double mouseY){
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        rrclbe.frontLeftAngle,
                        frrot,
                        rrclbe.backLeftAngle,
                        rrclbe.backRightAngle,
                        rrclbe.showFrontLights,
                        rrclbe.showBackLights
                ));
                rrclbe.updateBlock();
            }

            @Override
            protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
                super.onDrag(mouseX, mouseY, dragX, dragY);
                applyValue();
            }
        };

        backLeftRot = new ExtendedSlider(horzLeftButtonPos,
                frontLeftRot.getY() + frontLeftRot.getHeight() + spacingButtonHeight,
                70, 15,
                Component.translatable("slider.thingamajigsrailroadways.back_left_light_rot"),
                Component.empty(), minValue, maxValue, this.blrot, step, precise, false){
            @Override
            protected void applyValue() {
                RRCantLightsScreen.this.blrot = (float)(Mth.clampedLerp(minValue,maxValue,this.value));
            }

            @Override
            public void onRelease(double mouseX, double mouseY){
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        rrclbe.frontLeftAngle,
                        rrclbe.frontRightAngle,
                        blrot,
                        rrclbe.backRightAngle,
                        rrclbe.showFrontLights,
                        rrclbe.showBackLights
                ));
                rrclbe.updateBlock();
            }

            @Override
            protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
                super.onDrag(mouseX, mouseY, dragX, dragY);
                applyValue();
            }
        };

        backRightRot = new ExtendedSlider(horzRightButtonPos + spacingButtonWidth + 7,
                frontRightRot.getY() + frontRightRot.getHeight() + spacingButtonHeight,
                70, 15,
                Component.translatable("slider.thingamajigsrailroadways.back_right_light_rot"),
                Component.empty(), minValue, maxValue, this.brrot, step, precise, false){
            @Override
            protected void applyValue() {
                RRCantLightsScreen.this.brrot = (float)(Mth.clampedLerp(minValue,maxValue,this.value));
            }

            @Override
            public void onRelease(double mouseX, double mouseY){
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        rrclbe.frontLeftAngle,
                        rrclbe.frontRightAngle,
                        rrclbe.backLeftAngle,
                        brrot,
                        rrclbe.showFrontLights,
                        rrclbe.showBackLights
                ));
                rrclbe.updateBlock();
            }

            @Override
            protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
                super.onDrag(mouseX, mouseY, dragX, dragY);
                applyValue();
            }
        };

        frontLightsCheck = new ActionCheckbox(horzLeftButtonPos,topRowButtonY + (backLeftRot.getHeight() * 2) + 70,
                32,Component.translatable("checkbox.thingamajigsrailroadways.front_check"),
                this.font,rrclbe.showFrontLights){
            @Override
            public void doAction(boolean selected) {
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        rrclbe.frontLeftAngle,
                        rrclbe.frontRightAngle,
                        rrclbe.backLeftAngle,
                        rrclbe.backRightAngle,
                        frontLightsCheck.selected(),
                        rrclbe.showBackLights
                ));
                rrclbe.updateBlock();
            }
        };

        backLightsCheck = new ActionCheckbox(horzRightButtonPos,topRowButtonY + (backLeftRot.getHeight() * 2) + 70,
                32,Component.translatable("checkbox.thingamajigsrailroadways.back_check"),
                this.font,rrclbe.showBackLights){
            @Override
            public void doAction(boolean selected) {
                PacketDistributor.sendToServer(new RRCantLightsPayload(
                        new BlockPos(x,y,z),
                        rrclbe.yAngle,
                        rrclbe.frontLeftAngle,
                        rrclbe.frontRightAngle,
                        rrclbe.backLeftAngle,
                        rrclbe.backRightAngle,
                        rrclbe.showFrontLights,
                        backLightsCheck.selected()
                ));
                rrclbe.updateBlock();
            }
        };

        updateRotations = new RevertedButton(horzLeftButtonPos,topRowButtonY + (backLeftRot.getHeight() * 2) + 50,64,12,
                Component.translatable("button.thingamajigsrailroadways.update_rotation"),(handler) -> {
            PacketDistributor.sendToServer(new RRCantLightsPayload(
                    new BlockPos(x,y,z),
                    rrclbe.yAngle,
                    (float)flrot,
                    (float)frrot,
                    (float)blrot,
                    (float)brrot,
                    rrclbe.showFrontLights,
                    rrclbe.showBackLights
            ));
        }){};

        this.sliderMappingMouseMovement = Map.of(
                frontLeftRot, (a) -> flrot = a,
                frontRightRot, (a) -> frrot = a,
                backLeftRot, (a) -> blrot = a,
                backRightRot, (a) -> brrot = a
        );
    }

    // from lio modified for use with float sliders
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta, double deltaY) {
        this.sliderMappingMouseMovement.forEach((slider, consumer) -> {
            if (slider.isMouseOver(mouseX, mouseY)) {
                slider.setValue(((float)slider.getValue()) + (delta > 0 ? 1 : -1));
                consumer.accept((float)slider.getValue());
            }
        });
        return false;
    }
}
