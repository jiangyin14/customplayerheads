![modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_64h.png)
<img src="https://i.imgur.com/iaETp3c.png" alt="" width="200" >
![fabric-api](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/requires/fabric-api_64h.png)

## CustomPlayerHeads
一个小型 Fabric 模组，用于在玩家死亡时掉落玩家头颅，或通过命令获取头颅。兼容通过 Geyser 加入的基岩版玩家，以及其他在游戏中更换皮肤的模组（如 FabricTailor）。

## 支持版本：1.20.6、1.21 Fabric
可在 Modrinth 上获取：
#### https://modrinth.com/mod/customplayerheads

为什么使用模组而不是数据包？因为模组可以访问 GameProfile 中的皮肤数据，而数据包无法做到这一点。

需要 FabricAPI！Floodgate 是可选的，用于支持基岩版玩家，但如果你使用的 Floodgate 前缀不是"."，则需要安装 Floodgate。
由于 https://bugs.mojang.com/browse/MC-174496 的问题，建议使用类似 https://modrinth.com/mod/headfix 的模组来在破坏头颅时保留自定义头颅名称。

### 当前功能：
- 玩家死亡时掉落玩家头颅
- 会显示（并持续显示）死亡时使用的皮肤！
- 如果被玩家击杀：会在物品描述中添加"被 [玩家] 击杀"的信息，内容完全可自定义！
- 可选的 "/getskull" 命令——可以获取 Java 版和基岩版玩家的头颅。对于基岩版玩家，只要他们的皮肤已上传到 GeyserAPI（加入任何 Geyser+Floodgate 服务器时会自动完成）即可使用
- 可选择是否在头颅名称中显示 Floodgate 前缀

### 待添加功能：
目前没有进一步的计划！欢迎提出建议 ^^
