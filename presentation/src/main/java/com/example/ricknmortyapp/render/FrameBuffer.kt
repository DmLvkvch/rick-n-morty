package com.example.ricknmortyapp.render

import android.opengl.GLES20
import android.opengl.GLES20.GL_COLOR_ATTACHMENT0
import android.opengl.GLES20.GL_FRAMEBUFFER
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.glFramebufferTexture2D
import java.nio.IntBuffer

class FrameBuffer(texture0: Texture) {
    private val texture0: Texture
    private val fboId: Int

    init {
        this.texture0 = texture0;
        val intBuffer = IntBuffer.allocate(1)
        GLES20.glGenFramebuffers(1, intBuffer)
        fboId = intBuffer[0]
    }

    public fun addColorAttachment(texture0: Texture, slot: Int = 0)
    {
        bind()
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + slot, GL_TEXTURE_2D, texture0.getRendererID(), 0);
        unbind()
    }

    public fun bind()
    {
        GLES20.glBindFramebuffer(GL_FRAMEBUFFER, fboId)
    }

    public fun unbind()
    {
        GLES20.glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }
}