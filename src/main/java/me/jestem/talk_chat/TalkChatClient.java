package me.jestem.talk_chat;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class TalkChatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.load();

        KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("talk_chat", "talk_chat"));
        KeyMapping configKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.talk_chat.config", InputConstants.Type.KEYBOARD, InputConstants.KEY_K, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKey.consumeClick()) {
                if (client.player != null) {
                    Minecraft.getInstance().gui.setScreen(
                            new ConfigScreen(Component.empty())
                    );
                }
            }
        });
    }
}
