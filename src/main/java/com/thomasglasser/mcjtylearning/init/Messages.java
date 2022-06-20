package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.network.GatherManaPacket;
import com.thomasglasser.mcjtylearning.network.SyncManaToClientPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages
{
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(McJtyLearning.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(GatherManaPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GatherManaPacket::new)
                .encoder(GatherManaPacket::toBytes)
                .consumer(GatherManaPacket::handle)
                .add();

        net.messageBuilder(SyncManaToClientPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncManaToClientPacket::new)
                .encoder(SyncManaToClientPacket::toBytes)
                .consumer(SyncManaToClientPacket::handle)
                .add();
    }

    private static int id()
    {
        return packetId++;
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
