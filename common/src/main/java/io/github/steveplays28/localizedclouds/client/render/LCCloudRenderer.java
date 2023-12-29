package io.github.steveplays28.localizedclouds.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.localizedclouds.client.ClientCloudTicker;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class LCCloudRenderer {
	private static final float CLOUD_SIZE = 50f;

	public static void render(MatrixStack matrices, ClientWorld world, Entity camera) {
		ClientCloudTicker.getClouds().forEach(cloud -> {
			var cloudBlockPos = cloud.getBlockPos();
			var cloudPos = new Vector3f(cloudBlockPos.getX(), cloudBlockPos.getY(), cloudBlockPos.getZ());
			// Transform cloud position
			var cloudTransformedPos = cloudPos.sub(camera.getPos().toVector3f());

			// Translate matrices to world space
			matrices.translate(cloudTransformedPos.x(), cloudTransformedPos.y(), cloudTransformedPos.z());

			var tessellator = Tessellator.getInstance();
			buildCloudBuffer(matrices, tessellator, cloudPos);

			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			// Disable culling to allow viewing the clouds from above, similar to Minecraft's vanilla fast clouds
			RenderSystem.disableCull();
			tessellator.draw();
			RenderSystem.enableCull();
		});
	}

	private static void buildCloudBuffer(@NotNull MatrixStack matrices, @NotNull Tessellator tessellator, @NotNull Vector3f cloudPos) {
		var positionMatrix = matrices.peek().getPositionMatrix();
		var bufferBuilder = tessellator.getBuffer();

		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

		// Draw a cube, from https://stackoverflow.com/q/34118650/14508289
		// Top face (y = 1.0f)
		// Define vertices in counter-clockwise (CCW) order with normal pointing out
		// Green
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, -1.0f).color(0f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, -1.0f).color(0f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, 1.0f).color(0f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, 1.0f).color(0f, 1f, 0f, 1f).next();

		// Bottom face (y = -1.0f)
		// Orange
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, 1.0f).color(1f, 0.5f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, 1.0f).color(1f, 0.5f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, -1.0f).color(1f, 0.5f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, -1.0f).color(1f, 0.5f, 0f, 1f).next();

		// Front face  (z = 1.0f)
		// Reds
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, 1.0f).color(1f, 0f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, 1.0f).color(1f, 0f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, 1.0f).color(1f, 0f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, 1.0f).color(1f, 0f, 0f, 1f).next();

		// Back face (z = -1.0f)
		// Yellow
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, -1.0f).color(1f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, -1.0f).color(1f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, -1.0f).color(1f, 1f, 0f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, -1.0f).color(1f, 1f, 0f, 1f).next();

		// Left face (x = -1.0f)
		// Blue
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, 1.0f).color(0f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, -1.0f).color(0f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, -1.0f).color(0f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, 1.0f).color(0f, 0f, 1f, 1f).next();

		// Right face (x = 1.0f)
		// Magenta
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, -1.0f).color(1f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, 1.0f).color(1f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, 1.0f).color(1f, 0f, 1f, 1f).next();
		bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, -1.0f).color(1f, 0f, 1f, 1f).next();

		// Build a quad (counter-clockwise, in order: top-left, bottom-left, bottom-right, top-right)
//		bufferBuilder.vertex(positionMatrix, cloudPos.x(), cloudPos.y(), cloudPos.z()).color(1f, 1f, 1f, 1f).next();
//		bufferBuilder.vertex(positionMatrix, cloudPos.x() + CLOUD_SIZE, cloudPos.y(), cloudPos.z()).color(1f, 1f, 1f, 1f).next();
//		bufferBuilder.vertex(positionMatrix, cloudPos.x() + CLOUD_SIZE, cloudPos.y(), cloudPos.z() + CLOUD_SIZE).color(
//				1f, 1f, 1f, 1f).next();
//		bufferBuilder.vertex(positionMatrix, cloudPos.x(), cloudPos.y(), cloudPos.z() + CLOUD_SIZE).color(1f, 1f, 1f, 1f).next();
//
//		matrices.push();
//		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
//		bufferBuilder.vertex(positionMatrix, cloudPos.x() + CLOUD_SIZE, cloudPos.y(), cloudPos.z() + CLOUD_SIZE).color(
//				1f, 1f, 1f, 1f).next();
//		bufferBuilder.vertex(positionMatrix, cloudPos.x(), cloudPos.y(), cloudPos.z() + CLOUD_SIZE).color(1f, 1f, 1f, 1f).next();
	}
}
