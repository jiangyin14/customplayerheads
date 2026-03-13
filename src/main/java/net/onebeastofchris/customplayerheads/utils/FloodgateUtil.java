package net.onebeastofchris.customplayerheads.utils;

import net.fabricmc.loader.api.FabricLoader;
import net.onebeastofchris.customplayerheads.CustomPlayerHeads;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.UUID;

public class FloodgateUtil {

    static boolean warned = false;

    public static boolean isFloodgatePresent() {
        boolean isFloodgatePresent = FabricLoader.getInstance().isModLoaded("floodgate");
        if (!isFloodgatePresent && !warned) {
            CustomPlayerHeads.getLogger().info("未检测到 Floodgate，但建议安装以使本模组对基岩版玩家正常工作。");
            warned = true;
        }
        return isFloodgatePresent;
    }

    public static boolean isBedrockPlayer(UUID uuid, String playerName) {
        //linked players should be treated as Java players
        if (!isFloodgatePresent()) {
            return fallback(uuid, playerName);
        } else if (FloodgateApi.getInstance() == null) {
            CustomPlayerHeads.getLogger().info("Floodgate 似乎已安装，但 CustomPlayerHeads 无法访问它。请在 GitHub 页面上报告此问题！");
            return fallback(uuid, playerName);
        }

        return FloodgateApi.getInstance().isFloodgatePlayer(uuid) && !isLinked(uuid);
    }

    public static String FloodgatePrefix() {
        if (!isFloodgatePresent()) {
            return ".";
        } else if (FloodgateApi.getInstance() == null) {
            CustomPlayerHeads.getLogger().info("Floodgate 似乎已安装，但 CustomPlayerHeads 无法访问它。请在 GitHub 页面上报告此问题！");
            return ".";
        } else {
            return FloodgateApi.getInstance().getPlayerPrefix();
        }
    }

    public static boolean isLinked(UUID uuid) {
        FloodgatePlayer floodgatePlayer = FloodgateApi.getInstance().getPlayer(uuid);
        return floodgatePlayer.isLinked();
    }

    private static boolean fallback(UUID uuid, String playerName) {
        return uuid.version() == 0 || playerName.startsWith(FloodgatePrefix());
    }
}