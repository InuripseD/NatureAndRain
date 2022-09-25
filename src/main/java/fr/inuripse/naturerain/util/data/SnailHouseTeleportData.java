package fr.inuripse.naturerain.util.data;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SnailHouseTeleportData extends SavedData {

    private final Map<UUID, BlockPos> playersHouses = new HashMap<>();

    /*----------------To save a get saved data---------------*/

    public static SnailHouseTeleportData create() {
        return new SnailHouseTeleportData();
    }

    public static SnailHouseTeleportData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(SnailHouseTeleportData::load, SnailHouseTeleportData::create, "housemanager");
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listtag = new ListTag();

        playersHouses.forEach((key, value) -> {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.putUUID("player", key);
            compoundtag.put("housePos", NbtUtils.writeBlockPos(value));
            listtag.add(compoundtag);
        });

        pCompoundTag.put(NatureRain.MOD_ID + ":PlayersHouses", listtag);
        return pCompoundTag;
    }

    public static SnailHouseTeleportData load(CompoundTag pCompoundTag) {
        SnailHouseTeleportData data = create();

        ListTag listtag = pCompoundTag.getList(NatureRain.MOD_ID + ":PlayersHouses", Tag.TAG_COMPOUND);

        for(int i = 0; i<listtag.size(); i++){
            data.playersHouses.put(listtag.getCompound(i).getUUID("player"), NbtUtils.readBlockPos(listtag.getCompound(i).getCompound("housePos")));
        }

        return data;
    }

    /*-------------------------------------------------------*/


    /*-----------------For houses interactions---------------*/
    public boolean addHouseInList(ServerPlayer player, BlockPos pos){
        if(!playerHasHouse(player)){
            playersHouses.put(player.getUUID(), pos);
            setDirty();
        }else{
            return false;
        }
        return true;
    }

    public Map<UUID, BlockPos> getHouses(){
        return playersHouses;
    }

    public boolean playerHasHouse(ServerPlayer player){
        return playersHouses.containsKey(player.getUUID());
    }

    public int getNumberOfHouse(){
        return playersHouses.size();
    }

    public BlockPos getHousePosByPlayer(ServerPlayer player){
        return playersHouses.get(player.getUUID());
    }
    /*-------------------------------------------------------*/

}
