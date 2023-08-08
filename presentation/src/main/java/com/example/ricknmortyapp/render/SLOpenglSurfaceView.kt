package com.example.ricknmortyapp.render

import android.content.Context
import android.opengl.GLSurfaceView

class SLOpenglSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: SLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = SLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}