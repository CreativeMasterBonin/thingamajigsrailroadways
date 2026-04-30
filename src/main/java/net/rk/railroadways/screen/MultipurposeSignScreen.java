package net.rk.railroadways.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.menu.MultipurposeSignMenu;
import net.rk.railroadways.menu.RevertedButton;
import net.rk.railroadways.network.record.MultipurposeSignTypeUpdatePayload;
import net.rk.railroadways.registry.MultipurposeSignType;
import net.rk.railroadways.screen.widget.ActionCheckbox;
import net.rk.railroadways.util.Utilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MultipurposeSignScreen extends AbstractContainerScreen<MultipurposeSignMenu>{
    public static final ResourceLocation BG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"textures/gui/multipurpose_sign_bg.png");

    public static final ResourceLocation LIST_BG =
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"textures/gui/multipurpose_sign_list_bg.png");

    public RevertedButton decreaseRotation;
    public RevertedButton increaseRotation;
    public RevertedButton roundRotation;
    private ActionCheckbox alternatingModeEnable;

    public RevertedButton decreaseZRotation;
    public RevertedButton increaseZRotation;

    public boolean scrolling = false;
    public float scrollOffs = 0;
    public int startRow = 0;

    public int listButtonStartX = 184;
    public int listButtonStartY = 96;

    public int listBGX = 183;
    public int listBGY = 94;

    public int scrollerStartX = 241;
    public int scrollerStartY = 95;

    public int buttonSizeX = 14;
    public int buttonSizeY = 14;


    public MultipurposeSignScreen(MultipurposeSignMenu menu, Inventory playerInventory, Component title) {
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
        addRenderableWidget(alternatingModeEnable);
        addRenderableWidget(decreaseZRotation);
        addRenderableWidget(increaseZRotation);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        double deltop = Mth.sin((float)(Util.getMillis() % 2000L) / 2000f * Mth.TWO_PI);
        // render the bg behind gui elements
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        guiGraphics.blit(BG_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();

        // the list bg in front of the screen bg
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        guiGraphics.blit(LIST_BG, this.leftPos + listBGX, this.topPos + listBGY, 0.0F, 0.0F,
                71, 60, 71, 60);
        RenderSystem.disableBlend();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(this.leftPos + 128,this.topPos + 5.75, 0); // 'beacon' render with sign texture translate
        guiGraphics.pose().scale(64,88,0); // 'beacon' render with sign texture scale

        // this is the part that renders the sign preview (very-hacky, but works)
        if(ResourceLocation.tryParse(this.menu.be.textureOn) == null){
            BeaconRenderer.renderBeaconBeam(guiGraphics.pose(), guiGraphics.bufferSource(),ResourceLocation.parse(this.menu.be.fallbackSignTexture + Utilities.imgFileEnding),
                    0,2,0,0,1, Utilities.whiteColor,1,0);
        }
        else{
            if(deltop < 0){
                BeaconRenderer.renderBeaconBeam(guiGraphics.pose(), guiGraphics.bufferSource(),ResourceLocation.parse(this.menu.be.textureOn + Utilities.imgFileEnding),
                        0,2,0,0,1, Utilities.whiteColor,1,0);
            }
            else{
                BeaconRenderer.renderBeaconBeam(guiGraphics.pose(), guiGraphics.bufferSource(),ResourceLocation.parse(this.menu.be.textureOnAlt + Utilities.imgFileEnding),
                        0,2,0,0,1, Utilities.whiteColor,1,0);
            }
        }
        guiGraphics.pose().popPose();

        // scrollbar on top of the list bg
        int k = (int)(41.0F * this.scrollOffs);
        ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("container/loom/scroller");
        guiGraphics.blitSprite(resourcelocation, this.leftPos + scrollerStartX, this.topPos + scrollerStartY + k, 12, 15);
        Lighting.setupForFlatItems();

        int j2 = this.leftPos + listButtonStartX;
        int k2 = this.topPos + listButtonStartY;
        List<MultipurposeSignType> list = this.menu.getList(); // no caching of data required now

        // sign type preview 'buttons'
        label64:
        for (int l = 0; l < 4; l++) {
            for (int i1 = 0; i1 < 4; i1++) {
                int j1 = l + startRow;
                int k1 = j1 * 4 + i1;
                if (k1 >= list.size()) {
                    break label64;
                }

                int x = j2 + i1 * buttonSizeX;
                int y = k2 + l * buttonSizeY;
                boolean flag = mouseX >= x && mouseY >= y && mouseX < x + buttonSizeX && mouseY < y + buttonSizeY;
                ResourceLocation resourcelocation1;
                if (k1 == this.menu.be.indexId) {
                    resourcelocation1 = RevertedButton.LASER_SPRITES.get(true,false);
                }
                else if (flag) {
                    resourcelocation1 = RevertedButton.LASER_SPRITES.get(true,true);
                }
                else {
                    resourcelocation1 = RevertedButton.LASER_SPRITES.get(false,false);
                }

                // where the current 'button' is displayed
                guiGraphics.blitSprite(resourcelocation1, x, y, buttonSizeX, buttonSizeY);
                if(MultipurposeSignMenu.signTypes != null){
                    // the smooth values used for interpolating from one position in the texture to another and back
                    double delta = Math.sin(0.75 * Math.cos(6.32 * Util.getMillis() / 3800.0 / Math.max((double)l * 0.5, 3.0))) / 2.5;
                    double deltaY = Math.sin(0.75 * Math.cos(6.28 * Util.getMillis() / 3200.0 / Math.max((double)l * 0.5, 3.0))) / 2.5;

                    ResourceLocation res = ResourceLocation.parse(MultipurposeSignMenu.signTypes.get(k1).onStateTexture() + ".png");
                    if(res == null){
                        res = ResourceLocation.parse("thingamajigsrailroadways/textures/entity/multipurpose_sign_variants/multipurpose_sign.png");
                    }
                    else{
                        if(deltop < 0){
                            res = ResourceLocation.parse(MultipurposeSignMenu.signTypes.get(k1).onStateTexture() + ".png");
                        }
                        else{
                            res = ResourceLocation.parse(MultipurposeSignMenu.signTypes.get(k1).alternateOnStateTexture() + ".png");
                        }
                    }

                    // moving the sprite preview around to show the whole texture
                    double rangeLarge = Mth.lerp(delta,2,10);
                    double rangeLargeY = Mth.lerp(deltaY,32,34);
                    guiGraphics.blit(res, x,y,
                            (float)rangeLarge + 70f,(float)rangeLargeY + 72f,
                            14,14,
                            64,64);
                }
            }
        }
        Lighting.setupFor3DItems();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);

        String translationKey = this.menu.be.holderList.typesHolderObjectList().get(0).getSignType().translationKey();

        Component testComponent = Component.translatable(translationKey);
        if(testComponent.getString().length() > 16){
            guiGraphics.drawString(this.font,Component.translatable("container.railroadways.multipurpose_sign.sign_type")
                            .append(Component.literal(testComponent.getString(16)).append("...")),
                    this.titleLabelX + 9,this.titleLabelY + 98,
                    Utilities.whiteColor,true);
        }
        else{
            guiGraphics.drawString(this.font,Component.translatable("container.railroadways.multipurpose_sign.sign_type")
                            .append(Component.translatable(translationKey)),
                    this.titleLabelX + 9,this.titleLabelY + 98,
                    Utilities.whiteColor,true);
        }

        guiGraphics.drawString(this.font,Component.translatable("title.railroadways.y_rotation")
                        .append(Component.literal(": "))
                        .append(Component.literal(String.valueOf(this.menu.be.yAngle))),
                this.titleLabelX,this.titleLabelY + 115,
                Utilities.whiteColor,true);

        guiGraphics.drawString(this.font,Component.translatable("title.railroadways.z_rotation")
                        .append(Component.literal(": "))
                        .append(Component.literal(String.valueOf(this.menu.be.zAngle))),
                this.titleLabelX,this.titleLabelY + 125,
                Utilities.whiteColor,true);

        // tooltip to warn about the need for two textures for alternating textures toggle to do anything useful
        if(alternatingModeEnable != null) {
            if(mouseX >= alternatingModeEnable.getX() && mouseX <= alternatingModeEnable.getX() + alternatingModeEnable.getWidth()
            && mouseY >= alternatingModeEnable.getY() && mouseY <= alternatingModeEnable.getY() + alternatingModeEnable.getHeight()){
                // once the real mouse x and mouse y is ready, substitute a fake position to correct tooltip position
                guiGraphics.renderTooltip(this.font,
                        Component.translatable("button.railroadways.alternating_textures.tooltip")
                                .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY),
                        mouseX - 82,mouseY - 4);
            }
        }
    }


    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return super.charTyped(codePoint,modifiers);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        return super.keyPressed(key,b,c);
    }

    public void setup(){
        int horzLeftButtonPos = leftPos + 100;
        int topRowButtonY = topPos + 68;
        int spacingButtonWidth = 2;
        int spacingButtonHeight = spacingButtonWidth;
        float lowPitch = 0.95f;
        float normalPitch = 1.0f;

        String assetID = "thingamajigslongroads:test";

        try{
            assetID = this.menu.be.holderList.typesHolderObjectList().getFirst().getSignType().assetId().toString();
        }
        catch (Exception e){
            LogUtils.getLogger().error("Multipurpose Sign Type SELECT_SIGN_TYPE did not return data to MultipurposeSignScreen properly: {}", e.getMessage());
        }


        decreaseRotation = new RevertedButton(horzLeftButtonPos,topRowButtonY + 92,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                    this.menu.pos,
                    this.menu.be.yAngle - 0.5f,
                    this.menu.be.zAngle,
                    this.menu.be.indexId,
                    false,
                    this.menu.be.alternatingTextures
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        int horzRightButtonPos = decreaseRotation.getX() + decreaseRotation.getWidth() + spacingButtonWidth;

        increaseRotation = new RevertedButton(horzRightButtonPos,topRowButtonY + 92,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_gate_rot"),(handler) -> {
            PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                    this.menu.pos,
                    this.menu.be.yAngle + 0.5f,
                    this.menu.be.zAngle,
                    this.menu.be.indexId,
                    false,
                    this.menu.be.alternatingTextures
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};

        roundRotation = new RevertedButton(horzLeftButtonPos - 15,topRowButtonY + 72,90,16,
                Component.translatable("button.thingamajigsrailroadways.update_rotation"),(handler)->{
            PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                    this.menu.pos,
                    Math.round(this.menu.be.yAngle),
                    this.menu.be.zAngle,
                    this.menu.be.indexId,
                    false,
                    this.menu.be.alternatingTextures
            ));
        });

        alternatingModeEnable = new ActionCheckbox(decreaseRotation.getX() - 90,decreaseRotation.getY(),64,Component.translatable("checkbox.title.enable_alternating_textures")
                .withStyle(ChatFormatting.WHITE),this.font,this.menu.be.alternatingTextures){
            @Override
            public void onClick(double mouseX, double mouseY, int button) {
                this.onPress();
                if(this.selected()){
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON,1.0f));
                }
                else{
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,1.0f));
                }
                PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                        menu.pos,
                        menu.be.yAngle,
                        menu.be.zAngle,
                        menu.be.indexId,
                        false,
                        this.selected()
                ));
            }

            @Override
            public @NotNull Tooltip getTooltip() {
                return Tooltip.create(Component.translatable("button.railroadways.alternating_textures.tooltip"),Component.translatable("button.railroadways.alternating_textures.tooltip"));
            }
        };

        decreaseZRotation = new RevertedButton(decreaseRotation.getX(),decreaseRotation.getY() + 20,64,16,
                Component.translatable("button.thingamajigsrailroadways.dec_z_rot"),(handler) -> {
            PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                    this.menu.pos,
                    this.menu.be.yAngle,
                    this.menu.be.zAngle - 0.5f,
                    this.menu.be.indexId,
                    false,
                    this.menu.be.alternatingTextures
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,lowPitch));
        }){};

        increaseZRotation = new RevertedButton(increaseRotation.getX(),increaseRotation.getY() + 20,64,16,
                Component.translatable("button.thingamajigsrailroadways.inc_z_rot"),(handler) -> {
            PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                    this.menu.pos,
                    this.menu.be.yAngle,
                    this.menu.be.zAngle + 0.5f,
                    this.menu.be.indexId,
                    false,
                    this.menu.be.alternatingTextures
            ));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,normalPitch));
        }){};
    }


    // from Loom: mouse actions and controls (edited)
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int i = this.leftPos + listButtonStartX;
        int j = this.topPos + listButtonStartY;

        // math and server data for list of buttons when clicked
        for (int k = 0; k < 4; k++) { // was k < 4
            for (int l = 0; l < 4; l++) { // was l < 4
                double d0 = mouseX - (double)(i + l * 14);
                double d1 = mouseY - (double)(j + k * 14);
                int i1 = k + this.startRow;
                int buttonIndex = i1 * 4 + l;
                if (d0 >= 0.0 && d1 >= 0.0 && d0 < 14.0 && d1 < 14.0 && this.menu.clickedSignTypeSelectorButton(this.minecraft.player, buttonIndex)) {
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonIndex);
                    PacketDistributor.sendToServer(new MultipurposeSignTypeUpdatePayload(
                            this.menu.pos,
                            this.menu.be.yAngle,
                            this.menu.be.zAngle,
                            buttonIndex,
                            true,
                            this.menu.be.alternatingTextures
                    ));
                    return true;
                }
            }
        }

        i = this.leftPos + 119;
        j = this.topPos + 9;
        if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 56)) {
            this.scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.menu.signTypes.size() - 4;
        if (this.scrolling && i > 0) {
            int j = this.topPos + 13;
            int k = j + 56;
            this.scrollOffs = ((float)mouseY - (float)j - 7.5F) / ((float)(k - j) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startRow = Math.max((int)((double)(this.scrollOffs * (float)i) + 0.5), 0);
            return true;
        }
        else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = this.menu.signTypes.size() - 4;
        if (i > 0) {
            float f = (float)scrollY / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startRow = Math.max((int)(this.scrollOffs * (float)i + 0.5F), 0);
        }

        return true;
    }

    @Override
    public boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        return mouseX < (double)guiLeft
                || mouseY < (double)guiTop
                || mouseX >= (double)(guiLeft + this.imageWidth)
                || mouseY >= (double)(guiTop + this.imageHeight);
    }

    @Override
    public void containerTick(){
        if(startRow > this.menu.signTypes.size()){
            startRow = 0;
            scrollOffs = 0.0f;
        }
    }
}
