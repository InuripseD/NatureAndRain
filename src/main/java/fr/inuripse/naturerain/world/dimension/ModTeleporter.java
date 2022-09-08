package fr.inuripse.naturerain.world.dimension;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.ITeleporter;

public class ModTeleporter implements ITeleporter {
    protected final ServerLevel level;

    public ModTeleporter(ServerLevel worldIn) {
        this.level = worldIn;
    }
}
