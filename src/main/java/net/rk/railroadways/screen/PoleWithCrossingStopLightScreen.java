package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.railroadways.entity.blockentity.custom.PoleWithCrossingStopLightBE;
import net.rk.railroadways.menu.PoleWithCrossingStopLightMenu;
import net.rk.railroadways.menu.RevertedButton;
import net.rk.railroadways.network.record.PoleWithCrossingStopLightPayload;
import net.rk.railroadways.screen.widget.ActionCheckbox;
import org.lwjgl.glfw.GLFW;

public class PoleWithCrossingStopLightScreen extends AbstractContainerScreen<PoleWithCrossingStopLightMenu> {
    private final Level level;
    private final PoleWithCrossingStopLightBE be;
    private final BlockPos pos;

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.parse("thingamajigsrailroadways:textures/gui/rrc_component_setup_bg.png");

    private RevertedButton sendUpdateButton;
    private ActionCheckbox enableFlashingCheckBox;
    private ActionCheckbox horizontalCheckBox;
    /*private EditBox flashingIntervalEdit;
    private EditBox fullyCustomRotationEdit;*/
    private RevertedButton decreaseRotation;
    private RevertedButton increaseRotation;
    private RevertedButton decreaseFlashInterval;
    private RevertedButton increaseFlashInterval;


