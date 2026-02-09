package net.rk.railroadways.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
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
import net.rk.railroadways.entity.blockentity.custom.DynamicSignBE;
import net.rk.railroadways.menu.DynamicSignMenu;
import net.rk.railroadways.network.record.DynamicSignPayload;
import net.rk.railroadways.screen.widget.ActionCheckbox;
import net.rk.railroadways.screen.widget.DynamicEditBox;
import net.rk.thingamajigs.screen.widget.RevertedButton;
import net.rk.thingamajigs.xtras.TColors;

import java.util.HashMap;

public class DynamicSignScreen extends AbstractContainerScreen<DynamicSignMenu>{
    private final static HashMap<String, Object> guistate = DynamicSignMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    private DynamicSignBE dsbe;

    private RevertedButton decreaseRotation;
    private RevertedButton increaseRotation;

    private RevertedButton decreaseType;
    private RevertedButton increaseType;

    private RevertedButton updateBlock;

    private EditBox customTextureEdit;
    private ActionCheckbox customTextureEnable;

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.parse("thingamajigsrailroadways:textures/gui/rrc_component_setup_bg.png");

    public DynamicSignScreen(DynamicSignMenu container, Inventory inventory, Component text){
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

        this.dsbe = (DynamicSignBE) world.getBlockEntity(new BlockPos(x,y,z)); // be access
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, true);
        if(this.dsbe.customSign){
            guiGraphics.drawString(this.font,Component.translatable("container.thingamajigsrailroadways.dynamic_sign.custom_on"),
                    this.titleLabelX + 9,this.titleLabelY + 79,
                    TColors.getWhite(),true);
        }
        else{
            guiGraphics.drawString(this.font,Component.translatable("container.thingamajigsrailroadways.dynamic_sign.custom_off"),
                    this.titleLabelX + 9,this.titleLabelY + 79,
                    TColors.getWhite(),true);
        }
        guiGraphics.drawString(this.font,Component.translatable("container.thingamajigsrailroadways.dynamic_sign.sign_type",dsbe.signType),
                this.titleLabelX + 9,this.titleLabelY + 98,
                TColors.getWhite(),true);

        String signtypename;

        switch(dsbe.signType){
            case 0 -> signtypename = "sign_type.aust_alt";
            case 1 -> signtypename = "sign_type.aust";
            case 2 -> signtypename = "sign_type.canadian";
            case 3 -> signtypename = "sign_type.cateye";
            case 4 -> signtypename = "sign_type.czech";
            case 5 -> signtypename = "sign_type.finnish";
            case 6 -> signtypename = "sign_type.german_horz";
            case 7 -> signtypename = "sign_type.german_vert";
            case 8 -> signtypename = "sign_type.inverted_cateye";
            case 9 -> signtypename = "sign_type.japan";
            case 10 -> signtypename = "sign_type.usa";
            case 11 -> signtypename = "sign_type.rr_ahead";
            case 12 -> signtypename = "sign_type.rr_ahead_old";
            case 13 -> signtypename = "sign_type.stop_when_red_lights_show";
            case 14 -> signtypename = "sign_type.inverted_usa";
            default -> signtypename = "sign_type.unset";
        }

        if(dsbe.customSign){
            signtypename = "sign_type.custom";
        }

        guiGraphics.drawString(this.font,Component.translatable(signtypename),
                this.titleLabelX + 9,this.titleLabelY + 115,
                TColors.getWhite(),true);
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
    public boolean charTyped(char codePoint, int modifiers) {
        return this.customTextureEdit.charTyped(codePoint,modifiers);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        return this.customTextureEdit.keyPressed(key,b,c) || (this.customTextureEdit.isFocused() && key != 256) || super.keyPressed(key,b,c);
    }

    @Override
    protected void init() {
        super.init();
        setupWidgets();
        addRenderableWidget(decreaseRotation);
        addRenderableWidget(increaseRotation);
        addRenderableWidget(decreaseType);
        addRenderableWidget(increaseType);
        addRenderableWidget(updateBlock);
        addRenderableWidget(customTextureEdit);
        addRenderableWidget(customTextureEnable);
    }

    private void setupWidgets(){
        int horzLeftButtonPos = leftPos + 25;
        int topRowButtonY = topPos + 25;
        int spacingButtonWidth = 2;
        int spacingButtonHeight = spacingButtonWidth;
        float lowPitch = 0.95f;
        float normalPitch = 1.0f;

        customTextureEdit = new DynamicEditBox(this.font,this.leftPos + 12,this.topPos + 139,154,19,
                Component.literal(dsbe.customTextureLoc)
                .withStyle(ChatFormatting.DARK_GRAY));
        customTextureEdit.setEditable(true);
        customTextureEdit.setValue(dsbe.customTextureLoc);

        customTextureEnable = new ActionCheckbox(this.leftPos + 89,this.topPos + 99,76,Component.translatable("checkbox.title.dynamic_sign_crossbuck")
                .withStyle(ChatFormatting.YELLOW),this.font,dsbe.customSign);


        decreaseRotation = new RevertedButton(horzLeftButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new DynamicSignPayload(
                    new BlockPos(x,y,z),
                    dsbe.yAngle - 0.05f,
                    dsbe.signType,
                    false,
                    dsbe.customSign,
                    dsbe.customTextureLoc
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        int horzRightButtonPos = decreaseRotation.getX() + decreaseRotation.getWidth() + spacingButtonWidth;

        increaseRotation = new RevertedButton(horzRightButtonPos,topRowButtonY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new DynamicSignPayload(
                    new BlockPos(x,y,z),
                    dsbe.yAngle + 0.05f,
                    dsbe.signType,
                    false,
                    dsbe.customSign,
                    dsbe.customTextureLoc
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        int secondRowY = decreaseRotation.getY() + decreaseRotation.getHeight() + spacingButtonHeight;

        decreaseType = new RevertedButton(horzLeftButtonPos,secondRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_sign_type"),(handler) -> {
            PacketDistributor.sendToServer(new DynamicSignPayload(
                    new BlockPos(x,y,z),
                    dsbe.yAngle,
                    dsbe.signType - 1,
                    false,
                    dsbe.customSign,
                    dsbe.customTextureLoc
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseType = new RevertedButton(horzRightButtonPos,secondRowY,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_sign_type"),(handler) -> {
            PacketDistributor.sendToServer(new DynamicSignPayload(
                    new BlockPos(x,y,z),
                    dsbe.yAngle,
                    dsbe.signType + 1,
                    false,
                    dsbe.customSign,
                    dsbe.customTextureLoc
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        updateBlock = new RevertedButton(horzLeftButtonPos,secondRowY + decreaseType.getHeight() + spacingButtonHeight,64,16,
                Component.translatable("button.thingamajigsrailroadways.update_block"),(handler) -> {
            PacketDistributor.sendToServer(new DynamicSignPayload(
                    new BlockPos(x,y,z),
                    dsbe.yAngle,
                    dsbe.signType,
                    true,
                    customTextureEnable.selected(),
                    customTextureEdit.getValue()
            ));
        }){};
    }
}
