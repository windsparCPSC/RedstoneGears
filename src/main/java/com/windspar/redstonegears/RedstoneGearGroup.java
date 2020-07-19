package com.windspar.redstonegears;

import com.windspar.redstonegears.lists.ItemList;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RedstoneGearGroup extends ItemGroup {

	
	public RedstoneGearGroup(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}

	@Override
	@OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
       return new ItemStack(ItemList.redstone_gear);
    }

}
