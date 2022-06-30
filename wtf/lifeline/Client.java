/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline;

import org.lwjgl.opengl.Display;
import wtf.lifeline.command.CommandManager;
import wtf.lifeline.events.EventManager;
import wtf.lifeline.gui.login.AltManager;
import wtf.lifeline.management.FileManager;
import wtf.lifeline.management.FriendManager;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.ModuleCollection;
import wtf.lifeline.module.ModuleManager;
import wtf.lifeline.module.modules.render.Radar;
import wtf.lifeline.module.modules.render.UI.TabUI;
import wtf.lifeline.module.value.Value;
import wtf.lifeline.utils.objects.Dragging;

import java.awt.*;
import java.util.HashMap;

import static wtf.lifeline.module.modules.render.Animations.x;
import static wtf.lifeline.module.modules.render.Animations.y;


public class Client {
    public static final String name = "LIFELINE";
    public static final String version = "Preview";
    public static final String dev = "LIFELINE TEAM";
    public static boolean publicMode = false;
    public static Client instance = new Client();

    private static ModuleManager modulemanager;
    private CommandManager commandmanager;
    private AltManager altmanager;
    private FriendManager friendmanager;
    private ModuleCollection moduleCollection = new ModuleCollection();
    private TabUI tabui;
    public static long playTimeStart = 0;
    //public static ResourceLocation CLIENT_CAPE = new ResourceLocation("ETB/cape.png");

    public void initiate() {
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        EventManager.register(this);
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
        this.tabui = new TabUI();
        this.tabui.init();
        this.altmanager = new AltManager();
        AltManager.init();
        AltManager.setupAlts();
        FileManager.init();
        Display.setTitle(name + " " + version + " " + "by " + dev);
    }


    public static ModuleManager getModuleManager() {
        return modulemanager;
    }

    public ModuleCollection getModuleCollection() {
        return moduleCollection;
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public final Color getClientColor() {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public void shutDown() {
        String values = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabled = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled()) continue;
            enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabled, false);


        Module module = null;
        String name1 = name;
        Object String;
        String = null;
        Object DragManager;
        DragManager = null;
        String name11;
        name11 = null;

    }

    public Dragging createDrag(Radar radar, String radar1, int i, int i1) {
        return null;
    }
}



