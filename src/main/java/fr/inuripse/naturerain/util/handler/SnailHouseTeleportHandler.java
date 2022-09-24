package fr.inuripse.naturerain.util.handler;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.item.armor.SnailShellChestplate;
import fr.inuripse.naturerain.util.capability.PlayerPosBeforeTpCapability;
import fr.inuripse.naturerain.util.data.SnailHouseTeleportData;
import fr.inuripse.naturerain.world.dimension.ModDimensions;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SnailHouseTeleportHandler {

    /*-----------------For player interactions---------------*/
    public boolean isPlayerReadyForTeleportation(ServerPlayer player){
        return player!=null && player.isCrouching() && playerWearSnailChestplate(player) && !player.isPassenger();
    }

    private boolean playerWearSnailChestplate(ServerPlayer player){
        for(ItemStack armorSlot : player.getArmorSlots()){
            if(armorSlot.getItem() instanceof SnailShellChestplate) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public ResourceKey<Level> playerDestination(ServerLevel serverLevel){
        if(serverLevel.dimension() == Level.OVERWORLD){
            return ModDimensions.SNAIL_HOUSE_KEY;
        }else if(serverLevel.dimension() == ModDimensions.SNAIL_HOUSE_KEY){
            return Level.OVERWORLD;
        }else{
            return null;
        }
    }

    public void tryToTeleportPlayer(ServerPlayer player, ServerLevel serverLevel, ServerLevel destinationWorld, MinecraftServer minecraftServer){

        SnailHouseTeleportData snailHouseTeleportData = SnailHouseTeleportData.get(minecraftServer);

        //Player doesn't have a house! We create one!
        if(!snailHouseTeleportData.playerHasHouse(player)){
            int nbHouse = snailHouseTeleportData.getNumberOfHouse();
            int x = (nbHouse*16)+8;
            int y = 1;
            int z = (nbHouse*16)+8;
            BlockPos housePos = new BlockPos(x, y, z);
            setHouse(destinationWorld, housePos);
            snailHouseTeleportData.addHouseInList(player, housePos);
        }

        //We teleport the player!
        if(destinationWorld.dimension()==Level.OVERWORLD){

            AtomicInteger xPlayer = new AtomicInteger();
            AtomicInteger yPlayer = new AtomicInteger();
            AtomicInteger zPlayer = new AtomicInteger();

            player.getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(posBeforeTp -> {
                xPlayer.set(posBeforeTp.getPos().getX());
                yPlayer.set(posBeforeTp.getPos().getY());
                zPlayer.set(posBeforeTp.getPos().getZ());
            });

            player.teleportTo(destinationWorld, xPlayer.get(), yPlayer.get()+1, zPlayer.get(), player.getXRot(), player.getYRot());
        }else{

            player.getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(posBeforeTp -> {
                posBeforeTp.setPos(player.getOnPos());
            });

            int xHouse = snailHouseTeleportData.getPosByPlayer(player).getX();
            int yHouse = snailHouseTeleportData.getPosByPlayer(player).getY()+1;
            int zHouse = snailHouseTeleportData.getPosByPlayer(player).getZ();
            player.teleportTo(destinationWorld, xHouse+0.5, yHouse+0.5, zHouse+0.5, player.getXRot(), player.getYRot());
        }
    }
    /*-------------------------------------------------------*/


    /*-----------------For houses interactions---------------*/
    public void setHouse(ServerLevel level, BlockPos blockPos){
        Optional<StructureTemplate> optional = null;
        StructureManager structuremanager = level.getStructureManager();
        try {
            optional = structuremanager.get(new ResourceLocation(NatureRain.MOD_ID, "littlesnail_house"));
        } catch (ResourceLocationException resourcelocationexception) {

        }
        if (optional.isPresent()){
            StructureTemplate structureTemplate = optional.get();

            StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true);

            structureTemplate.placeInWorld(level, blockPos.relative(Direction.WEST, 4).relative(Direction.NORTH, 4), blockPos.relative(Direction.WEST, 4).relative(Direction.NORTH, 4), structureplacesettings, new Random(level.getSeed()), 2);
        }
        level.setBlock(blockPos, ModBlocks.DEEPSLATE_ZIRMS_ORE.get().defaultBlockState(), 1);
    }

    /*-------------------------------------------------------*/


}
