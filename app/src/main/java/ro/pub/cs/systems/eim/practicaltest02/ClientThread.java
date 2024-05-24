package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// PAS 3.1: Clasa pentru thread-ul Client
// BufferedReader, PrintWriter
public class ClientThread extends Thread {
    // Elementele clientului
    private final String address;
    private final int port;
    private final String city;
    private final String informationType;
    private final TextView weatherForecastTextView;

    // Constructor
    public ClientThread(String address, int port, String city, String informationType, TextView weatherForecastTextView) {
        this.address = address;
        this.port = port;
        this.city = city;
        this.informationType = informationType;
        this.weatherForecastTextView = weatherForecastTextView;
    }

    // Socket pentru Client
    private Socket socket;

    // Realizare comunicarea cu serverul
    @Override
    public void run() {
        try {
            // Socket-ul pentru Client primeste adresa si portul
            socket = new Socket(address, port);

            // Operatiile de scriere si citire pe canalul de comunicatie
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            // Trimiterea catre server (prin scriere pe canalul de comunicatie) a parametrilor pentru furnizarea informatiilor
            printWriter.println(city);
            printWriter.flush();
            printWriter.println(informationType);
            printWriter.flush();

            // Primirea de la server (prin citire de pe canalul de comunicatie) a liniilor distincte continand datele cerute
            String weatherInformation;
            while ((weatherInformation = bufferedReader.readLine()) != null) {
                final String finalizedWeatherInformation = weatherInformation;

                // Vizualizarea datelor intr-un obiect de tip TextView -> accesul la controlul grafic se obtine cu post()
                weatherForecastTextView.post(() -> weatherForecastTextView.setText(finalizedWeatherInformation));
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
        } finally {
            // Inchidere canal de comunicatie
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                }
            }
        }
    }
}
