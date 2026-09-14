package net.libz.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.libz.network.packet.ConfigPacket;
import net.libz.network.packet.MousePacket;
import net.libz.util.ConfigHelper;
import net.minecraft.server.level.ServerPlayer;

public class LibzServerPacket {

    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(ConfigPacket.PACKET_ID, ConfigPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(MousePacket.PACKET_ID, MousePacket.PACKET_CODEC);
    }

    public static void writeS2CConfigPacket(ServerPlayer serverPlayerEntity, String configName, boolean gson) {
        byte[] bytes = ConfigHelper.getConfigBytes(configName, gson, true);
        if (bytes != null) {
            ServerPlayNetworking.send(serverPlayerEntity, new ConfigPacket(configName, gson, bytes));
        }
    }

    public static void writeS2CMousePositionPacket(ServerPlayer serverPlayerEntity, int mouseX, int mouseY) {
        ServerPlayNetworking.send(serverPlayerEntity, new MousePacket(mouseX, mouseY));
    }

}
