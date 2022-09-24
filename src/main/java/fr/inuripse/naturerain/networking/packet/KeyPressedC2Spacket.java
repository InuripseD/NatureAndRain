package fr.inuripse.naturerain.networking.packet;

import fr.inuripse.naturerain.util.handler.SnailHouseTeleportHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyPressedC2Spacket {

    public KeyPressedC2Spacket(){

    }

    public KeyPressedC2Spacket(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            MinecraftServer minecraftServer = player.getServer();

            SnailHouseTeleportHandler snailHouseTeleportHandler = new SnailHouseTeleportHandler();

            if(snailHouseTeleportHandler.isPlayerReadyForTeleportation(player)){

                ResourceKey<Level> destination = snailHouseTeleportHandler.playerDestination(level);

                if(destination!=null && minecraftServer!=null){

                    ServerLevel destinationWorld = minecraftServer.getLevel(destination);

                    if(destinationWorld != null && minecraftServer.isNetherEnabled()){

                        snailHouseTeleportHandler.tryToTeleportPlayer(player, level, destinationWorld, minecraftServer);

                    }

                }

            }
        });
        return true;
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
    }
}
