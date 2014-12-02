package net.boatcake.MyWorldGen.blocks;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockPlacementLogic {
	private static Map<String, BlockPlacementLogic> blockNameToLogic = new HashMap<String, BlockPlacementLogic>();

	public static BlockPlacementLogic get(String blockName) {
		return blockNameToLogic.get(blockName);
	}

	public static boolean placementLogicExists(String blockName) {
		return blockNameToLogic.containsKey(blockName);
	}

	public BlockPlacementLogic(String blockName) {
		blockNameToLogic.put(blockName, this);
	}

	public abstract void affectWorld(IBlockState myState,
			TileEntity myTileEntity, World world, BlockPos pos);
}
