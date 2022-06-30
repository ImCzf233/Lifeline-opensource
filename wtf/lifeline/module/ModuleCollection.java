package wtf.lifeline.module;


import java.util.HashMap;

public class ModuleCollection {

    private HashMap<Class<? extends Module>, Module> modules = new HashMap<>();

    public Module get(Class<? extends Module> mod) {
        return this.modules.get(mod);
    }

    public class moduleCollection {
    }
}
