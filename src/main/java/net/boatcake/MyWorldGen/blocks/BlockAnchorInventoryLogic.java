package net.boatcake.MyWorldGen.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockAnchorInventoryLogic extends BlockAnchorLogic {

	public BlockAnchorInventoryLogic(String blockName) {
		super(blockName);
	}

	@Override
	public boolean matches(IBlockState myState, TileEntity myTileEntity,
			World world, BlockPos pos) {
		return ((TileEntityAnchorInventory) myTileEntity).matches(world
				.getBlockState(pos));
	}

}
