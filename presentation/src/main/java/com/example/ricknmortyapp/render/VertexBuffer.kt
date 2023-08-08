package com.example.ricknmortyapp.render

import android.opengl.GLES20
import java.nio.FloatBuffer
import java.nio.IntBuffer

class VertexBuffer (array: FloatBuffer) {
    private val rendererId: Int
    private val elementCount: Int

    init {
        val rendererID: IntBuffer = IntBuffer.allocate(1);
        GLES20.glGenBuffers(1, rendererID)
        this.rendererId = rendererID[0]
        this.elementCount = array.array().size

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, rendererId);
        GLES20.glBufferData(
            GLES20.GL_ARRAY_BUFFER,
            4 * array.array().size,
            array,
            GLES20.GL_DYNAMIC_DRAW
        );
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public fun bind() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, rendererId);
    }

    public fun unbind() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public fun getElementCount() {
        rendererId
    }
}