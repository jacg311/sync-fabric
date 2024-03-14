package dev.kir.sync.client.render;

import dev.kir.sync.block.entity.SyncBlockEntities;
import dev.kir.sync.client.render.block.entity.ShellConstructorBlockEntityRenderer;
import dev.kir.sync.client.render.block.entity.ShellStorageBlockEntityRenderer;
import dev.kir.sync.client.render.block.entity.TreadmillBlockEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registry;

@Environment(EnvType.CLIENT)
public final class SyncRenderers {
    public static void initClient() {
        register(ShellStorageBlockEntityRenderer::new, SyncBlockEntities.SHELL_STORAGE);
        register(ShellConstructorBlockEntityRenderer::new, SyncBlockEntities.SHELL_CONSTRUCTOR);
        register(TreadmillBlockEntityRenderer::new, SyncBlockEntities.TREADMILL);
    }

    private static <E extends BlockEntity> void register(BlockEntityRendererFactory<? super E> rendererFactory, BlockEntityType<E> blockEntityType) {
        BlockEntityRendererRegistry.register(blockEntityType, rendererFactory);

        Identifier id = Registries.BLOCK_ENTITY_TYPE.getId(blockEntityType);
        Block block = Registries.BLOCK.get(id);
        Item item = Registries.ITEM.get(id);
        if (Registries.BLOCK.getId(block).equals(Registries.BLOCK.getDefaultId()) || Registries.ITEM.getId(item).equals(Registries.ITEM.getDefaultId())) {
            return;
        }

        BlockEntity renderEntity = blockEntityType.instantiate(BlockPos.ORIGIN, block.getDefaultState());
        BuiltinItemRendererRegistry.INSTANCE.register(item, (itemStack, transform, stack, source, light, overlay) ->
            MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(renderEntity, stack, source, light, overlay)
        );
    }
}
