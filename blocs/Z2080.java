package zebulox.SoundLab.blocs;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import zebulox.SoundLab.SoundLab;
import zebulox.SoundLab.lib.ModInfo;
import zebulox.SoundLab.lib.config.Names;
import zebulox.SoundLab.tileentity.TileEntityZ2080;

public class Z2080 extends BlockContainer {

	private boolean powered;
	
	public Z2080(int id) {
		
		super(id, Material.rock);
		this.setUnlocalizedName(Names.Z2080_unlocalizedName);
		this.setCreativeTab(SoundLab.soundlabcreativetab);
		
		this.setHardness(2F);
		this.setResistance(15F);
		
		this.setStepSound(Block.soundStoneFootstep);
		this.setLightValue(0.5F);//laght radius du bloc torche == 1.0F

		}
	
	
	//en fait un Tile nécéssaire pour avoir un inventaire
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityZ2080();
	}
	
	
	//definition du comportement a l'activation 
	@Override
	//
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		//We want the block to be activated regardless of whether we are on the client or server-side, so we need to return true outside of the if function:
		if(!world.isRemote) {
			// if we are on the server, open a specific GUI
			FMLNetworkHandler.openGui(player, SoundLab.instance, 0, world, x, y, z);
		}
		return true;
	}
	
	
	/*
	 * TEST ALIMENTATON DU BLOC ET CHANGEMENT D'ETAT
	 * @see net.minecraft.block.Block#onNeighborBlockChange(net.minecraft.world.World, int, int, int, int)
	 */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            }
            else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
            {
                par1World.setBlock(par2, par3, par4, Block.redstoneLampActive.blockID, 0, 2);
            }
        }
    }
	
	
		@SideOnly(Side.CLIENT)
		public static Icon topIcon;//définir la texture du haut
		@SideOnly(Side.CLIENT)
		public static Icon bottomIcon;//définir la texture du dessous nécéssaire ???
		@SideOnly(Side.CLIENT)
		public static Icon sideIcon;//définir la texture des cotées

	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		topIcon = icon.registerIcon(ModInfo.ID.toLowerCase() + ":" + Names.Z2080_unlocalizedName + "_top");
		bottomIcon = icon.registerIcon(ModInfo.ID.toLowerCase() + ":" + Names.Z2080_unlocalizedName + "_side");
		sideIcon = icon.registerIcon(ModInfo.ID.toLowerCase() + ":" + Names.Z2080_unlocalizedName + "_side");
		}

	
	/* DEFINIS LES COTES DU BLOC POUR LES TEXTURES DIFFERENTES */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if(side == 0) {
			return bottomIcon;
		} else if(side == 1) {
			return topIcon;
		} else {
			return sideIcon;
		}
	}

}
