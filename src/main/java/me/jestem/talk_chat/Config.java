package me.jestem.talk_chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.io.*;
import java.nio.file.Path;

public class Config {
    public boolean enabled = true;
    public String sound = "minecraft:block.note_block.hat";
    public String sound_del = "minecraft:entity.player.hurt";
    public int delay = 100;
    public int delay_del = 100;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("talkchat.json");
    public static Config config = new Config();

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
            config = GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            save();
        }
    }

    public SoundEvent getSound(String string) {
        try {
            Identifier id = Identifier.parse(string);
            return BuiltInRegistries.SOUND_EVENT
                    .getOptional(id)
                    .orElseGet(() -> SoundEvent.createVariableRangeEvent(id));
        } catch(Exception e) {
            System.err.println("[TalkChat] Failed to load sound " + string);
            e.printStackTrace();
            return SoundEvents.NOTE_BLOCK_HAT.value();
        }
    }
}
