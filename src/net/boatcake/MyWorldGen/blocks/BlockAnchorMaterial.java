package net.boatcake.MyWorldGen.blocks;

import java.util.List;

import net.boatcake.MyWorldGen.MyWorldGen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnchorMaterial extends Block implements BlockAnchorBase {
	public enum AnchorType {
		AIR(1, "Air", null), DIRT(5, "Dirt", Material.ground), GROUND(0,
				"Ground", null), LAVA(4, "Lava", Material.lava), LEAVES(7,
				"Leaves", Material.leaves), SAND(8, "Sand", Material.sand), STONE(
				2, "Stone", Material.rock), WATER(3, "Water", Material.water), WOOD(
				6, "Wood", Material.wood);
		private static final AnchorType[] v = values();
		public static final int size = v.length;

		public static AnchorType get(int id) {
			if (id > AnchorType.class.getEnumConstants().length) {
				return null;
			}
			return v[id];
		}

		public final int id;
		public final Material material;

		public final String name;

		private AnchorType(int id, String name, Material mat) {
			this.id = id;
			this.name = name;
			this.material = mat;
		}
	}

	public IIcon[] icons;

	public BlockAnchorMaterial(Material par2Material) {
		super(par2Material);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setStepSound(Block.soundTypeStone);
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs,
			List subBlockList) {
		for (int i = 0; i < AnchorType.size; i++) {
			subBlockList.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[AnchorType.size];
		for (int i = 0; i < icons.length; i++) {
			this.icons[i] = iconRegister.registerIcon(this.getTextureName()
					+ AnchorType.get(i).name);
		}
	}
}
