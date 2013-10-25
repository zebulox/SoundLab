package zebulox.SoundLab;

import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import zebulox.SoundLab.blocs.Blocks;
import zebulox.SoundLab.client.GuiHandler;
import zebulox.SoundLab.items.Items;
import zebulox.SoundLab.lib.LogHelper;
import zebulox.SoundLab.lib.ModInfo;
import zebulox.SoundLab.lib.Recipes;
import zebulox.SoundLab.lib.SoundLabCreativeTab;
import zebulox.SoundLab.lib.config.ConfigHandler;
import zebulox.SoundLab.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;//pour le @mod
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;//pour le @SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;// pour le @NetworkMod
import cpw.mods.fml.common.registry.LanguageRegistry;



@Mod( modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION )//définis le package comme un mod

@NetworkMod( channels = {ModInfo.ID}, clientSideRequired = true, serverSideRequired = true )//autorise le SMP

public class SoundLab {
	
	//un peu sombre nécéssaire pour pouvoir déployer un GUI personalisé 
	@Instance(ModInfo.ID)
	public static SoundLab instance;


	
	@SidedProxy( clientSide = ModInfo.PROXY_LOCATION + ".ClientProxy", serverSide = ModInfo.PROXY_LOCATION + ".CommonProxy")//informe le mod ou trouver les fichiers de proxy
	public static CommonProxy proxy;
	
	public static CreativeTabs soundlabcreativetab = new SoundLabCreativeTab(CreativeTabs.getNextID(), ModInfo.NAME);
	
	@EventHandler
	//tout ce qui doit etre chargé avant...
	public static void preInit(FMLPreInitializationEvent event) {
		
		//pour afficher les msg dans la console
		LogHelper.init();

		
		//fonction qui règle les problemes de collisions d'ID entre mods
		LogHelper.log(Level.INFO, "Preparing items ID's");
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		LogHelper.log(Level.INFO, "items ID's loaded");

		
		LogHelper.log(Level.INFO, "Preparing blocs renderers");
		//appel des fonctions d'init des ressources des classes proxy
		proxy.initRenderers();
		LogHelper.log(Level.INFO, "blocs renderers loading finish");
		LogHelper.log(Level.INFO, "Preparing sounds init");
		proxy.initSounds();
		LogHelper.log(Level.INFO, "sounds init done");
		
		new GuiHandler();

		LogHelper.log(Level.INFO, "initalising blocs");
		Blocks.init();
		LogHelper.log(Level.INFO, "blocs loaded");
		LogHelper.log(Level.INFO, "initalising items");
		Items.init();
		LogHelper.log(Level.INFO, "items loaded");
		LogHelper.log(Level.INFO, "initalising recipes");
		Recipes.init();
		LogHelper.log(Level.INFO, "recipes loaded");

	}
		
	@EventHandler
	//tout ce qui doit etre chargé pendant...
	public static void init(FMLInitializationEvent event) {
		
		LogHelper.log(Level.INFO, "creating creativetab");
		LanguageRegistry.instance().addStringLocalization("itemGroup." + ModInfo.NAME, "en_US", ModInfo.NAME);
		
		LogHelper.log(Level.INFO, "finally adding items's names");
		Items.addNames();
		LogHelper.log(Level.INFO, "items's names added");

		LogHelper.log(Level.INFO, "finally adding blocks's names");
		Blocks.addNames();
		LogHelper.log(Level.INFO, "blocks's names added");
	}

	@EventHandler
	//tout ce qui doit etre chargé apres...
	public static void postInit(FMLPostInitializationEvent event) {

	}
	
	
}
