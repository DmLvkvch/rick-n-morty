package com.example.ricknmortyapp.render

import android.opengl.GLES20

class Shader(vertexCode: String, fragmentCode: String) {
    enum class ShaderType {
        VERTEX, FRAGMENT
    }

    private val shaderProgramId: Int

    init {
        val vertexId = compile(vertexCode, ShaderType.VERTEX)
        val fragmentId = compile(fragmentCode, ShaderType.FRAGMENT)
        shaderProgramId = GLES20.glCreateProgram()
        GLES20.glAttachShader(shaderProgramId, vertexId)
        GLES20.glAttachShader(shaderProgramId, fragmentId)
        GLES20.glLinkProgram(shaderProgramId)
        GLES20.glDeleteShader(vertexId)
        GLES20.glDeleteShader(fragmentId)
        val param = IntArray(1)
        GLES20.glGetProgramiv(shaderProgramId, GLES20.GL_LINK_STATUS, param, 0)
        if (param[0] != 0) {
            val glGetProgramInfoLog = GLES20.glGetProgramInfoLog(shaderProgramId)
            val x = 0
        }
    }

    public fun getRendererID(): Int {
        return shaderProgramId;
    }

    fun bind() {
        GLES20.glUseProgram(shaderProgramId)
    }

    fun unbind() {
        GLES20.glUseProgram(0)
    }

    fun destroy() {
        GLES20.glDeleteProgram(shaderProgramId)
    }

    fun setInt(name: String, value: Int) {}

    fun setFloat(name: String, value: Float) {}

    fun setTexture(name: String, texture: Texture, slot: Int) {}

    private fun compile(shaderCode: String, type: ShaderType): Int {
        val glShaderType = getShaderType(type)
        val shaderId = GLES20.glCreateShader(glShaderType)
        GLES20.glShaderSource(shaderId, shaderCode)
        GLES20.glCompileShader(shaderId)
        return shaderId
    }

    private fun getShaderType(shaderType: ShaderType): Int {
        return when (shaderType) {
            ShaderType.VERTEX -> GLES20.GL_VERTEX_SHADER
            ShaderType.FRAGMENT -> GLES20.GL_FRAGMENT_SHADER
            else -> -1
        }
    }
}