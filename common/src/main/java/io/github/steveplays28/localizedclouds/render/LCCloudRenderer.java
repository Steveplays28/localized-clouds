package io.github.steveplays28.localizedclouds.render;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.localizedclouds.client.ClientCloudTicker;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.Heightmap;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LCCloudRenderer {
	private static final float CLOUD_SIZE = 50f;

	public static void render(MatrixStack matrices, ClientWorld world, Entity camera) {
		ClientCloudTicker.getClouds().forEach(cloud -> {
			var cloudBlockPos = cloud.getBlockPos();
			// Sample heightmap for cloud Y position
			var cloudYPos = world.getTopY(Heightmap.Type.MOTION_BLOCKING, cloudBlockPos.getX(), cloudBlockPos.getZ());
			var cloudPos = new Vector3f(cloudBlockPos.getX(), cloudBlockPos.getY(), cloudBlockPos.getZ());
			// Transform cloud position
			var cloudTransformedPos = cloudPos.sub(camera.getPos().toVector3f());

			// Translate matrices to world space
			matrices.translate(cloudTransformedPos.x(), cloudTransformedPos.y(), cloudTransformedPos.z());

			var positionMatrix = matrices.peek().getPositionMatrix();
			var tessellator = Tessellator.getInstance();
			var bufferBuilder = tessellator.getBuffer();

			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			// Build quad (counter-clockwise, in order: top-left, bottom-left, bottom-right, top-right)
			// TODO: Fix clouds being too high up
			bufferBuilder.vertex(positionMatrix, cloudPos.x(), cloudYPos    , cloudPos.z()).color(1f, 1f, 1f, 1f).next();
			bufferBuilder.vertex(positionMatrix, cloudPos.x() + CLOUD_SIZE, cloudYPos, cloudPos.z()).color(1f, 1f, 1f, 1f).next();
			bufferBuilder.vertex(positionMatrix, cloudPos.x() + CLOUD_SIZE, cloudYPos, cloudPos.z() + CLOUD_SIZE).color(
					1f, 1f, 1f, 1f).next();
			bufferBuilder.vertex(positionMatrix, cloudPos.x(), cloudYPos, cloudPos.z() + CLOUD_SIZE).color(1f, 1f, 1f, 1f).next();

			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			// Disable culling to allow viewing the clouds from above, similar to Minecraft's vanilla fast clouds
			RenderSystem.disableCull();
			tessellator.draw();
			RenderSystem.enableCull();
		});
	}
}
