package me.jestem.talk_chat.mixin;

import me.jestem.talk_chat.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EditBox.class)
public abstract class EditBoxSoundEffect {

    private int prevLen = 0;
    private long soundTime = 0;

    @Inject(method = "onValueChange", at = @At("RETURN"))
    private void onValueChange(String string, CallbackInfo ci) {
        if (!((EditBox)(Object)this).isFocused() || !Config.config.enabled) {
            prevLen = string.length();
            return;
        }

        if(!(string.length() > prevLen) && !(string.length() < prevLen)) return;

        long now = System.currentTimeMillis();
        int delay = string.length() > prevLen ? Config.config.delay : Config.config.delay_del;
        String snd = string.length() > prevLen ? Config.config.sound : Config.config.sound_del;
        if(now-soundTime >= delay) {
            soundTime = now;
            Minecraft client = Minecraft.getInstance();
            if (client.getSoundManager() != null) {
                client.getSoundManager().play(SimpleSoundInstance.forUI(Config.config.getSound(snd), 1.0F));
            }
        }
        prevLen = string.length();
    }
}