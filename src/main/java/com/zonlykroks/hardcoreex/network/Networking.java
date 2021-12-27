package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

@SuppressWarnings("UnusedAssignment")
public final class Networking {
    private static final String VERSION = "hardcore-ex network 2021.12.26-23.00";

    public static NetworkManager getManager() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getNetworkManager();
    }

    public static SimpleChannel channel;

    static {
        int id = 0;
        channel = NetworkRegistry.ChannelBuilder.named(HardcoreExtended.rl("network"))
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .networkProtocolVersion(() -> VERSION)
                .simpleChannel();

        /////////////////////////////////
        //     PACKET REGISTRATION     //
        /////////////////////////////////
        channel.messageBuilder(LoginPacket.Reply.class, id++)
                .loginIndex(LoginPacket::getLoginIndex, LoginPacket::setLoginIndex)
                .decoder(buffer -> new LoginPacket.Reply())
                .encoder((msg, buffer) -> {
                })
                .consumer(FMLHandshakeHandler.indexFirst((hh, msg, ctx) -> msg.handle(ctx)))
                .add();
        channel.messageBuilder(ChallengeFailedPacket.class, id++)
                .decoder(ChallengeFailedPacket::new)
                .encoder(ChallengeFailedPacket::toBytes)
                .consumer(ChallengeFailedPacket::handle)
                .add();
        channel.messageBuilder(RequestChallengesPacket.class, id++)
                .decoder(RequestChallengesPacket::new)
                .encoder(RequestChallengesPacket::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(RequestChallengesPacket.Accepted.class, id++)
                .decoder(RequestChallengesPacket.Accepted::new)
                .encoder(RequestChallengesPacket.Accepted::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(RequestChallengesPacket.Rejected.class, id++)
                .decoder(RequestChallengesPacket.Rejected::new)
                .encoder(RequestChallengesPacket.Rejected::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(ChallengeEnabledPacket.class, id++)
                .decoder(ChallengeEnabledPacket::new)
                .encoder(ChallengeEnabledPacket::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(ChallengeDisabledPacket.class, id++)
                .decoder(ChallengeDisabledPacket::new)
                .encoder(ChallengeDisabledPacket::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(EnableChallengePacket.class, id++)
                .decoder(EnableChallengePacket::new)
                .encoder(EnableChallengePacket::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(DisableChallengePacket.class, id++)
                .decoder(DisableChallengePacket::new)
                .encoder(DisableChallengePacket::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
        channel.messageBuilder(SetupDonePacket.class, id++)
                .decoder(buffer -> new SetupDonePacket())
                .encoder(SetupDonePacket::toBytes)
                .consumer(SetupDonePacket::handle)
                .add();
    }

    private Networking() {
    }

    public static void initialize() {
    }

    public static void sendToAllClients(Object packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        channel.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        channel.sendToServer(packet);
    }
}
