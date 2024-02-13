package com.company;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws IOException {

        //CREACION DE JUGADORES

        String numero = JOptionPane.showInputDialog("Introduzca el número de jugadores");
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 0; i< Integer.parseInt(numero); i++){
            String nombre = JOptionPane.showInputDialog("Escriba nombre de jugador "+ (i+1)).toUpperCase();
            Jugador jugador = new Jugador(nombre);
            jugadores.add(jugador);
        }

        //TURNOS

        ArrayList<Jugador> jugadores0 = new ArrayList<>(jugadores);
        for (int i = 0; i< 7; i++){
            jugadores.addAll(jugadores0);
        }

        //EMPIEZA LA PARTIDA

        Principal.EmpezarPartida(jugadores);

    }
}
