package fr.inuripse.naturerain.block.blockentity;

import fr.inuripse.naturerain.networking.ModMessages;
import fr.inuripse.naturerain.networking.packet.ItemStackRendererSyncStoCpacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MainPillarBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackRendererSyncStoCpacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.empty();

    public MainPillarBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ItemStack getItem(){
        return itemHandler.getStackInSlot(0);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i< itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public boolean placeItem(ItemStack itemStack){
        if (getItemHandler().getStackInSlot(0).isEmpty()) {
            this.getItemHandler().setStackInSlot(0, itemStack.split(1));
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            getItemHandler().setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}
