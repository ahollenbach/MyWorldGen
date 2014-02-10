package net.boatcake.MyWorldGen.items;

import net.boatcake.MyWorldGen.MyWorldGen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWandLoad extends Item {
	public ItemWandLoad(int id) {
		super(id);
		setMaxStackSize(0);
		setUnlocalizedName("wandLoad");
		setCreativeTab(MyWorldGen.creativeTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		this.itemIcon = ir.registerIcon("MyWorldGen:"
				+ (this.getUnlocalizedName().substring(5)));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			int blockX, int blockY, int blockZ, int side, float hitX,
			float hitY, float hitZ) {
		if (world.isRemote) {
			player.openGui(MyWorldGen.instance, 1, world, blockX
					+ Facing.offsetsXForSide[side], blockY
					+ Facing.offsetsYForSide[side], blockZ
					+ Facing.offsetsZForSide[side]);
		}
		return true;
	}
}
