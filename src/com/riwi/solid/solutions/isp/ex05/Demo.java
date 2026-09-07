package com.riwi.solid.solutions.isp.ex05;

/**
 * SOLUCIÓN ISP 05 — Dispositivo multimedia
 *
 * MediaDevice obligaba a todo aparato a reproducir audio, reproducir video y
 * grabar. Un reproductor de mp3 solo hace lo primero.
 */
public final class Demo {

    private Demo() {
    }

    public static void run() {
        System.out.println("== ISP 05 — Dispositivos multimedia ==");
        Mp3Player mp3 = new Mp3Player();
        Smartphone phone = new Smartphone();

        // Escuchar música funciona con los dos.
        listenMusic(mp3);
        listenMusic(phone);

        // Video y grabación se le piden solo a quien puede.
        phone.playVideo();
        phone.record();
    }

    static void listenMusic(AudioPlayer player) {
        player.playAudio();
    }

    public static void main(String[] args) {
        run();
    }
}

interface AudioPlayer {
    void playAudio();
}

interface VideoPlayer {
    void playVideo();
}

interface Recorder {
    void record();
}

class Mp3Player implements AudioPlayer {
    @Override public void playAudio() { System.out.println("Reproduciendo audio"); }
}

class Smartphone implements AudioPlayer, VideoPlayer, Recorder {
    @Override public void playAudio() { System.out.println("Reproduciendo audio (celular)"); }
    @Override public void playVideo() { System.out.println("Reproduciendo video"); }
    @Override public void record() { System.out.println("Grabando"); }
}
