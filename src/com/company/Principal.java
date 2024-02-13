package com.company;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Principal extends JDialog {

    public JList getCuadroLista() {
        return cuadroLista;
    }

    public DefaultListModel getModelo() {
        return modelo;
    }

    public Clip getClip() {
        return clip;
    }

    private JButton letra1;
    private JButton letra2;
    private JButton letra3;
    private JButton letra4;

    public JTextField getTotal() {
        return total;
    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    private JButton letra5;
    private JButton letra6;
    private JButton letra7;
    private JButton letra8;
    private JPanel panelEntrada;
    private JButton enviarButton;
    private JButton limpiarButton;
    private JTextField entrada;
    private JList cuadroLista;
    private JButton a0Button;
    private JButton a0Button1;
    private JButton a0Button2;
    private JButton a0Button3;
    private JButton a0Button4;
    private JButton a0Button5;
    private JButton a0Button6;
    private JButton a0Button7;
    private JTextField total;
    private JTextField nombre;
    private JTextField temporizador;
    private JButton Empezar;
    private JTextField Pbonus;
    private JButton Parar;
    private JTextField contador;
    private JTextField tres;
    private JTextField cuatro;
    private JTextField cinco;
    private JTextField inicial;
    private JTable tabla;
    private JTextField tresA;
    private JTextField tresB;
    private JTextField tresC;
    private JTextField cuatroA;
    private JTextField cuatroB;
    private JTextField cuatroC;
    private JTextField cincoA;
    private JTextField cincoB;
    private JTextField cincoC;
    private JTextField escA;
    private JTextField escB;
    private JTextField escC;
    private JTextField iniA;
    private JTextField iniB;
    private JTextField iniC;
    private JTextField yachtA;
    private int turnos = 0;
    private DefaultListModel modelo = new DefaultListModel();
    private ArrayList<JButton> botones = new ArrayList<>();
    private Clip clip;
    DefaultTableModel m;

    public JTextField getContador() {
        return contador;
    }

    public int getTurnos(){
        return turnos;
    }

    public JButton getParar() {
        return Parar;
    }

    public JTextField getPbonus() {
        return Pbonus;
    }

    private String cadena = "";
    private ArrayList<String> palabras = new ArrayList<>();
    private int bonus;

    public JTextField getTemporizador() {
        return temporizador;
    }

    public JButton getA0Button() {
        return a0Button;
    }

    public JButton getEmpezar() {
        return Empezar;
    }

    public void setBonus(int bonus){
        this.bonus = bonus;
    }

    public JButton getA0Button1() {
        return a0Button1;
    }

    public JButton getA0Button2() {
        return a0Button2;
    }

    public JButton getA0Button3() {
        return a0Button3;
    }

    public JTextField getEntrada() {
        return entrada;
    }

    public JButton getA0Button4() {
        return a0Button4;
    }

    public JButton getA0Button5() {
        return a0Button5;
    }

    public JButton getA0Button6() {
        return a0Button6;
    }

    public JButton getA0Button7() {
        return a0Button7;
    }

    public ArrayList<String> getPalabras() {
        return palabras;
    }

    public JButton getEnviarButton() {
        return enviarButton;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public Principal(ArrayList<Jugador> jugadores) throws IOException {
        try {
            Clip  clip_nuevo = musica();
            clip = clip_nuevo;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }

        ArrayList<String> diccionario = Diccionario();
        setSize(1200,950);
        setLocation(200,0);
        setTitle("Word Yacht");
        setContentPane(panelEntrada);
        getLetra1().setVisible(false);
        getLetra2().setVisible(false);
        getLetra3().setVisible(false);
        getLetra4().setVisible(false);
        getLetra5().setVisible(false);
        getLetra6().setVisible(false);
        getLetra7().setVisible(false);
        getLetra8().setVisible(false);
        tresA.setBorder(null);
        tresB.setBorder(null);
        tresC.setBorder(null);
        cuatroA.setBorder(null);
        cuatroB.setBorder(null);
        cuatroC.setBorder(null);
        cincoA.setBorder(null);
        cincoB.setBorder(null);
        cincoC.setBorder(null);
        escA.setBorder(null);
        escB.setBorder(null);
        escC.setBorder(null);
        iniA.setBorder(null);
        iniB.setBorder(null);
        iniC.setBorder(null);
        yachtA.setBorder(null);

        //CARGAR DATOS DE JUGADOR
        empezarTurno(jugadores);
        setVisible(true);



        letra1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra1.getText();
                entrada.setText(cadena);
                letra1.setVisible(false);

            }
        });
        letra2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra2.getText();
                entrada.setText(cadena);
                letra2.setVisible(false);
            }
        });
        letra3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra3.getText();
                entrada.setText(cadena);
                letra3.setVisible(false);
            }
        });
        letra4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra4.getText();
                entrada.setText(cadena);
                letra4.setVisible(false);
            }
        });
        letra5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra5.getText();
                entrada.setText(cadena);
                letra5.setVisible(false);
            }
        });
        letra6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra6.getText();
                entrada.setText(cadena);
                letra6.setVisible(false);

            }
        });
        letra7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra7.getText();
                entrada.setText(cadena);
                letra7.setVisible(false);
            }
        });
        letra8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena += letra8.getText();
                entrada.setText(cadena);
                letra8.setVisible(false);
            }
        });

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cadena.length() >= 3) {
                        if (!palabras.contains(cadena)) {
                            if (diccionario.contains(cadena)) {
                                modelo.addElement(cadena);
                                palabras.add(cadena);
                                contador.setText(String.valueOf(palabras.size()));
                                cadena = "";
                                entrada.setText("");
                                cuadroLista.setModel(modelo);
                                for (JButton boton: botones){
                                    if (!boton.isVisible()){
                                        boton.setBackground(Color.black);
                                    }
                                }
                                letra1.setVisible(true);
                                letra2.setVisible(true);
                                letra3.setVisible(true);
                                letra4.setVisible(true);
                                letra5.setVisible(true);
                                letra6.setVisible(true);
                                letra7.setVisible(true);
                                letra8.setVisible(true);
                                sonidoAcierto();
                                recuento(jugadores);
                            } else {
                                sonidoError();
                            }
                        } else {
                            sonidoRepetida();
                        }
                    } else {
                        sonidoError();
                    }
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex){
                    ex.printStackTrace();
                }
            }
        });
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadena = "";
                entrada.setText("");
                letra1.setVisible(true);
                letra2.setVisible(true);
                letra3.setVisible(true);
                letra4.setVisible(true);
                letra5.setVisible(true);
                letra6.setVisible(true);
                letra7.setVisible(true);
                letra8.setVisible(true);
            }
        });

        a0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button.getText()) + bonus);
                jugadores.get(turnos).setPtresletras(Integer.parseInt(a0Button.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);
            }
        });
        a0Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button1.getText()) + bonus);
                jugadores.get(turnos).setPcuatroletras(Integer.parseInt(a0Button1.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button2.getText()) + bonus);
                jugadores.get(turnos).setPmasdecinco(Integer.parseInt(a0Button2.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button3.getText()) + bonus);
                jugadores.get(turnos).setPescalera(Integer.parseInt(a0Button3.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button4.getText()) + bonus);
                jugadores.get(turnos).setPinicial(Integer.parseInt(a0Button4.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button5.getText()) + bonus);
                jugadores.get(turnos).setPtodas(Integer.parseInt(a0Button5.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button6.getText()) + bonus);
                jugadores.get(turnos).setPcantidad(Integer.parseInt(a0Button6.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);

            }
        });
        a0Button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugadores.get(turnos).Sumar(Integer.parseInt(a0Button7.getText()) + bonus);
                jugadores.get(turnos).setPyacht(Integer.parseInt(a0Button7.getText()));
                total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));
                getPbonus().setText(String.valueOf(bonus));
                a0Button.setEnabled(false);
                a0Button1.setEnabled(false);
                a0Button2.setEnabled(false);
                a0Button3.setEnabled(false);
                a0Button4.setEnabled(false);
                a0Button5.setEnabled(false);
                a0Button6.setEnabled(false);
                a0Button7.setEnabled(false);
                pasarTurno(jugadores);
            }
        });
    }

    private void pasarTurno(ArrayList<Jugador> jugadores) {
        turnos++;
        if (turnos < jugadores.size()) {
            try {
                Clip  clip_nuevo = musica();
                clip = clip_nuevo;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }

            getTemporizador().setText("200");
            setSize(1200,950);
            setCadena("");

            //CARGAR DATOS DE JUGADOR

            empezarTurno(jugadores);
            getTres().setText("");
            getCuatro().setText("");
            getCinco().setText("");
            getInicial().setText("");
            tresA.setForeground(Color.black);
            tresB.setForeground(Color.black);
            tresC.setForeground(Color.black);
            cuatroA.setForeground(Color.black);
            cuatroB.setForeground(Color.black);
            cuatroC.setForeground(Color.black);
            cincoA.setForeground(Color.black);
            cincoB.setForeground(Color.black);
            cincoC.setForeground(Color.black);
            escA.setForeground(Color.black);
            escB.setForeground(Color.black);
            escC.setForeground(Color.black);
            iniA.setForeground(Color.black);
            iniB.setForeground(Color.black);
            iniC.setForeground(Color.black);
            yachtA.setForeground(Color.black);

            contador.setText("");
            letra1.setBackground(Color.RED);
            letra2.setBackground(Color.RED);
            letra3.setBackground(Color.RED);
            letra4.setBackground(Color.RED);
            letra5.setBackground(Color.RED);
            letra6.setBackground(Color.RED);
            letra7.setBackground(Color.RED);
            letra8.setBackground(Color.RED);

            getEntrada().setText("");
            ArrayList<String> vacias = new ArrayList<>();
            palabras = vacias;
            DefaultListModel vacio = new DefaultListModel();
            modelo = vacio;
            cuadroLista.setModel(modelo);

        }
        else if (turnos == jugadores.size()){
            //TERMINA LA PARTIDA
            crearTabla();
            añadirFilas(jugadores);
            getEmpezar().setVisible(false);
            int[] puntuaciones = new int[jugadores.size()];

            for (int i = 0; i< jugadores.size(); i++){
                puntuaciones[i] = jugadores.get(i).Puntuacion();
            }
            Arrays.sort(puntuaciones);
            int puntuacion_ganadora = puntuaciones[puntuaciones.length - 1];
            String ganador = "";
            for (Jugador jugador : jugadores){
                if (jugador.Puntuacion() == puntuacion_ganadora){
                    ganador = jugador.Nombre();
                }
            }
            try {
                sonidoVictoria();
                int seleccion = JOptionPane.showOptionDialog(
                        null,
                        "¡Has ganado, "+ganador+"!",
                        "Fin de la partida",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[] { "NUEVA PARTIDA", "SALIR"},
                        "SALIR");
                if (seleccion == 0){
                    dispose();
                    int n_jugadores = jugadores.size()/8;
                    for (int i = 0; i < n_jugadores; i++){
                        jugadores.get(i).setPtresletras(-1);
                        jugadores.get(i).setPcuatroletras(-1);
                        jugadores.get(i).setPmasdecinco(-1);
                        jugadores.get(i).setPescalera(-1);
                        jugadores.get(i).setPinicial(-1);
                        jugadores.get(i).setPtodas(-1);
                        jugadores.get(i).setPcantidad(-1);
                        jugadores.get(i).setPyacht(-1);
                        jugadores.get(i).setPuntuacion(0);
                    }
                    Turno turno = new Turno(jugadores);
                } else if (seleccion == 1){
                    System.exit(0);
                }
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void EmpezarPartida(ArrayList<Jugador> jugadores) throws IOException {
        Turno turno = new Turno(jugadores);
    }

    public JButton getLetra1() {
        return letra1;
    }

    public JButton getLetra2() {
        return letra2;
    }

    public JButton getLetra3() {
        return letra3;
    }

    public JButton getLetra4() {
        return letra4;
    }

    public JButton getLetra5() {
        return letra5;
    }

    public JButton getLetra6() {
        return letra6;
    }

    public JButton getLetra7() {
        return letra7;
    }

    public JButton getLetra8() {
        return letra8;
    }

    public ArrayList<String> Diccionario() throws IOException {
        FileReader lector = new FileReader("C://Users//David//Desktop//WordYacht//Recursos//Diccionario.txt");
        BufferedReader lector2 = new BufferedReader(lector);
        ArrayList<String> diccionario = new ArrayList<>();
        for (int i = 0; i < 137483; i++) {
            diccionario.add(lector2.readLine().replace(" ",""));
        }
        return diccionario;
    }

    public void sonidoError() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File error = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\error.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(error);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public void sonidoAcierto() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File acierto = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\acierto.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(acierto);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public void sonidoRepetida() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File repetida = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\repetida.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(repetida);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public void sonidoFinalizar() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File finalizar = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\tiempo.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(finalizar);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public Clip musica() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File musica = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\ambiente.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musica);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public void sonidoVictoria() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File victoria = new File(FileSystems.getDefault().getPath("C:\\Users\\David\\Desktop\\WordYacht\\Recursos").toAbsolutePath() + "\\victoria.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(victoria);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public void empezarTurno(ArrayList<Jugador> jugadores){
        setBonus(0);
        getPbonus().setText("0");
        nombre.setText(jugadores.get(turnos).Nombre());
        total.setText(String.valueOf(jugadores.get(turnos).Puntuacion()));

        String[] letras = new String[8];
        String abecedario = "abcdefghijklmnopqrstuvwxyzabcdefghijlmnoprstuvz";
        String vocales = "aeiou";
        for (int i = 0; i < 2; i++) {
            int posicion = (int) (Math.random() * 5);
            letras[i] = String.valueOf(vocales.toUpperCase().charAt(posicion));
        }
        for (int i = 2; i < 8; i++) {
            int posicion = (int) (Math.random() * 47);
            letras[i] = String.valueOf(abecedario.toUpperCase().charAt(posicion));
        }

        getEnviarButton().setVisible(false);
        getLimpiarButton().setVisible(false);
        getEntrada().setVisible(false);
        getParar().setVisible(false);
        getEmpezar().setVisible(true);

        getLetra1().setText(letras[0]);
        getLetra2().setText(letras[1]);
        getLetra3().setText(letras[2]);
        getLetra4().setText(letras[3]);
        getLetra5().setText(letras[4]);
        getLetra6().setText(letras[5]);
        getLetra7().setText(letras[6]);
        getLetra8().setText(letras[7]);

        botones = new ArrayList<>();
        botones.add(letra1);
        botones.add(letra2);
        botones.add(letra3);
        botones.add(letra4);
        botones.add(letra5);
        botones.add(letra6);
        botones.add(letra7);
        botones.add(letra8);
        aparecerBotones(jugadores);
        crearTabla();
        añadirFilas(jugadores);
    }

    public void finalizaTurno(ArrayList<Jugador> jugadores){
        getLetra1().setVisible(false);
        getLetra2().setVisible(false);
        getLetra3().setVisible(false);
        getLetra4().setVisible(false);
        getLetra5().setVisible(false);
        getLetra6().setVisible(false);
        getLetra7().setVisible(false);
        getLetra8().setVisible(false);
        getEntrada().setVisible(false);
        getEnviarButton().setVisible(false);
        getLimpiarButton().setVisible(false);
        activarBotones(jugadores);
    }

    public void aparecerBotones(ArrayList<Jugador> jugadores){
        a0Button.setEnabled(false);
        a0Button1.setEnabled(false);
        a0Button2.setEnabled(false);
        a0Button3.setEnabled(false);
        a0Button4.setEnabled(false);
        a0Button5.setEnabled(false);
        a0Button6.setEnabled(false);
        a0Button7.setEnabled(false);

        a0Button.setBackground(Color.white);
        a0Button1.setBackground(Color.white);
        a0Button2.setBackground(Color.white);
        a0Button3.setBackground(Color.white);
        a0Button4.setBackground(Color.white);
        a0Button5.setBackground(Color.white);
        a0Button6.setBackground(Color.white);
        a0Button7.setBackground(Color.white);

        if (jugadores.get(turnos).getPtresletras() == -1) {
            getA0Button().setText("0");
        } else {
            getA0Button().setText(String.valueOf(jugadores.get(turnos).getPtresletras()));
            getA0Button().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPcuatroletras() == -1) {
            getA0Button1().setText("0");
        } else {
            getA0Button1().setText(String.valueOf(jugadores.get(turnos).getPcuatroletras()));
            getA0Button1().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPmasdecinco() == -1) {
            getA0Button2().setText("0");
        } else {
            getA0Button2().setText(String.valueOf(jugadores.get(turnos).getPmasdecinco()));
            getA0Button2().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPescalera() == -1) {
            getA0Button3().setText("0");
        } else {
            getA0Button3().setText(String.valueOf(jugadores.get(turnos).getPescalera()));
            getA0Button3().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPinicial() == -1) {
            getA0Button4().setText("0");
        } else {
            getA0Button4().setText(String.valueOf(jugadores.get(turnos).getPinicial()));
            getA0Button4().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPtodas() == -1) {
            getA0Button5().setText("0");
        } else {
            getA0Button5().setText(String.valueOf(jugadores.get(turnos).getPtodas()));
            getA0Button5().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPcantidad() == -1) {
            getA0Button6().setText("0");
        } else {
            getA0Button6().setText(String.valueOf(jugadores.get(turnos).getPcantidad()));
            getA0Button6().setBackground(Color.red);
        }
        if (jugadores.get(turnos).getPyacht() == -1) {
            getA0Button7().setText("0");
        } else {
            getA0Button7().setText(String.valueOf(jugadores.get(turnos).getPyacht()));
            getA0Button7().setBackground(Color.red);
        }
    }

    public void activarBotones(ArrayList<Jugador> jugadores){
        if (jugadores.get(turnos).getPtresletras() == -1) {
            a0Button.setEnabled(true);
        }
        if (jugadores.get(turnos).getPcuatroletras() == -1) {
            a0Button1.setEnabled(true);
        }
        if (jugadores.get(turnos).getPmasdecinco() == -1) {
            a0Button2.setEnabled(true);
        }
        if (jugadores.get(turnos).getPescalera() == -1) {
            a0Button3.setEnabled(true);
        }
        if (jugadores.get(turnos).getPinicial() == -1) {
            a0Button4.setEnabled(true);
        }
        if (jugadores.get(turnos).getPtodas() == -1) {
            a0Button5.setEnabled(true);
        }
        if (jugadores.get(turnos).getPcantidad() == -1) {
            a0Button6.setEnabled(true);
        }
        if (jugadores.get(turnos).getPyacht() == -1) {
            a0Button7.setEnabled(true);
        }
    }

    public JTextField getTres() {
        return tres;
    }

    public JTextField getCuatro() {
        return cuatro;
    }

    public JTextField getCinco() {
        return cinco;
    }


    public JTextField getInicial() {
        return inicial;
    }

    public void recuento(ArrayList<Jugador> jugadores) {
        ArrayList<String> palabras = getPalabras();
        int tres = 0;
        int cuatro = 0;
        int masdecinco;
        int cinco = 0;
        int seis = 0;
        int siete = 0;
        int ocho = 0;
        boolean escaleratres = false;
        boolean escaleracuatro = false;
        boolean escaleracinco = false;
        int inicial;
        boolean todas = false;
        boolean yacht = false;
        String[] iniciales = new String[palabras.size()];
        for (
                int i = 0; i < palabras.size(); i++) {
            iniciales[i] = String.valueOf(palabras.get(i).charAt(0));
            if (palabras.get(i).length() == 3) {
                tres++;
            }
            if (palabras.get(i).length() == 4) {
                cuatro++;
            }
            if (palabras.get(i).length() == 5) {
                cinco++;
            }
            if (palabras.get(i).length() == 6) {
                seis++;
            }
            if (palabras.get(i).length() == 7) {
                siete++;
            }
            if (palabras.get(i).length() == 8) {
                ocho++;
            }

        }
        if ((tres >= 1 && cuatro >= 1 && cinco >= 1) || (cuatro >= 1 && cinco >= 1 && seis >= 1) || (cinco >= 1 && seis >= 1 && siete >= 1) || (seis >= 1 && siete >= 1 && ocho >= 1)) {
            escaleratres = true;
        }
        if ((tres >= 1 && cuatro >= 1 && cinco >= 1 && seis >= 1) || (cuatro >= 1 && cinco >= 1 && seis >= 1 && siete >= 1) || (cinco >= 1 && seis >= 1 && siete >= 1 && ocho >= 1)) {
            escaleracuatro = true;
        }
        if ((tres >= 1 && cuatro >= 1 && cinco >= 1 && seis >= 1 && siete >= 1) || (cuatro >= 1 && cinco >= 1 && seis >= 1 && siete >= 1 && ocho >= 1)) {
            escaleracinco = true;
        }

        masdecinco = palabras.size() - tres - cuatro;

        String cadena = "";
        for (int j = 0; j < palabras.size(); j++) {
            cadena += palabras.get(j).charAt(0);
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (int j = 0; j < cadena.length(); j++) {
            char caracter = cadena.charAt(j);
            hashSet.add(String.valueOf(caracter));
        }

        int[] contador = new int[hashSet.size()];

        for (int j = 0; j < hashSet.size(); j++) {
            contador[j] = cadena.length() - cadena.replace(String.valueOf(hashSet.toArray()[j]), "").length();
        }
        Arrays.sort(contador);
        inicial = contador[contador.length - 1];

        if (getLetra1().getBackground() == Color.black && getLetra2().getBackground() == Color.black && getLetra3().getBackground() == Color.black && getLetra4().getBackground() == Color.black &&
                getLetra5().getBackground() == Color.black && getLetra6().getBackground() == Color.black && getLetra7().getBackground() == Color.black && getLetra8().getBackground() == Color.black ){
            todas = true;
        }

        if (palabras.size() >= 15) {
            yacht = true;
        }

        //PUNTAJES

        int Ptresletras = 0;
        int Pcuatroletras = 0;
        int Pmasdecinco = 0;
        int Pescalera = 0;
        int Pinicial = 0;
        int Ptodas = 0;
        int Pyacht = 0;
        int Pbonus = 0;

        if (tres >= 3) {
            Ptresletras = 15;
        }

        if (tres >= 5) {
            Ptresletras = 30;
        }

        if (tres >= 10) {
            Ptresletras = 45;
        }

        if (cuatro >= 2) {
            Pcuatroletras = 15;
        }

        if (cuatro >= 4) {
            Pcuatroletras = 30;
        }

        if (cuatro >= 6) {
            Pcuatroletras = 45;
        }

        if (masdecinco == 1) {
            Pmasdecinco = 30;
        }

        if (masdecinco == 2) {
            Pmasdecinco = 50;

        }

        if (masdecinco >= 3) {
            Pmasdecinco = 70;
        }

        if (escaleratres) {
            Pescalera = 25;
        }

        if (escaleracuatro) {
            Pescalera = 50;
        }

        if (escaleracinco) {
            Pescalera = 75;
        }

        if (inicial >= 3) {
            Pinicial = 15;
        }

        if (inicial >= 5) {
            Pinicial = 30;
        }

        if (inicial >= 10) {
            Pinicial = 45;
        }

        if (todas) {
            Ptodas = 20;
        }

        int Pcantidad = palabras.size();

        if (yacht) {
            Pyacht = 75;
        }

        if (siete >= 1 || ocho >= 1) {
            Pbonus = 50;

        }

        //APLICAR PUNTAJES Y COLORES A BOTONES NO PULSADOS

        if (jugadores.get(turnos).getPtresletras() == -1) {
            a0Button.setText(String.valueOf(Ptresletras));
            if (Ptresletras >= 15){
                tresA.setForeground(Color.red);
            }
            if (Ptresletras >= 30){
                tresB.setForeground(Color.red);
            }
            if (Ptresletras == 45){
                tresC.setForeground(Color.red);
            }
        }
        if (jugadores.get(turnos).getPcuatroletras() == -1) {
            a0Button1.setText(String.valueOf(Pcuatroletras));
            if (Pcuatroletras >= 15){
                cuatroA.setForeground(Color.red);
            }
            if (Pcuatroletras >= 30){
                cuatroB.setForeground(Color.red);
            }
            if (Pcuatroletras == 45){
                cuatroC.setForeground(Color.red);
            }
        }
        if (jugadores.get(turnos).getPmasdecinco() == -1) {
            a0Button2.setText(String.valueOf(Pmasdecinco));
            if (Pmasdecinco >= 30){
                cincoA.setForeground(Color.red);
            }
            if (Pmasdecinco >= 50){
                cincoB.setForeground(Color.red);
            }
            if (Pmasdecinco == 70){
                cincoC.setForeground(Color.red);
            }
        }
        if (jugadores.get(turnos).getPescalera() == -1) {
            a0Button3.setText(String.valueOf(Pescalera));
            if (Pescalera >= 25){
                escA.setForeground(Color.red);
            }
            if (Pescalera >= 50){
                escB.setForeground(Color.red);
            }
            if (Pescalera == 75){
                escC.setForeground(Color.red);
            }
        }
        if (jugadores.get(turnos).getPinicial() == -1) {
            a0Button4.setText(String.valueOf(Pinicial));
            if (Pinicial >= 15){
                iniA.setForeground(Color.red);
            }
            if (Pinicial >= 30){
                iniB.setForeground(Color.red);
            }
            if (Pinicial == 45){
                iniC.setForeground(Color.red);
            }
        }
        if (jugadores.get(turnos).getPtodas() == -1) {
            a0Button5.setText(String.valueOf(Ptodas));
        }
        if (jugadores.get(turnos).getPcantidad() == -1) {
            a0Button6.setText(String.valueOf(Pcantidad));
        }
        if (jugadores.get(turnos).getPyacht() == -1) {
            a0Button7.setText(String.valueOf(Pyacht));
            if (Pyacht == 75) {
                yachtA.setForeground(Color.red);
            }
        }

        getTres().setText(String.valueOf(tres));
        getCuatro().setText(String.valueOf(cuatro));
        getCinco().setText(String.valueOf(masdecinco));
        getInicial().setText(String.valueOf(inicial));
        setBonus(Pbonus);
        getPbonus().setText(String.valueOf(Pbonus));
    }

    public void crearTabla(){
        m = new DefaultTableModel(null, new String[] {"Columna 1", "Columna 2"});
        tabla.setModel(m);
    }

    public void añadirFilas(ArrayList<Jugador> jugadores){
        m = (DefaultTableModel) tabla.getModel();
        int n_jugadores = jugadores.size()/8;
        for (int i = 0; i < n_jugadores; i++){
            m.addRow(new String[] {jugadores.get(i).Nombre(), String.valueOf(jugadores.get(i).Puntuacion())});
        }
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        tabla.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        tabla.getColumnModel().getColumn(1).setCellRenderer(Alinear);
    }

}
