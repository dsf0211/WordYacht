package com.company;

public class Jugador{
    private int puntuacion = 0;
    private String nombre;
    private int Ptresletras = -1;
    private int Pcuatroletras = -1;
    private int Pmasdecinco = -1;
    private int Pescalera = -1;
    private int Pinicial = -1;
    private int Ptodas = -1;
    private int Pcantidad = -1;
    private int Pyacht = -1;

    public Jugador(String nombre){
        this.nombre = nombre;
    }

    public void Sumar(int suma){
        puntuacion+= suma;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int Puntuacion(){
        return puntuacion;
    }

    public int getPtresletras() {
        return Ptresletras;
    }

    public void setPtresletras(int ptresletras) {
        Ptresletras = ptresletras;
    }

    public int getPcuatroletras() {
        return Pcuatroletras;
    }

    public void setPcuatroletras(int pcuatroletras) {
        Pcuatroletras = pcuatroletras;
    }

    public int getPmasdecinco() {
        return Pmasdecinco;
    }

    public void setPmasdecinco(int pmasdecinco) {
        Pmasdecinco = pmasdecinco;
    }

    public int getPescalera() {
        return Pescalera;
    }

    public void setPescalera(int pescalera) {
        Pescalera = pescalera;
    }

    public int getPinicial() {
        return Pinicial;
    }

    public void setPinicial(int pinicial) {
        Pinicial = pinicial;
    }

    public int getPtodas() {
        return Ptodas;
    }

    public void setPtodas(int ptodas) {
        Ptodas = ptodas;
    }

    public int getPcantidad() {
        return Pcantidad;
    }

    public void setPcantidad(int pcantidad) {
        Pcantidad = pcantidad;
    }

    public int getPyacht() {
        return Pyacht;
    }

    public void setPyacht(int pyacht) {
        Pyacht = pyacht;
    }

    public String Nombre(){
        return nombre;
    }


}
