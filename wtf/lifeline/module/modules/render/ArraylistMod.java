package wtf.lifeline.module.modules.render;

import net.minecraft.block.state.BlockState;

public class ArraylistMod {
    private BlockState.StateImplementation height;

    public BlockState.StateImplementation getHeight() {
        return height;
    }

    public void setHeight(BlockState.StateImplementation height) {
        this.height = height;
    }
}
