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
    private ResourceKey<Level> playerDestination(ServerPlayer player, ServerLevel currentLevel){
        if(currentLevel.dimension()==ModDimensions.SNAIL_HOUSE_KEY){
            return player.getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).resolve().get().getDim();
        }else{
            return ModDimensions.SNAIL_HOUSE_KEY;
        }
    }

    public void tryToTeleportPlayer(ServerPlayer player, ServerLevel currentLevel, MinecraftServer minecraftServer){

        if(currentLevel!=null){

            ResourceKey<Level> destinationDimension = this.playerDestination(player, currentLevel);

            if(destinationDimension!=null && minecraftServer!=null){

                ServerLevel destinationLevel = minecraftServer.getLevel(destinationDimension);

                if (destinationLevel != null && minecraftServer.isNetherEnabled()) {

                    if(destinationDimension == ModDimensions.SNAIL_HOUSE_KEY){

                        this.teleportPlayerInHisHouse(player, currentLevel, minecraftServer);

                    }else{

                        this.teleportPlayerBack(player, destinationLevel);

                    }

                }

            }

        }
    }

    private void teleportPlayerInHisHouse(ServerPlayer player, ServerLevel currentLevel, MinecraftServer minecraftServer) {

        //We save the player dimension origin and pos//
        player.getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(posBeforeTp -> {
            posBeforeTp.setPos(player.getOnPos());
            posBeforeTp.setDim(currentLevel.dimension());
        });

        //We take the data about all houses and prepare the destination world//
        SnailHouseTeleportData snailHouseTeleportData = SnailHouseTeleportData.get(minecraftServer);
        ServerLevel snailHouseLevel = minecraftServer.getLevel(ModDimensions.SNAIL_HOUSE_KEY);

        if(snailHouseLevel != null) {

            //We check if the player has a house//
            if (!snailHouseTeleportData.playerHasHouse(player)) {
                this.generateHouse(player, snailHouseTeleportData, snailHouseLevel);
            }

            //We teleport the player to his house location//
            BlockPos playerHousePos = snailHouseTeleportData.getHousePosByPlayer(player);
            player.teleportTo(snailHouseLevel, playerHousePos.getX() + 0.5, playerHousePos.getY() + 1.5, playerHousePos.getZ() + 0.5, player.getYRot(), player.getXRot());

        }

    }

    private void teleportPlayerBack(ServerPlayer player, ServerLevel destinationLevel) {
        player.getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(posBeforeTp -> {
            player.teleportTo(destinationLevel, posBeforeTp.getPos().getX(), posBeforeTp.getPos().getY()+1, posBeforeTp.getPos().getZ(), player.getYRot(), player.getXRot());
        });
    }

    /*-------------------------------------------------------*/


    /*-----------------For houses interactions---------------*/
    private void buildHouse(ServerLevel level, BlockPos blockPos){
        Optional<StructureTemplate> optional = Optional.empty();
        StructureManager structuremanager = level.getStructureManager();

        //We take the house pattern (.nbt file)//
        try {
            optional = structuremanager.get(new ResourceLocation(NatureRain.MOD_ID, "littlesnail_house"));
        } catch (ResourceLocationException ignored) {

        }

        //We generate the structure into the snail dimension//
        if (optional.isPresent()){
            StructureTemplate structureTemplate = optional.get();

            StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true);

            structureTemplate.placeInWorld(level, blockPos.relative(Direction.WEST, 4).relative(Direction.NORTH, 4), blockPos.relative(Direction.WEST, 4).relative(Direction.NORTH, 4), structureplacesettings, new Random(level.getSeed()), 2);
        }
    }

    private void generateHouse(ServerPlayer player, SnailHouseTeleportData snailHouseTeleportData, ServerLevel snailHouseLevel) {

        //If we have a House.class it will be here to init it//
        int nbHouse = snailHouseTeleportData.getNumberOfHouse();
        int x = (nbHouse*160)+8;
        int y = 1;
        int z = (nbHouse*160)+8;
        BlockPos housePos = new BlockPos(x, y, z);
        this.buildHouse(snailHouseLevel, housePos);

        //We add the house in the data//
        snailHouseTeleportData.addHouseInList(player, housePos);
    }

    /*-------------------------------------------------------*/


}
