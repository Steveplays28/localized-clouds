package io.github.steveplays28.localizedclouds.mixin.client;

import io.github.steveplays28.localizedclouds.client.render.LCCloudRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow
	@Nullable
	private ClientWorld world;

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FDDD)V", at = @At(value = "HEAD"), cancellable = true)
	private void localizedclouds$renderCloudsInject(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double cameraX, double cameraY, double cameraZ, @NotNull CallbackInfo ci) {
		ci.cancel();

		var camera = client.getCameraEntity();
		if (camera == null) return;

		LCCloudRenderer.render(new MatrixStack(), world, camera);
	}
}
