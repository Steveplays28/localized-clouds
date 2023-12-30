package io.github.steveplays28.localizedclouds.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.steveplays28.localizedclouds.client.ClientCloudTicker;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

public class LCCloudRenderer {
	private static final float CLOUD_SIZE = 50f;

	public static void render(Camera camera) {
		ClientCloudTicker.getClouds().forEach(cloud -> {
			var cloudBlockPos = cloud.getBlockPos();
			Vec3d targetPosition = new Vec3d(cloudBlockPos.getX(), cloudBlockPos.getY(), cloudBlockPos.getZ());
			Vec3d transformedPosition = targetPosition.subtract(camera.getPos());

			MatrixStack matrixStack = new MatrixStack();
			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
			matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

//			Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
			Tessellator tessellator = Tessellator.getInstance();
//			BufferBuilder buffer = tessellator.getBuffer();

			buildCloudBuffer(matrixStack, tessellator);

//			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
//			buffer.vertex(positionMatrix, 0, 1, 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
//			buffer.vertex(positionMatrix, 0, 0, 0).color(1f, 0f, 0f, 1f).texture(0f, 1f).next();
//			buffer.vertex(positionMatrix, 1, 0, 0).color(0f, 1f, 0f, 1f).texture(1f, 1f).next();
//			buffer.vertex(positionMatrix, 1, 1, 0).color(0f, 0f, 1f, 1f).texture(1f, 0f).next();

			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
//			RenderSystem.setShaderTexture(0, new Identifier("examplemod", "icon.png"));
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			RenderSystem.disableCull();
//			RenderSystem.depthFunc(GL11.GL_ALWAYS);

			tessellator.draw();

//			RenderSystem.depthFunc(GL11.GL_LEQUAL);
			RenderSystem.enableCull();

//			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
//			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//			// Disable culling to allow viewing the clouds from above, similar to Minecraft's vanilla fast clouds
//			RenderSystem.disableCull();
//			RenderSystem.disableDepthTest();
//			tessellator.draw();
//			RenderSystem.enableDepthTest();
//			RenderSystem.enableCull();
		});
	}

	private static void buildCloudBuffer(@NotNull MatrixStack matrixStack, @NotNull Tessellator tessellator) {
		var positionMatrix = matrixStack.peek().getPositionMatrix();
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
