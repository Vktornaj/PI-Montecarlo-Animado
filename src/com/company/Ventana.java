package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {

    JPanel north, south, center, derecha, izquierda;
    JLabel titulo, lbDescripcion, aproxPI;
    JButton btnIniciar;

    public Ventana(String title) {
        super(title);

        initFrame();
        initComponents();
        addComponentsToContentPane();
        addListeners();

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        this.setSize(2 * width / 3, 2 * height / 3);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    private void initFrame() {
        north = new JPanel();
        south = new JPanel();
        center = new JPanel();
        derecha = new JPanel();
        izquierda = new JPanel();
        north.setBackground(Color.green);
        south.setBackground(Color.blue);
        center.setBackground(Color.black);
    }

    private void initComponents() {
        titulo = new JLabel("Pi con Montecarlo");
        lbDescripcion = new JLabel("Iniciar el calculo de PI con el metodo Montecarlo: ");
        btnIniciar = new JButton("INICIAR");
        aproxPI = new JLabel();
        // izquierda.setBackground(Color.red);
        // derecha.setBackground(Color.CYAN);

    }

    private void addComponentsToContentPane() {
        this.getContentPane().add(north, BorderLayout.NORTH);
        this.getContentPane().add(south, BorderLayout.SOUTH);
        this.getContentPane().add(center, BorderLayout.CENTER);
        north.add(titulo);
        center.setLayout(new GridLayout(1, 2));
        center.add(izquierda);
        izquierda.setLayout(new GridLayout(1, 1));
        Grafico grf = new Grafico();
        //HelloWorld hw = new HelloWorld();

        izquierda.add(grf);
        center.add(derecha);
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        lbDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        derecha.add(lbDescripcion);
        derecha.add(btnIniciar);
        derecha.add(aproxPI);
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //try {
                    //hw.run();
                //} catch (InterruptedException e) {
                    //e.printStackTrace();
                //}
            }
        });

    }

    private void addListeners() {
        // add a listener

    }
}
