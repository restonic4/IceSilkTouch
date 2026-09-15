package com.restonic4.ice_silk_touch.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.IceBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin extends Block {
	protected IceBlockMixin(int id, int sprite, Material material) {
		super(id, sprite, material);
	}

	@Inject(
		method = "afterMinedByPlayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;getMaterial(III)Lnet/minecraft/block/material/Material;"
		),
		cancellable = true
	)
	private void ice_silk_touch$prevent_water(World world, PlayerEntity player, int x, int y, int z, int metadata, CallbackInfo ci) {
		if (EnchantmentHelper.hasSilkTouch(player.inventory)) {
			ci.cancel();
		}
	}

	@Inject(method = "getSilkTouchDrop", at = @At("RETURN"), cancellable = true)
	private void ice_silk_touch$drop(int metadata, CallbackInfoReturnable<ItemStack> cir) {
		ItemStack originalDrop = super.getSilkTouchDrop(metadata);
		cir.setReturnValue(originalDrop);
	}
}
