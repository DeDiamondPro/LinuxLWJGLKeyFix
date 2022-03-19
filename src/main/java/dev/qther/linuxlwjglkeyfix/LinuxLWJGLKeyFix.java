package dev.qther.linuxlwjglkeyfix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;

@Mod(
        modid = LinuxLWJGLKeyFix.MODID,
        name = LinuxLWJGLKeyFix.MOD_NAME,
        version = LinuxLWJGLKeyFix.VERSION,
        acceptableRemoteVersions = "*"
)
public class LinuxLWJGLKeyFix {
    public static final String MODID = "linuxlwjglkeyfix";
    public static final String MOD_NAME = "LinuxLWJGLKeyFix";
    public static final String VERSION = "1.azerty";

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final HashMap<Character, Integer> triggers = new HashMap<Character, Integer>() {{
        put('&', 0);
        put('é', 1);
        put('"', 2);
        put('\'', 3);
        put('(', 4);
        put('§', 5);
        put('è', 6);
        put('!', 7);
        put('ç', 8);
    }};

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (mc.thePlayer != null && Keyboard.isCreated() && Keyboard.getEventKeyState()) {
            char pogChar = Keyboard.getEventCharacter();
            if (triggers.containsKey(pogChar)) {
                int i = triggers.get(pogChar);
                if (mc.gameSettings.keyBindsHotbar[i].getKeyCode() == i + 2) mc.thePlayer.inventory.currentItem = i;
            }
        }
    }

    @SubscribeEvent
    public void onGuiPress(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        if (event.gui instanceof GuiContainer && mc.thePlayer != null && Keyboard.isCreated() && Keyboard.getEventKeyState()) {
            char pogChar = Keyboard.getEventCharacter();
            GuiContainer gui = (GuiContainer) event.gui;
            Slot slot = gui.getSlotUnderMouse();
            if (slot != null && triggers.containsKey(pogChar)) {
                mc.playerController.windowClick(
                        gui.inventorySlots.windowId,
                        slot.slotNumber,
                        triggers.get(pogChar),
                        2,
                        event.gui.mc.thePlayer
                );
                event.setCanceled(true);
            }
        }
    }
}
