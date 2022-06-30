package wtf.lifeline.module.modules.world;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventTick;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.ModuleManager;
import wtf.lifeline.module.value.Numbers;
import wtf.lifeline.module.value.Option;
import wtf.lifeline.module.value.Value;
import wtf.lifeline.utils.Helper;
import wtf.lifeline.utils.TimerUtil;
import wtf.lifeline.Client;
import wtf.lifeline.management.FileManager;

public class Saver extends Module {
    private Option<Boolean> auto = new Option<Boolean>("AutoSave", "AutoSave", false);
    private Option<Boolean> autoi = new Option<Boolean>("AutoSaveINFO", "AutoSaveINFO", false);
    private static Numbers<Double> SaverDelay = new Numbers("SaverDelay","SaverDelay", 5000.0, 0.0, 100000.0, 1.0);
    TimerUtil delay = new TimerUtil();
    public Saver() {
        super("Saver", new String[]{}, Category.World);
        addValues(this.auto,this.autoi,this.SaverDelay);
    }
    public void SaveFile() {
        String values = "";
        Client.instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabled = "";
        Client.instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled()) continue;
            enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabled, false);
        String Hiddens = "";
        for (Module m : ModuleManager.getModules()) {
            if(m.wasRemoved())Hiddens = String.valueOf(Hiddens) + m.getName() + System.lineSeparator(); {
            }
        }
        String colors = "";
        for (Module m : ModuleManager.getModules()) {
            colors = colors + m.getName() + ":" + String.format("%s%s", m.getColor(), System.lineSeparator());
        }


        FileManager.save("Colors.txt",colors,false);


    }
    @Override
    public void onEnable() {
        if (this.auto.getValue() == false) {
            SaveFile();
            setEnabled(false);

        }
    }
    @EventTarget
    public void onUpdate(EventTick e) {
            if (delay.delay(SaverDelay.getValue().floatValue()) && this.auto.getValue() == true) {
                SaveFile();
                if (this.autoi.getValue() == true) {
                    Helper.sendMessage("已自动保存配置");
                }
                delay.reset();
            }
    }
}
