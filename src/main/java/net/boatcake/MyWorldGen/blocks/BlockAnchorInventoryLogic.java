package net.boatcake.MyWorldGen.blocks;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockAnchorInventoryLogic extends BlockAnchorLogic {

	public BlockAnchorInventoryLogic(String blockName) {
		super(blockName);
	}

	@Override
	public boolean matches(int myMeta, TileEntity myTileEntity, World world,
			int x, int y, int z) {
		return ((TileEntityAnchorInventory) myTileEntity).matches(world
				.getBlock(x, y, z));
	}

	@Override
	public Integer[] getQuickMatchingBlockInChunk(int myMeta,
			TileEntity myTileEntity, Chunk chunk, Random rand) {
		return null;
	}

}
