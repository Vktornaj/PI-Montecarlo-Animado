package com.company;

import javax.swing.*;
import java.awt.*;

public class Grafico extends JPanel implements Runnable{

    int radio = 200;
    int tp = 10000, pa = 0;
    int pdc = 0;

    static boolean dibujo = false;

    int valoresX[] = {radio, radio, -radio, -radio};
    int valoresY[] = {radio, -radio, -radio, radio};

    int np = 800;
    int cvaloresX[] = new int[np];
    int cvaloresY[] = new int[np];

    public void dibujarCirculo(){
        for (int i = -200; i <= 200; i++) {
            cvaloresX[i + 200] = i;
            cvaloresY[i + 200] = (int) Math.sqrt(Math.pow(200, 2) - Math.pow(i, 2));
        }

        for (int i = 199; i > -200; i--) {
            cvaloresX[i + 600] = -i;
            cvaloresY[i + 600] = -(int) Math.sqrt(Math.pow(200, 2) - Math.pow(i, 2));
        }
//        System.out.println("0 " + cvaloresX[0] + " " + cvaloresY[0]);
//        System.out.println("1 " + cvaloresX[1] + " " + cvaloresY[1]);
//        System.out.println("398 " + cvaloresX[398] + " " + cvaloresY[398]);
//        System.out.println("399 " + cvaloresX[399] + " " + cvaloresY[399]);
//        System.out.println("400 " + cvaloresX[400] + " " + cvaloresY[400]);
//        System.out.println("401 " + cvaloresX[401] + " " + cvaloresY[401]);
//        System.out.println("402 " + cvaloresX[402] + " " + cvaloresY[402]);
//        System.out.println("403 " + cvaloresX[403] + " " + cvaloresY[403]);
//        System.out.println("798 " + cvaloresX[798] + " " + cvaloresY[798]);
//        System.out.println("799 " + cvaloresX[799] + " " + cvaloresY[799]);

    }

    // int[] valoresX, valoresY;
    Thread hilo;
    public Grafico() {
        hilo = new Thread(this);
//        for (int i = 0; i < valoresY.length; i++) {
//            valoresY[i]=valoresY[i]*-1;
//        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.translate(getWidth() / 2, getHeight() / 2);
        g.setColor(Color.BLACK);
        //eje X
        g.drawLine(0, 0, getWidth() / 2, 0);
        g.drawLine(0, 0, -getWidth() / 2, -0);
        //eje Y
        g.drawLine(0, 0, 0, getHeight() / 2);
        g.drawLine(0, 0, 0, -getHeight() / 2);


        Polygon cuadrado = new Polygon(valoresX, valoresY, valoresX.length);
        g.drawPolygon(cuadrado);
        Color c = new Color(0f,0f,0f,0.75f );
        g.setColor(c);
        g.fillPolygon(cuadrado);
        System.out.println("" + valoresX.length);

        dibujarCirculo();
        Polygon circulo = new Polygon(cvaloresX, cvaloresY, cvaloresX.length);
        g.drawPolygon(circulo);
        Color c2 = new Color(1f,0f,0f,0.6f );
        g.setColor(c2);
        g.fillPolygon(circulo);
        System.out.println("" + cvaloresX.length);

        for (int i = 0; i < this.tp; i++) {
            int rx, ry;
            rx = (int)(Math.random() * 400) - 200;
            ry = (int)(Math.random() * 400) - 200;
            pa++;
            if (rx * rx + ry * ry <= 200 * 200){
                pdc++;
            }
            crearPunto(g , rx, ry);

        }
    }

    public void crearPunto(Graphics g, int x, int y){
        int t = 1;
        int px[] = { x, x + t, x + t, x};
        int py[] = { y, y, y + t, y + t};
        Polygon punto = new Polygon(px, py, px.length);
        g.drawPolygon(punto);
        Color c2 = new Color(0f,0.8f,0.2f,0.6f );
        g.setColor(c2);
        g.fillPolygon(punto);
    }

    @Override
    public void run() {
        for (int j = 0; j < 100; j++) {// cuantas veces se quiere que se mueva

            for (int i = 0; i < valoresX.length; i++) {
                try {
                    Thread.sleep(3);



                    System.out.println("" + j);
                } catch (InterruptedException ex) {
                    System.out.println("Error en el hilo");
                }
            }
            repaint();
            System.out.println("" + j);
        }
    }

}


