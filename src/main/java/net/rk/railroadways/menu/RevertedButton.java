package net.rk.railroadways.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;

public class RevertedButton extends ExtendedButton{
    public RevertedButton(int xPos, int yPos, int width, int height, Component displayString, OnPress handler) {
        super(xPos, yPos, width, height, displayString, handler);
    }

    public RevertedButton(int xPos, int yPos, int width, int height, Component displayString, OnPress handler, CreateNarration createNarration) {
        super(xPos, yPos, width, height, displayString, handler, createNarration);
    }

    public RevertedButton(Builder builder) {
        super(builder);
    }

    // visible yet expandable button sprites
    public static final WidgetSprites LASER_SPRITES = new WidgetSprites(
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/pressed_button"),
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/disabled_button"),
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/selected_button")
    );

    public static final WidgetSprites HC_BUTTON_SPRITES = new WidgetSprites(
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/pressed_button_hc"),
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/disabled_button_hc"),
            ResourceLocation.parse("thingamajigsrailroadways:container/crossing_components/selected_button_hc")
    );

    // override from Button.class as ExtendedButton.renderWidget doesn't work when object is already an inherited Button.class
    // added extended button string rendering method in order to allow button to look and behave as if one single class
    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float ptick) {
        float defColor = 1.0f;
        Minecraft mc = Minecraft.getInstance();

        gui.setColor(defColor,defColor,defColor,this.alpha);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        if(mc.options.highContrast().get()){
            gui.blitSprite(HC_BUTTON_SPRITES.get(this.active,this.isHoveredOrFocused()),this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }
        else{
            gui.blitSprite(LASER_SPRITES.get(this.active,this.isHoveredOrFocused()),this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }
        gui.setColor(defColor,defColor,defColor,defColor);

        //this.renderString(gui,mc.font, getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
        final FormattedText buttonText = mc.font.ellipsize(this.getMessage(),this.width - 6); // contain in borders
        gui.drawCenteredString(mc.font, Language.getInstance().getVisualOrder(buttonText), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, getFGColor());
    }
}
