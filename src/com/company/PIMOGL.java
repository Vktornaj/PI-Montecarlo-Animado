package com.company;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.swing.*;
import java.awt.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class PIMOGL extends  JPanel{

    float rotateX = 0;
    float rotateY = 0;
    int np = 800;
    float cvaloresX[] = new float[np];
    float cvaloresY[] = new float[np];
    float radio = 1.0f;
    int presision = 16384;
    int dormir = 0;
    float tamP = 0.005f;
    float[] rx, ry;
    int tp = 14, pa = 0;
    int pdc = 0;

    // The window handle
    private long window;

    public void run() throws InterruptedException {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        int p = Integer.parseInt(JOptionPane.showInputDialog("Presicion: ", 0));
        if (p != 0){
            presision = (int)Math.pow(2, p);
            tp = p; // (int)(Math.log(presision) / Math.log(2))
        }
        rx = new float[presision];
        ry = new float[presision];
        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(600, 600, "PI Montecarlo!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() throws InterruptedException {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.


        crearAleatorios();
        int cont = 0;
        boolean bandera = true;
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            dibujar();
            //Thread.sleep(dormir);
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < cont; j++) {
                    crearPunto(rx[j], ry[j]);
                    //pa++;
                }
                if (cont < presision) {
                    cont++;
                } else {
                    if (bandera) {
                        for (int j = 0; j < Math.pow(2, tp); j++) {
                            if (rx[j] * rx[j] + ry[j] * ry[j] <= 1) {
                                pdc++;
                            }
                        }
                        System.out.println(pdc);
                        System.out.println(Math.pow(2, tp));
                        JOptionPane.showMessageDialog(null, " PI aprox = " + 4 * pdc / Math.pow(2,tp));
                        bandera = false;
                    }
                }
            }
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public void dibujar(){
        glLoadIdentity();

        // Otras transformaciones
        // glTranslatef( 0.1, 0.0, 0.0 );      // No incluido
        // glRotatef( 180, 0.0, 1.0, 0.0 );    // No incluido

        // Rotar cuando el usuario cambie “rotate_x” y “rotate_y”
        glRotatef( rotateX, 1.0f, 0.0f, 0.0f );
        glRotatef( rotateY, 0.0f, 1.0f, 0.0f);

        // Otras transformaciones
        // glScalef( 2.0, 2.0, 0.0 );          // No incluido

        //LADO FRONTAL: lado multicolor
        float valoresX[] = {radio, radio, -radio, -radio};
        float valoresY[] = {radio, -radio, -radio, radio};

        glBegin(GL_POLYGON);

        glColor3f( 0.3f, 0.3f, 0.3f );
        glVertex3f( radio, radio, -0.5f );
        glVertex3f( radio, -radio, -0.5f );
        glVertex3f( -radio, -radio, -0.5f );
        glVertex3f( -radio, radio, -0.5f );

        glEnd();
        dibujarCirculo();

//        for (int j = 0; j < presision; j++) {
//            crearPunto(rx[j], ry[j]);
//        }

        glFlush();
        //glutSwapBuffers();

    }

    public void dibujarCirculo(){
        int detalle = 100;
        float x, y;
        glBegin(GL_POLYGON);
        glColor3f( 0.9f, 0.1f, 0.1f );
        for (int i = -detalle; i <= detalle; i++) {
            x = (float)(i * (1.0f / detalle));
            y = (float) Math.sqrt(Math.pow(1, 2) - Math.pow(x, 2));
            glVertex2d(x, y);
        }
        for (int i = detalle; i > -detalle; i--) {
            x = (float)(i * (1.0f / detalle));
            y = -(float) Math.sqrt(Math.pow(1, 2) - Math.pow(x, 2));
            glVertex2d(x, y);
        }
        glEnd();
    }

    public void crearPunto(float x, float y){
        float t = tamP;
        float px[] = { x, x + t, x + t, x};
        float py[] = { y, y, y + t, y + t};

        glBegin(GL_POLYGON);

        glColor3f( 0.09f, 1.0f, 0.05f );
        glVertex3f( x, y, -0.5f );
        glVertex3f( x + t, y, -0.5f );
        glVertex3f( x + t, y + t, -0.5f );
        glVertex3f( x, y + t, -0.5f );

        glEnd();

    }
    public void crearAleatorios(){
        PsdAleatorios ps = new PsdAleatorios(500, 34, 47,  tp);
        for (int i = 0; i < presision; i++) {
            rx[i] = (float)(ps.numsD[i] * 2 - 1);
        }
        PsdAleatorios ps2 = new PsdAleatorios(700, 28, 49,  tp);
        for (int i = 0; i < presision; i++) {
            ry[i] = (float)(ps2.numsD[i] * 2 - 1);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new PIMOGL().run();
    }

}