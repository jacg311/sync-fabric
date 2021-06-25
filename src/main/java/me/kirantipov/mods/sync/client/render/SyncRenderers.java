package me.kirantipov.mods.sync.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public final class SyncRenderers {
    public static void initClient() {
    }

    private static <E extends BlockEntity> void register(BlockEntityRendererFactory<? super E> rendererFactory, BlockEntityType<E> blockEntityType) {
        BlockEntityRendererRegistry.INSTANCE.register(blockEntityType, rendererFactory);

        Identifier id = Registry.BLOCK_ENTITY_TYPE.getId(blockEntityType);
        Block block = Registry.BLOCK.get(id);
        Item item = Registry.ITEM.get(id);
        if (Registry.BLOCK.getId(block).equals(Registry.BLOCK.getDefaultId()) || Registry.ITEM.getId(item).equals(Registry.ITEM.getDefaultId())) {
            return;
        }

        BlockEntity renderEntity = blockEntityType.instantiate(BlockPos.ORIGIN, block.getDefaultState());
        BuiltinItemRendererRegistry.INSTANCE.register(item, (itemStack, transform, stack, source, light, overlay) ->
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(renderEntity, stack, source, light, overlay)
        );
    }
}