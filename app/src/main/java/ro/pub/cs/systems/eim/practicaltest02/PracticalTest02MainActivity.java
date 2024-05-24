package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02MainActivity extends AppCompatActivity {

    // PAS 1.1: Initializare serverThread, clientThread, toate EditText-urile, Spinner-ul si TextView-ul
    private ServerThread serverThread = null;
    private ClientThread clientThread = null;
    private EditText serverPortEditText = null;
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText cityEditText =  null;
    private Spinner informationTypeSpinner = null;
    private TextView weatherForecastTextView =  null;

    // PAS 1.3: Initializare listeneri pentru butoane
    private final ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();

    private final GetWeatherForecastButtonClickListener getWeatherForecastButtonClickListener = new GetWeatherForecastButtonClickListener();

    // PAS 2
    // Creare conexiune Server -> new ServerThread(serverPort); serverThread.start();
    // (serverPort)
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Dupa ce am implementat ServerThread (CommunicationThread not yet), revin aici!
            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            serverThread.start();
        }
    }

    // PAS 3
    // Creare conexiune client -> new ClientThread(...), clientThread.start();
    // (clientAddress, clientPort)
    // city, informationType
    private class GetWeatherForecastButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort = clientPortEditText.getText().toString();
            if (clientAddress.isEmpty() || clientPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (serverThread == null || !serverThread.isAlive()) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }

            String city = cityEditText.getText().toString();
            if (city.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            String informationType = informationTypeSpinner.getSelectedItem().toString();
            if (informationType.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (information type) should be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            weatherForecastTextView.setText("");

            // Dupa ce am implementat ClientThread (CommunicationThread not yet), revin aici!
            clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), city, informationType, weatherForecastTextView);
            clientThread.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practical_test02_main);

        // PAS 1.2: Creare elemente vizuale
        serverPortEditText = findViewById(R.id.serverPortEditText);
        clientAddressEditText = findViewById(R.id.clientAddressEditText);
        clientPortEditText = findViewById(R.id.clientPortEditText);
        cityEditText = findViewById(R.id.cityEditText);
        informationTypeSpinner = findViewById(R.id.infoTypeSpinner);
        weatherForecastTextView = findViewById(R.id.weatherForecastTextView);
        // + butoane
        Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(connectButtonClickListener);

        Button getWeatherForecastButton = findViewById(R.id.getWeatherForecastButton);
        getWeatherForecastButton.setOnClickListener(getWeatherForecastButtonClickListener);
    }

    // PAS 2.3: Oprire serverThread
    @Override
    protected void onDestroy() {
        Log.i(Constants.TAG, "[MAIN ACTIVITY] onDestroy() callback method has been invoked");
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
