package net.boatcake.MyWorldGen.blocks;

import net.boatcake.MyWorldGen.MyWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnchorInventory extends BlockContainer implements BlockAnchorBase, ITileEntityProvider {

	public BlockAnchorInventory(Material par2Material) {
		super(par2Material);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setStepSound(Block.soundTypeStone);
		setBlockName("anchorInventory");
		setCreativeTab(MyWorldGen.creativeTab);
		setBlockTextureName("MyWorldGen:anchorInventory");
	}

	@Override
	public boolean matches(int myMeta, TileEntity myTileEntity, World world, int x, int y, int z) {
		return ((TileEntityAnchorInventory)myTileEntity).matches(world.getBlock(x, y, z));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAnchorInventory();
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(getTextureName());
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		// code to open gui explained later
		player.openGui(MyWorldGen.instance, 2, world, x, y, z);
		return true;
	}
}
