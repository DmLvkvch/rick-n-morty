package com.example.ricknmortyapp.render

import android.opengl.GLES20.GL_DYNAMIC_DRAW
import android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER
import android.opengl.GLES20.glBindBuffer
import android.opengl.GLES20.glBufferData
import android.opengl.GLES20.glGenBuffers
import java.nio.IntBuffer

class IndexBuffer (array: IntBuffer)
{
    private val rendererId: Int
    private val elementCount: Int

    init {
        val rendererID: IntBuffer = IntBuffer.allocate(1);
        glGenBuffers(1, rendererID)
        this.rendererId = rendererID[0]
        this.elementCount = array.array().size

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, 4 * array.array().size, array, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public fun bind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererId);
    }

    public fun unbind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public fun getElementCount()
    {
        rendererId
    }
}