    public PoleWithCrossingStopLightScreen(PoleWithCrossingStopLightMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.level = menu.level;
        this.be = menu.be;

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.minecraft = Minecraft.getInstance();
        this.font = this.minecraft.font;
        if(be != null)
            this.pos = be.getBlockPos();
        else{
            this.pos = new BlockPos(0,0,0);
            LogUtils.getLogger().error("Illegal argument was given for PoleWithCrossingStopLightScreen, as such its position was set to 0 0 0");
            throw new IllegalArgumentException("PoleWithCrossingStopLight 'be' cannot be null for " + this.toString() + " from " + menu.toString());
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, true);

        if(isShifting) {
            if (isTryingToEscape) {
                guiGraphics.drawString(this.font, Component.translatable("pole_with_crossing_stop_light.hint.toggle_shift_to_esc"), this.titleLabelX, this.titleLabelY + 120, 16759296, false);
            }
            else{
                guiGraphics.drawString(this.font, Component.translatable("pole_with_crossing_stop_light.hint.shift_mode_on"), this.titleLabelX, this.titleLabelY + 120, 16759296, false);
            }
        }
        else{
            guiGraphics.drawString(this.font,Component.translatable("pole_with_crossing_stop_light.hint.shift_mode_off"),this.titleLabelX,this.titleLabelY + 120,16759296,false);
        }
        guiGraphics.drawString(this.font,Component.translatable("pole_with_crossing_stop_light.info.rotation_and_flash_interval",this.be.yAngle,this.be.flashingInterval),this.titleLabelX,this.titleLabelY + 144,14216703,false);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
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
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 256){
            isTryingToEscape = false;
            return true;
        }
        else if(keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            isShifting = false;
            return true;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 256){
            isTryingToEscape = true;
            if(!isShifting) {
                this.onClose();
            }
            return true;
        }
        else{
            isTryingToEscape = false;
        }
        if(keyCode == GLFW.GLFW_KEY_LEFT_SHIFT || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT){
            isShifting = !isShifting;
            return true;
        }
        return false;
    }

    public boolean isShifting = false;
    public boolean isTryingToEscape = false;

    /*@Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(this.fullyCustomRotationEdit.isFocused()){
            return this.fullyCustomRotationEdit.charTyped(codePoint,modifiers);
        }
        else if(this.flashingIntervalEdit.isFocused()){
            return this.flashingIntervalEdit.charTyped(codePoint,modifiers);
        }
        return super.charTyped(codePoint,modifiers);
    }*/

    /*@Override
    public boolean keyPressed(int key, int b, int c) {
        return this.flashingIntervalEdit.keyPressed(key,b,c) || (this.flashingIntervalEdit.isFocused() && key != 256) || this.fullyCustomRotationEdit.keyPressed(key,b,c) || (this.fullyCustomRotationEdit.isFocused() && key != 256) || super.keyPressed(key,b,c);
    }*/

    @Override
    public void init() {
        super.init();
        setupWidgets();
        addRenderableWidget(enableFlashingCheckBox);
        //addRenderableWidget(flashingIntervalEdit);
        //addRenderableWidget(fullyCustomRotationEdit);
        addRenderableWidget(horizontalCheckBox);
        addRenderableWidget(sendUpdateButton);
        addRenderableWidget(decreaseRotation);
        addRenderableWidget(increaseRotation);
        addRenderableWidget(decreaseFlashInterval);
        addRenderableWidget(increaseFlashInterval);
    }

    private void setupWidgets(){
        int horzLeftButtonPos = leftPos + 25;
        int topRowButtonY = topPos + 25;
        int spacingButtonWidth = 12;
        int spacingButtonHeight = spacingButtonWidth;
        float lowPitch = 0.95f;
        float normalPitch = 1.0f;

        enableFlashingCheckBox = new ActionCheckbox(horzLeftButtonPos,topRowButtonY,76,
                Component.translatable("checkbox.title.flashing")
                .withStyle(ChatFormatting.YELLOW),this.font,be.flashing);

        horizontalCheckBox = new ActionCheckbox(enableFlashingCheckBox.getX() + enableFlashingCheckBox.getWidth() + spacingButtonWidth,enableFlashingCheckBox.getY(),76,
                Component.translatable("checkbox.title.horizontal")
                .withStyle(ChatFormatting.GOLD),this.font, be.currentOrientation == PoleWithCrossingStopLightBE.Orientation.HORIZONTAL);


        decreaseRotation = new RevertedButton(horzLeftButtonPos,enableFlashingCheckBox.getY() + 18,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                    this.pos,
                    this.be.yAngle - (isShifting ? 5.0f : 1.0f),this.be.flashingInterval,
                    enableFlashingCheckBox.selected(),
                    horizontalCheckBox.selected()
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseRotation = new RevertedButton(decreaseRotation.getX() + decreaseRotation.getWidth() + spacingButtonWidth, decreaseRotation.getY(), 64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                    this.pos,
                    this.be.yAngle + (isShifting ? 5.0f : 1.0f),this.be.flashingInterval,
                    enableFlashingCheckBox.selected(),
                    horizontalCheckBox.selected()
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        decreaseFlashInterval = new RevertedButton(decreaseRotation.getX(),decreaseRotation.getY() + decreaseRotation.getHeight() + spacingButtonHeight,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_flasher_interval"),(handler) -> {
            PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                    this.pos,
                    this.be.yAngle,this.be.flashingInterval - (isShifting ? 2 : 1),
                    enableFlashingCheckBox.selected(),
                    horizontalCheckBox.selected()
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseFlashInterval = new RevertedButton(increaseRotation.getX(), decreaseFlashInterval.getY(), 64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_flasher_interval"),(handler) -> {
            PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                    this.pos,
                    this.be.yAngle,this.be.flashingInterval + (isShifting ? 2 : 1),
                    enableFlashingCheckBox.selected(),
                    horizontalCheckBox.selected()
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        /*fullyCustomRotationEdit = new DynamicEditBox(this.font,enableFlashingCheckBox.getX(),enableFlashingCheckBox.getY() + spacingButtonHeight + 32,154,19,
                Component.literal(String.valueOf(be.yAngle))
                        .withStyle(ChatFormatting.DARK_GRAY)){
            @Override
            public boolean charTyped(char codePoint, int modifiers){
                if(codePoint == '?' || codePoint == '.' || codePoint == ',' || codePoint == '>' || codePoint == '<' || codePoint == '/' || codePoint == '|' || codePoint == ']' || codePoint == '[' || codePoint == '}' || codePoint == '{' || codePoint == '=' || codePoint == '+' || codePoint == '_' || codePoint == '~' || codePoint == '`' || codePoint == ' ' || codePoint == '!' || codePoint == '@' || codePoint == '\'' || codePoint == '\"' || codePoint == '\\' || codePoint == ':' || codePoint == ';' || codePoint == '#' || codePoint == '$' || codePoint == '%' || codePoint == '^' || codePoint == '&' || codePoint == '*' || codePoint == '(' || codePoint == ')'){
                    return false;
                }
                return super.charTyped(codePoint, modifiers);
            }
        };
        fullyCustomRotationEdit.setEditable(true);
        fullyCustomRotationEdit.setValue(String.valueOf(be.yAngle));

        flashingIntervalEdit = new DynamicEditBox(this.font,fullyCustomRotationEdit.getX(),fullyCustomRotationEdit.getHeight() + spacingButtonHeight,154,19,
                Component.literal(String.valueOf(be.yAngle))
                        .withStyle(ChatFormatting.DARK_GRAY)){
            @Override
            public boolean charTyped(char codePoint, int modifiers){
                if(codePoint == '?' || codePoint == '.' || codePoint == ',' || codePoint == '>' || codePoint == '<' || codePoint == '/' || codePoint == '|' || codePoint == ']' || codePoint == '[' || codePoint == '}' || codePoint == '{' || codePoint == '=' || codePoint == '+' || codePoint == '_' || codePoint == '~' || codePoint == '`' || codePoint == ' ' || codePoint == '!' || codePoint == '@' || codePoint == '\'' || codePoint == '\"' || codePoint == '\\' || codePoint == ':' || codePoint == ';' || codePoint == '#' || codePoint == '$' || codePoint == '%' || codePoint == '^' || codePoint == '&' || codePoint == '*' || codePoint == '(' || codePoint == ')'){
                    System.out.println(codePoint);
                    return false;
                }
                return super.charTyped(codePoint, modifiers);
            }
        };
        flashingIntervalEdit.setEditable(true);
        flashingIntervalEdit.setValue(String.valueOf(be.flashingInterval));*/

        sendUpdateButton = new RevertedButton(horzLeftButtonPos,decreaseFlashInterval.getY() + decreaseFlashInterval.getHeight() + spacingButtonHeight,64,16,
                Component.translatable("button.thingamajigsrailroadways.update_block"),(handler) -> {
            /*String formattedStringRotation = fullyCustomRotationEdit.getValue().replaceAll("^-[0-9]+$","");
            String formattedStringInterval = flashingIntervalEdit.getValue().replaceAll("^-[0-9]+$","");
            System.out.println(formattedStringRotation);
            System.out.println(formattedStringInterval);
            formattedStringInterval.replaceAll("\\.","");
            try{
                float rotation = Float.parseFloat(formattedStringRotation);
                int interval = Integer.parseInt(formattedStringInterval);
                float clampedRotation = Mth.clamp((float)rotation,-180.0f,180.0f);
                int clampedInterval = Mth.clamp((int)Mth.abs(interval),5,100);

                PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                        this.pos,
                        clampedRotation,clampedInterval,
                        enableFlashingCheckBox.selected(),
                        horizontalCheckBox.selected()
                ));
            }
            catch (Exception e){
                LogUtils.getLogger().error(e.getStackTrace().toString());
                LogUtils.getLogger().error(e.getLocalizedMessage());
            }*/
            PacketDistributor.sendToServer(new PoleWithCrossingStopLightPayload(
                    this.pos,
                    this.be.yAngle,this.be.flashingInterval,
                    enableFlashingCheckBox.selected(),
                    horizontalCheckBox.selected()
            ));
        }){};
    }
}
