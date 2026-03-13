package net.onebeastofchris.customplayerheads.utils;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;


@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public final class CPHConfig {

    @Comment("是否在死亡时掉落头颅？")
    private boolean shouldDropHeadsOnDeath = true;

    @Comment("是否在被玩家击杀掉落的头颅上显示\"被 <玩家> 击杀\"的描述信息。")
    private boolean showLore = true;

    @Comment("非玩家击杀是否也掉落头颅（例如：怪物、爆炸、摔落伤害等）。")
    private boolean dropNonPlayerKillHeads = false;

    @Comment("是否启用 /getskull 命令，允许拥有以下权限等级的玩家获取其他玩家的头颅。")
    private boolean commandEnabled = true;

    @Comment("使用 /getskull 命令所需的权限等级。")
    private int commandPermissionLevel = 2;

    @Comment("Geyser/Floodgate 用户专用：是否在头颅名称中包含 Floodgate 前缀（例如 .steve 而非 steve）。")
    private boolean showFloodgatePrefix = true;

    @Comment("是否启用调试模式。启用后将在控制台输出更多信息。")
    private boolean debug = false;

    @Comment("头颅的描述信息内容是什么？")
    private String lore = "被 %player% 击杀";

    @Comment("头颅的名称应该是什么？")
    private String name = "%player% 的头颅";

    public boolean isShouldDropHeadsOnDeath() {
        return shouldDropHeadsOnDeath;
    }

    public boolean isShowLore() {
        return showLore;
    }

    public boolean isDropNonPlayerKillHeads() {
        return dropNonPlayerKillHeads;
    }

    public boolean isCommandEnabled() {
        return commandEnabled;
    }

    public int getCommandPermissionLevel() {
        return commandPermissionLevel;
    }

    public boolean isShowFloodgatePrefix() {
        return showFloodgatePrefix;
    }

    public String getLore() {
        return lore;
    }

    public boolean isDebug() {
        return debug;
    }


    public String getName() {
        return name;
    }
}