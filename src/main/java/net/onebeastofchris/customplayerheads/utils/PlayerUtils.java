package net.onebeastofchris.customplayerheads.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.onebeastofchris.customplayerheads.CustomPlayerHeads;

import java.util.HashMap;
import java.util.UUID;

public class PlayerUtils {

    public static HashMap<String, String> javaUUIDmap = new HashMap<>();
    public static HashMap<String, String> bedrockXUIDmap = new HashMap<>();
    private static final String GEYSER_XUID_API = "https://api.geysermc.org/v2/xbox/xuid/";
    private static final String GEYSER_SKIN_API = "https://api.geysermc.org/v2/skin/";
    private static final String MOJANG_SESSIONSERVER = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final String MOJANG_UUID = "https://api.mojang.com/users/profiles/minecraft/";

    public static boolean isBedrockPlayer(PlayerEntity player) {
        return FloodgateUtil.isBedrockPlayer(player.getUuid(), player.getNameForScoreboard());
    }

    public static String xuidLookup(String playername, PlayerEntity self) {
        String lowerCasePlayerName = playername.toLowerCase().replace(FloodgateUtil.FloodgatePrefix(), "");

        if (bedrockXUIDmap.get(lowerCasePlayerName) != null) {
            CustomPlayerHeads.debugLog("Bedrock player" + playername + " is cached, using their cached XUID: " + bedrockXUIDmap.get(playername));
            return bedrockXUIDmap.get(lowerCasePlayerName);
        }

        var xuidJson = CustomPlayerHeads.getWebUtil().webRequest(GEYSER_XUID_API + lowerCasePlayerName);
        try {
            return xuidJson.get("xuid").getAsString();
        } catch (Exception e) {
            switch (xuidJson.get("message").getAsString()) {
                case "gamertag is empty or longer than 16 chars" -> {
                    self.sendMessage(Text.literal("无效的基岩版玩家名称。").formatted(Formatting.RED), false);
                    return null;
                }
                case "Unable to find user in our cache. Please try specifying their Floodgate UUID instead" -> {
                    self.sendMessage(Text.literal("该基岩版玩家不存在，或尚未加入过 Geyser 服务器。").formatted(Formatting.RED), false);
                    return null;
                }
                default -> {
                    CustomPlayerHeads.debugLog("GetXuid: " + e.getMessage());
                    self.sendMessage(Text.literal("获取该基岩版玩家的 XUID 时发生错误。").formatted(Formatting.RED), false);
                    return null;
                }
            }
        }
    }

    public static PropertyMap getTextures(String lookupID, boolean isBedrock) throws IllegalArgumentException {
        String value, signature;
        if (isBedrock) {
            JsonObject jsonObject = CustomPlayerHeads.getWebUtil().webRequest(GEYSER_SKIN_API + lookupID);
            try {
                value = jsonObject.get("value").getAsString();
                signature = jsonObject.get("signature").getAsString();
            } catch (Exception e) {
                throw new IllegalArgumentException("texture for " + lookupID + " not found");
            }
        } else {
            JsonObject jsonObject = CustomPlayerHeads.getWebUtil().webRequest(MOJANG_SESSIONSERVER + lookupID);
            try {
                JsonObject object = jsonObject.get("properties").getAsJsonArray().get(0).getAsJsonObject();
                value = object.get("value").getAsString();
                signature = object.get("signature").getAsString();
            } catch (Exception e) {
                throw new IllegalArgumentException("texture for " + lookupID + " not found");
            }
        }

        PropertyMap propertyMap = new PropertyMap();
        propertyMap.put("textures", new Property("textures", value, signature));
        return propertyMap;
    }

    public static String getJavaUUID(String playername) {
        String lowerCasePlayerName = playername.toLowerCase();

        // cache the UUIDs, so we don't have to make a web request every time
        if (javaUUIDmap.get(lowerCasePlayerName) != null) {
            return javaUUIDmap.get(lowerCasePlayerName);
        }
        JsonObject getJson = CustomPlayerHeads.getWebUtil().webRequest(MOJANG_UUID + lowerCasePlayerName);

        JsonElement uuid = getJson.get("id");
        if (uuid == null) {
            return null;
        }
        return uuid.getAsString();
    }

    public static UUID fromUuidString(String target) {
        return UUID.fromString(target.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"
        ));
    }

    public static UUID uuidFromXuid(String target) {
        long xuid = Long.parseLong(target);
        return new UUID(0, xuid);
    }
}