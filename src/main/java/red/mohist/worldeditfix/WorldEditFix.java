package red.mohist.worldeditfix;

import com.sk89q.worldedit.blocks.BlockType;
import java.util.Map;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 * @author Mgazul
 * @date 2020/5/29 19:07
 */
public class WorldEditFix {

    public static boolean debug = false;

    public static void init() {
        Map<String, Material> BLOCK_BY_NAME = ObfuscationReflectionHelper.getPrivateValue(Material.class, null, "BLOCK_BY_NAME");
        for (Map.Entry<String, Material> map : BLOCK_BY_NAME.entrySet()) {
            String name = map.getKey();
            Material material = map.getValue();
            int newid = material.getId();
            BlockType bt = EnumHelper.addEnum(BlockType.class, name, new Class[] { Integer.TYPE, String.class, String.class }, newid, name, name.toLowerCase());
            BlockType.ids.put(newid, bt);
            BlockType.lookup.put(name.toLowerCase(), bt);
        }
        if (debug) {
            for (BlockType blockType : BlockType.values()) {
                Bukkit.getLogger().info(blockType.toString() + " : " + blockType.getID());
            }
        }
    }

}
