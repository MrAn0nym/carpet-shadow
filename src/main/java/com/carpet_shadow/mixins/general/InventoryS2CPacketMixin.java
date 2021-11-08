package com.carpet_shadow.mixins.general;

import com.carpet_shadow.CarpetShadow;
import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryS2CPacket.class)
public abstract class InventoryS2CPacketMixin {


    @Redirect(method = "<init>(IILnet/minecraft/util/collection/DefaultedList;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"))
    public ItemStack copy_redirect(ItemStack instance){
        if(CarpetShadowSettings.shadowItemFragilityFixes && ((ShadowItem)(Object)instance).getShadowId()!=null){
            ItemStack reference = CarpetShadow.shadowMap.get(((ShadowItem)(Object)instance).getShadowId()).get();
            if (reference!=null)
                return reference;
        }
        if(CarpetShadowSettings.shadowItemTooltip){
            return ShadowItem.copy_redirect(instance);
        }
        return instance.copy();
    }

}
