package fr.inuripse.naturerain.util.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public class PlayerPosBeforeTp {

    private BlockPos pos;


    public void saveNBTData(CompoundTag compoundTag){
        compoundTag.put("playerposbeforetp", NbtUtils.writeBlockPos(pos));
    }

    public void loadNBTData(CompoundTag compoundTag){
        pos = NbtUtils.readBlockPos(compoundTag.getCompound("playerposbeforetp"));
    }

    public void copyFrom(PlayerPosBeforeTp playercap) {
        this.pos = playercap.pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }
}
