package me.jestem.talk_chat;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class ConfigScreen extends Screen {
    protected ConfigScreen(Component component) {
        super(component);
    }

    private int textWidth(String string) {
        return this.font.width(Component.translatable(string));
    }

    @Override
    protected void init() {
        StringWidget titleWidget = new StringWidget(this.width/2-textWidth("title.talk_chat.config")/2, 10, textWidth("title.talk_chat.config"), 10, Component.translatable("title.talk_chat.config"), this.font);

        Component enabledLabel = Config.config.enabled ? Component.translatable("manageServer.resourcePack.enabled") : Component.translatable("manageServer.resourcePack.disabled");
        Button buttonWidget = Button.builder(enabledLabel, (btn) -> {
            if(Config.config.enabled) {
                btn.setMessage(Component.translatable("manageServer.resourcePack.disabled"));
                Config.config.enabled = false;
            } else {
                btn.setMessage(Component.translatable("manageServer.resourcePack.enabled"));
                Config.config.enabled = true;
            }
        }).bounds(this.width/2-60, 30, 120, 20).build();

        // TYPING
        StringWidget sndLabelWidget = new StringWidget(50, 90, 150, 10, Component.translatable("text.talk_chat.type_sound"), this.font);
        EditBox sndInputWidget = new EditBox(this.font, 50, 100, 150, 20, Component.translatable("input.talk_chat.type_sound"));
        sndInputWidget.setValue(Config.config.sound);

        // TYPING DELAY
        StringWidget delayLabelWidget = new StringWidget(50, 130, 150, 10, Component.translatable("text.talk_chat.type_delay"), this.font);
        EditBox delayInputWidget = new EditBox(this.font, 50, 140, 150, 20, Component.translatable("input.talk_chat.type_delay"));
        delayInputWidget.setValue(String.valueOf(Config.config.delay));


        // DELETING
        StringWidget delLabelWidget = new StringWidget(this.width-200, 90, 150, 10, Component.translatable("text.talk_chat.delete_sound"), this.font);
        EditBox delInputWidget = new EditBox(this.font, this.width-200, 100, 150, 20, Component.translatable("input.talk_chat.delete_sound"));
        delInputWidget.setValue(Config.config.sound_del);

        // DELETING DELAY
        StringWidget delDelayLabelWidget = new StringWidget(this.width-200, 130, 150, 10, Component.translatable("text.talk_chat.delete_delay"), this.font);
        EditBox delDelayInputWidget = new EditBox(this.font, this.width-200, 140, 150, 20, Component.translatable("input.talk_chat.delete_delay"));
        delDelayInputWidget.setValue(String.valueOf(Config.config.delay_del));


        Button saveButtonWidget = Button.builder(Component.translatable("structure_block.button.save"), (btn) -> {
            if(!sndInputWidget.getValue().isBlank()) {
                Config.config.sound = sndInputWidget.getValue();
                Config.config.sound_del = delInputWidget.getValue();
                if(Integer.parseInt(delayInputWidget.getValue()) < 0) {
                    Config.config.delay = 0;
                } else {
                    Config.config.delay = Integer.parseInt(delayInputWidget.getValue());
                }

                if(Integer.parseInt(delDelayInputWidget.getValue()) < 0) {
                    Config.config.delay_del = 0;
                } else {
                    Config.config.delay_del = Integer.parseInt(delDelayInputWidget.getValue());
                }
            }
            try {
                Config.save();
                this.onClose();
            } catch (Exception e) {
                System.err.println("[TalkChat] Error occured when saving config!");
                e.printStackTrace();
            }
        }).bounds(this.width/2-60, this.height-30, 120, 20).build();

        this.addRenderableWidget(titleWidget);
        this.addRenderableWidget(buttonWidget);

        this.addRenderableWidget(sndLabelWidget);
        this.addRenderableWidget(sndInputWidget);

        this.addRenderableWidget(delayLabelWidget);
        this.addRenderableWidget(delayInputWidget);

        this.addRenderableWidget(delLabelWidget);
        this.addRenderableWidget(delInputWidget);

        this.addRenderableWidget(delDelayLabelWidget);
        this.addRenderableWidget(delDelayInputWidget);

        this.addRenderableWidget(saveButtonWidget);
    }
}
