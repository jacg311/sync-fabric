package dev.kir.sync.util;

import dev.kir.sync.Sync;
import dev.kir.sync.config.SyncConfig;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ItemUtil {
    private static final TagKey<Item> WRENCHES = TagKey.of(RegistryKeys.ITEM, new Identifier("c:wrenches"));

    public static boolean isWrench(ItemStack itemStack) {
        if (itemStack.isIn(WRENCHES)) {
            return true;
        }

        SyncConfig config = Sync.getConfig();
        Identifier wrenchId = config.wrench() == null || config.wrench().isBlank() ? null : Identifier.tryParse(Sync.getConfig().wrench());
        if (wrenchId == null) {
            return false;
        }
        Item wrench = Registries.ITEM.get(wrenchId);
        return wrench != Items.AIR && itemStack.isOf(wrench);
    }

    public static boolean isWrench(ItemConvertible item) {
        return isWrench(new ItemStack(item));
    }


    public static boolean isArmor(ItemStack itemStack) {
        return isArmor(itemStack.getItem());
    }

    public static boolean isArmor(ItemConvertible item) {
        return DispenserBlock.BEHAVIORS.get(item.asItem()) == ArmorItem.DISPENSER_BEHAVIOR;
    }


    public static EquipmentSlot getPreferredEquipmentSlot(ItemStack itemStack) {
        return MobEntity.getPreferredEquipmentSlot(itemStack);
    }

    public static EquipmentSlot getPreferredEquipmentSlot(ItemConvertible item) {
        return getPreferredEquipmentSlot(new ItemStack(item));
    }
}
