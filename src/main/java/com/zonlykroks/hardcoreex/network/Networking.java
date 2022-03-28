package com.zonlykroks.hardcoreex.network;

import com.zonlykroks.hardcoreex.HardcoreExtended;
import com.zonlykroks.hardcoreex.network.packets.*;
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
                .consumer(ChallengeFailedPacket::handlePacket)
                .add();
        channel.messageBuilder(RequestChallengesPacket.class, id++)
                .decoder(RequestChallengesPacket::new)
                .encoder(RequestChallengesPacket::toBytes)
                .consumer(RequestChallengesPacket::handlePacket)
                .add();
        channel.messageBuilder(OpenChallengesMenuPacket.class, id++)
                .decoder(OpenChallengesMenuPacket::new)
                .encoder(OpenChallengesMenuPacket::toBytes)
                .consumer(OpenChallengesMenuPacket::handlePacket)
                .add();
        channel.messageBuilder(ChallengesStartedPacket.class, id++)
                .decoder(ChallengesStartedPacket::new)
                .encoder(ChallengesStartedPacket::toBytes)
                .consumer(ChallengesStartedPacket::handlePacket)
                .add();
        channel.messageBuilder(ChallengeEnabledPacket.class, id++)
                .decoder(ChallengeEnabledPacket::new)
                .encoder(ChallengeEnabledPacket::toBytes)
                .consumer(ChallengeEnabledPacket::handlePacket)
                .add();
        channel.messageBuilder(ChallengeDisabledPacket.class, id++)
                .decoder(ChallengeDisabledPacket::new)
                .encoder(ChallengeDisabledPacket::toBytes)
                .consumer(ChallengeDisabledPacket::handlePacket)
                .add();
        channel.messageBuilder(EnableChallengePacket.class, id++)
                .decoder(EnableChallengePacket::new)
                .encoder(EnableChallengePacket::toBytes)
                .consumer(EnableChallengePacket::handlePacket)
                .add();
        channel.messageBuilder(DisableChallengePacket.class, id++)
                .decoder(DisableChallengePacket::new)
                .encoder(DisableChallengePacket::toBytes)
                .consumer(DisableChallengePacket::handlePacket)
                .add();
        channel.messageBuilder(SetupDonePacket.class, id++)
                .decoder(buffer -> new SetupDonePacket())
                .encoder(SetupDonePacket::toBytes)
                .consumer(SetupDonePacket::handlePacket)
                .add();
        channel.messageBuilder(ChallengesStartedPacket.class, id++)
                .decoder(ChallengesStartedPacket::new)
                .encoder(ChallengesStartedPacket::toBytes)
                .consumer(ChallengesStartedPacket::handlePacket)
                .add();
        channel.messageBuilder(StartChallengesPacket.class, id++)
                .decoder(StartChallengesPacket::new)
                .encoder(StartChallengesPacket::toBytes)
                .consumer(StartChallengesPacket::handlePacket)
                .add();
    }

    private Networking() {

    }

    public static void initialize() {

    }

    public static void sendToAllClients(PacketToClient<?> packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToAllClients(BiDirectionalPacket<?> packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(PacketToClient<?> packet, ServerPlayerEntity player) {
        channel.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToClient(BiDirectionalPacket<?> packet, ServerPlayerEntity player) {
        channel.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(PacketToServer<?> packet) {
        channel.sendToServer(packet);
    }

    public static void sendToServer(BiDirectionalPacket<?> packet) {
        channel.sendToServer(packet);
    }
}
