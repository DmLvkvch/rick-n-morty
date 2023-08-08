package com.example.ricknmortyapp.render

import android.opengl.GLES20
import android.opengl.GLES20.GL_RGBA
import android.opengl.GLES20.GL_TEXTURE0
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_UNSIGNED_BYTE
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glDeleteTextures
import java.nio.ByteBuffer
import java.nio.IntBuffer

class Texture(width: Int, height: Int, array: ByteBuffer)
{
    private val textureID: Int

    init {
        var texture: IntBuffer = IntBuffer.allocate(1);
        GLES20.glGenTextures(1, texture)
        textureID = texture[0];
        GLES20.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, array)
    }

    fun bind(slot: Int = 0)
    {
        GLES20.glActiveTexture(GL_TEXTURE0 + slot)
        glBindTexture(GL_TEXTURE_2D, textureID)
    }

    fun unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    fun getRendererID(): Int
    {
        return textureID
    }

    fun destroy()
    {
        glDeleteTextures(1, IntBuffer.allocate(1).apply {
            put(0, textureID)
        })
    }

}