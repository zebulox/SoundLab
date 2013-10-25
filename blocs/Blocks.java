package zebulox.SoundLab.blocs;

import net.minecraft.block.Block;
import zebulox.SoundLab.lib.config.Ids;
import zebulox.SoundLab.lib.config.Names;
import zebulox.SoundLab.tileentity.TileEntityZ2080;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


public class Blocks {
	public static Block Z2080block;

	public static void init() {
		Z2080block = new Z2080(Ids.Z2080_actual);
		GameRegistry.registerBlock(Z2080block, Names.Z2080_name);
	}

	public static void addNames() {
		LanguageRegistry.addName(Z2080block, Names.Z2080_name);
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityZ2080.class, "Z2080SoundLab");
	}
}