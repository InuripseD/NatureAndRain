package fr.inuripse.naturerain.networking;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.networking.packet.ItemStackRendererSyncStoCpacket;
import fr.inuripse.naturerain.networking.packet.KeyPressedC2Spacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(NatureRain.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ItemStackRendererSyncStoCpacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackRendererSyncStoCpacket::new)
                .encoder(ItemStackRendererSyncStoCpacket::toBytes)
                .consumer(ItemStackRendererSyncStoCpacket::handle)
                .add();

        net.messageBuilder(KeyPressedC2Spacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KeyPressedC2Spacket::new)
                .encoder(KeyPressedC2Spacket::toBytes)
                .consumer(KeyPressedC2Spacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
