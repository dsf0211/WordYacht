package com.company;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Turno implements ActionListener, KeyListener{
    Principal principal;
    ArrayList<Jugador> jugadores;
    Timer timer = new Timer();

    public Turno(ArrayList<Jugador> jugadores) throws IOException {

        //EMPIEZA LA PARTIDA

        this.jugadores = jugadores;
        principal = new Principal(jugadores);
        principal.getParar().addActionListener(this);
        principal.getEmpezar().addActionListener(this);
        principal.getEntrada().addKeyListener(this);
        principal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(e.getActionCommand(), "TERMINAR")){
            timer.cancel();
            principal.finalizaTurno(jugadores);

            principal.getParar().setVisible(false);
            principal.getClip().stop();
            if (principal.getPalabras().size() >= 1) {
                principal.recuento(jugadores);
            }
        }
        if (Objects.equals(e.getActionCommand(), "COMENZAR")) {
            principal.getClip().start();

            //EMPIEZA LA CUENTA REGRESIVA
            principal.getEntrada().setVisible(true);
            principal.getLetra1().setVisible(true);
            principal.getLetra2().setVisible(true);
            principal.getLetra3().setVisible(true);
            principal.getLetra4().setVisible(true);
            principal.getLetra5().setVisible(true);
            principal.getLetra6().setVisible(true);
            principal.getLetra7().setVisible(true);
            principal.getLetra8().setVisible(true);

            principal.getEnviarButton().setVisible(true);
            principal.getLimpiarButton().setVisible(true);
            principal.getEmpezar().setVisible(false);
            principal.getParar().setVisible(true);

            //TEMPORIZADOR
            Timer timer1 = new Timer();
            timer = timer1;
            timer.scheduleAtFixedRate(new TimerTask() {
                int i = 200;

                public void run() {

                    principal.getTemporizador().setText(String.valueOf(i));
                    i--;

                    //SE ACABA EL TIEMPO

                    if (i < 0) {
                        timer.cancel();
                        principal.getTemporizador().setText("0");
                        principal.getParar().setVisible(false);
                        principal.finalizaTurno(jugadores);
                        try {
                            principal.sonidoFinalizar();
                            principal.getClip().stop();
                        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                            ex.printStackTrace();
                        }
                        if (principal.getPalabras().size() >= 1) {
                            principal.recuento(jugadores);
                        }
                    }
                }
            }, 0, 1000);
        ;}
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //RETROCESO
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if (!(principal.getCadena().equals(""))){
                String cadena =  principal.getCadena();
                String ultima_letra = String.valueOf(cadena.charAt(cadena.length()-1));
                if (principal.getLetra1().getText().equals(ultima_letra) && !principal.getLetra1().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra1().setVisible(true);
                }
                else if (principal.getLetra2().getText().equals(ultima_letra) && !principal.getLetra2().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra2().setVisible(true);
                }
                else if (principal.getLetra3().getText().equals(ultima_letra) && !principal.getLetra3().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra3().setVisible(true);
                }
                else if (principal.getLetra4().getText().equals(ultima_letra) && !principal.getLetra4().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra4().setVisible(true);
                }
                else if (principal.getLetra5().getText().equals(ultima_letra) && !principal.getLetra5().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra5().setVisible(true);
                }
                else if (principal.getLetra6().getText().equals(ultima_letra) && !principal.getLetra6().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra6().setVisible(true);
                }
                else if (principal.getLetra7().getText().equals(ultima_letra) && !principal.getLetra7().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra7().setVisible(true);
                }
                else if (principal.getLetra8().getText().equals(ultima_letra) && !principal.getLetra8().isVisible()) {
                    principal.setCadena(principal.getCadena().substring(0,principal.getCadena().length() - 1));
                    principal.getEntrada().setText(principal.getCadena());
                    principal.getLetra8().setVisible(true);
                }
            }
        }
        //ENVIAR PALABRA
        if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
            try {
                if (principal.getCadena().length() >= 3) {
                    if (!principal.getPalabras().contains(principal.getCadena())) {
                        if (principal.Diccionario().contains(principal.getCadena())) {
                            principal.getModelo().addElement(principal.getCadena());
                            principal.getPalabras().add(principal.getCadena());
                            principal.getContador().setText(String.valueOf(principal.getPalabras().size()));
                            principal.setCadena("");
                            principal.getEntrada().setText("");
                            principal.getCuadroLista().setModel(principal.getModelo());
                            for (JButton boton : principal.getBotones()) {
                                if (!boton.isVisible()) {
                                    boton.setBackground(Color.black);
                                }
                            }
                            principal.getLetra1().setVisible(true);
                            principal.getLetra2().setVisible(true);
                            principal.getLetra3().setVisible(true);
                            principal.getLetra4().setVisible(true);
                            principal.getLetra5().setVisible(true);
                            principal.getLetra6().setVisible(true);
                            principal.getLetra7().setVisible(true);
                            principal.getLetra8().setVisible(true);
                            principal.sonidoAcierto();
                            principal.recuento(jugadores);
                        } else {
                            principal.sonidoError();
                        }
                    } else {
                        principal.sonidoRepetida();
                    }
                } else {
                    principal.sonidoError();
                }
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                ex.printStackTrace();
            }
        }
        //ESCRIBIR LETRA
        if (e.getKeyCode() >= 65 && e.getKeyCode() <= 90){
            String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String letra = String.valueOf(letras.charAt(e.getKeyCode() - 65));
            if (principal.getLetra1().getText().equals(letra) && principal.getLetra1().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra1().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra1().setVisible(false);
            }
            else if (principal.getLetra2().getText().equals(letra) && principal.getLetra2().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra2().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra2().setVisible(false);
            }
            else if (principal.getLetra3().getText().equals(letra) && principal.getLetra3().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra3().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra3().setVisible(false);
            }
            else if (principal.getLetra4().getText().equals(letra) && principal.getLetra4().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra4().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra4().setVisible(false);
            }
            else if (principal.getLetra5().getText().equals(letra) && principal.getLetra5().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra5().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra5().setVisible(false);
            }
            else if (principal.getLetra6().getText().equals(letra) && principal.getLetra6().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra6().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra6().setVisible(false);
            }
            else if (principal.getLetra7().getText().equals(letra) && principal.getLetra7().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra7().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra7().setVisible(false);
            }
            else if (principal.getLetra8().getText().equals(letra) && principal.getLetra8().isVisible()) {
                String cadena = principal.getCadena();
                principal.setCadena(cadena + principal.getLetra8().getText());
                principal.getEntrada().setText(principal.getCadena());
                principal.getLetra8().setVisible(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}


