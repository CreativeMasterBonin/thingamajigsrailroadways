package net.rk.railroadways.screen.widget;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class DynamicEditBox extends EditBox {
    public DynamicEditBox(Font font, int width, int height, Component message) {
        super(font, width, height, message);
    }

    public DynamicEditBox(Font font, int x, int y, int width, int height, Component message) {
        super(font, x, y, width, height, message);
        this.setMaxLength(256);
        this.setCanLoseFocus(true);
        this.setEditable(true);
        this.setHint(Component.translatable("editbox.hint.dynamic_sign_crossbuck").withStyle(ChatFormatting.DARK_GRAY));
        this.isFocused();
    }

    public DynamicEditBox(Font font, int x, int y, int width, int height, @Nullable EditBox editBox, Component message) {
        super(font, x, y, width, height, editBox, message);
    }
}
