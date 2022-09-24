package fr.inuripse.naturerain.util.capability;

import fr.inuripse.naturerain.util.data.PlayerPosBeforeTp;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPosBeforeTpCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerPosBeforeTp> PLAYER_POS_BEFORE_TP = CapabilityManager.get(new CapabilityToken<>(){});

    private PlayerPosBeforeTp playerPosBeforeTp = null;
    private final LazyOptional<PlayerPosBeforeTp> opt = LazyOptional.of(this::createPlayerPosBeforeTp);

    private PlayerPosBeforeTp createPlayerPosBeforeTp() {
        if(playerPosBeforeTp == null){
            playerPosBeforeTp = new PlayerPosBeforeTp();
        }
        return playerPosBeforeTp;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == PLAYER_POS_BEFORE_TP){
            return opt.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerPosBeforeTp().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerPosBeforeTp().loadNBTData(nbt);
    }
}
