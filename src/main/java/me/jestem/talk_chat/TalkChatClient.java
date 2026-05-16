package me.jestem.talk_chat;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class TalkChatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.load();

        KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("talk_chat", "talk_chat"));
        KeyMapping configKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.talk_chat.config", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKey.consumeClick()) {
                if (client.player != null) {
                    Minecraft.getInstance().setScreen(
                            new ConfigScreen(Component.empty())
                    );
                }
            }
        });
    }
}
