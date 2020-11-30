package com.mohistmc.worldeditfix;

import com.sk89q.worldedit.blocks.BlockType;
import net.minecraftforge.common.util.EnumHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 * @author Mgazul
 * @date 2020/5/29 19:07
 */
public class WorldEditFix {

    public static boolean debug = false;

    public static void init() {
        for (Material material : Material.values()) {
            if (material.isForgeBlock()) {
                String name = material.name();
                int newid = material.getId();
                BlockType bt = EnumHelper.addEnum(BlockType.class, name, new Class[] { Integer.TYPE, String.class, String.class }, newid, name, name.toLowerCase());
                BlockType.ids.put(newid, bt);
                BlockType.lookup.put(name.toLowerCase(), bt);
            }
        }
        if (debug) {
            for (BlockType blockType : BlockType.values()) {
                Bukkit.getLogger().info(blockType.toString() + " : " + blockType.getID());
            }
        }
    }
}
