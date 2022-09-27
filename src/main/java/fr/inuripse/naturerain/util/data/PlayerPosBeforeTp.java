package fr.inuripse.naturerain.util.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class PlayerPosBeforeTp {

    private BlockPos pos = BlockPos.ZERO;
    private ResourceKey<Level> dim = Level.OVERWORLD;


    public void saveNBTData(CompoundTag compoundTag){
        compoundTag.put("playerposbeforetp", NbtUtils.writeBlockPos(pos));
        ResourceLocation.CODEC.encodeStart(NbtOps.INSTANCE, this.dim.location()).result().ifPresent((dimension) -> {
            compoundTag.put("playerdimbeforetp", dimension);
        });
    }

    public void loadNBTData(CompoundTag compoundTag){
        pos = NbtUtils.readBlockPos(compoundTag.getCompound("playerposbeforetp"));
        if (compoundTag.contains("playerdimbeforetp")) {
            this.dim = Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, compoundTag.get("playerdimbeforetp")).result().orElse(Level.OVERWORLD);
        }
    }

    public void copyFrom(PlayerPosBeforeTp playercap) {
        this.pos = playercap.pos;
        this.dim = playercap.dim;
    }

    public BlockPos getPos() {
        return pos;
    }

    public ResourceKey<Level> getDim(){
        return dim;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void setDim(ResourceKey<Level> dim){
        this.dim = dim;
    }
}
