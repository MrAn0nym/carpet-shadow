package com.carpet_shadow.mixins.fragility;

import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.block.DropperBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DropperBlock.class)
public abstract class DropperBlockMixin {

    @Redirect(method = "dispense", at=@At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;",ordinal = 0),
    slice = @Slice(from=@At(value = "INVOKE",target = "Lnet/minecraft/block/entity/HopperBlockEntity;transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;")))
    public ItemStack fix_dispense1(ItemStack instance){
        if(CarpetShadowSettings.shadowItemFragilityFixes && ((ShadowItem)(Object)instance).getShadowId()!=null){
            return instance;
        }
        return instance.copy();
    }

    @Redirect(method = "dispense", at=@At(value = "INVOKE",target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;",ordinal = 1),
    slice = @Slice(from=@At(value = "INVOKE",target = "Lnet/minecraft/block/entity/HopperBlockEntity;transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;")))
    public ItemStack fix_dispense2(ItemStack instance){
        if(CarpetShadowSettings.shadowItemFragilityFixes && ((ShadowItem)(Object)instance).getShadowId()!=null){
            instance.increment(1);
            return instance;
        }
        return instance.copy();
    }

}
