package net.rk.railroadways.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ActionCheckbox extends AbstractWidget{
    private static final ResourceLocation CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("widget/checkbox_selected_highlighted");
    private static final ResourceLocation CHECKBOX_SELECTED_SPRITE = ResourceLocation.withDefaultNamespace("widget/checkbox_selected");
    private static final ResourceLocation CHECKBOX_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("widget/checkbox_highlighted");
    private static final ResourceLocation CHECKBOX_SPRITE = ResourceLocation.withDefaultNamespace("widget/checkbox");
    private boolean selected;
    private final ActionCheckbox.OnValueChange onValueChange;
    private final MultiLineTextWidget textWidget;

    public ActionCheckbox(int x, int y, int maxWidth, Component message, Font font, boolean selected){
        super(x, y, 0, 0, message);
        this.width = this.getAdjustedWidth(maxWidth, message, font);
        this.textWidget = (new MultiLineTextWidget(message, font)).setMaxWidth(this.width).setColor(14737632);
        this.height = this.getAdjustedHeight(font);
        this.selected = selected;
        this.onValueChange = ActionCheckbox::doAction;
    }

    public void doAction(boolean selected){}

    private int getAdjustedWidth(int maxWidth, Component message, Font font) {
        return Math.min(getDefaultWidth(message, font), maxWidth);
    }

    private int getAdjustedHeight(Font font) {
        return Math.max(getBoxSize(font), this.textWidget.getHeight());
    }

    static int getDefaultWidth(Component message, Font font) {
        return getBoxSize(font) + 4 + font.width(message);
    }

    public static int getBoxSize(Font font) {
        return 17;
    }

    public void onPress() {
        this.selected = !this.selected;
        this.onValueChange.onValueChange(this, this.selected);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        this.onPress();
    }

    public boolean selected() {
        return this.selected;
    }

    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
        if (this.active) {
            if (this.isFocused()) {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.focused"));
            } else {
                narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.checkbox.usage.hovered"));
            }
        }
    }

    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.enableDepthTest();
        Font font = minecraft.font;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        ResourceLocation resourcelocation;
        if (this.selected) {
            resourcelocation = this.isFocused() ? CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE : CHECKBOX_SELECTED_SPRITE;
        } else {
            resourcelocation = this.isFocused() ? CHECKBOX_HIGHLIGHTED_SPRITE : CHECKBOX_SPRITE;
        }

        int i = getBoxSize(font);
        guiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), i, i);
        int j = this.getX() + i + 4;
        int k = this.getY() + i / 2 - this.textWidget.getHeight() / 2;
        this.textWidget.setPosition(j, k);
        this.textWidget.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnValueChange{
        ActionCheckbox.OnValueChange NOP = ActionCheckbox::doAction;
        void onValueChange(ActionCheckbox var1, boolean var2);
    }
}
