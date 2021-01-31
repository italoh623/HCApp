package me.aflak.heroicuidador.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.heroicuidador.R;

public class OperacaoActivity extends AppCompatActivity implements Bluetooth.CommunicationCallback {

    // Bluetooth
    private String name;
    private Bluetooth b;
    private boolean registered = false;

    private TextView textViewLogsComunicacao;
    private Button buttonResetar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacao);

        textViewLogsComunicacao = findViewById(R.id.textViewLogsComunicacao);
        buttonResetar = findViewById(R.id.buttonResetar);

        // Bluetooth

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        final int position = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(position).getName();

        Toast.makeText(getApplicationContext(),  "Conectando...", Toast.LENGTH_SHORT).show();
        Display("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(position));

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;


        buttonResetar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.send("resetar");
            }
        });

    }

    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Conectado " + device.getName() + " - " + device.getAddress());
//        Toast.makeText(getApplicationContext(),  "Connected", Toast.LENGTH_SHORT).show();

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.send("operacao");
            }
        });
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display("Connecting again...");
        b.connectToDevice(device);
    }

    @Override
    public void onBackPressed() {
        b.removeCommunicationCallback();
        b.disconnect();

        unregisterReceiver(mReceiver);

        Intent intent = new Intent(this, HomeActivity.class);
        int position = getIntent().getExtras().getInt("pos");
        intent.putExtra("pos", position);
        startActivity(intent);

        finish();
    }


    @Override
    public void onMessage(String message) {
        Display(message);
    }

    @Override
    public void onError(String message) {
        Display("Error: " + message);
    }


    @Override
    public void onConnectError(final BluetoothDevice device, String message) {
        Display("Error: " + message);
        Display("Trying again in 3 sec.");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Tentando conectar");
                        b.connectToDevice(device);
                    }
                }, 2000);
            }
        });
    }

    public void Display(final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewLogsComunicacao.setText(s);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(OperacaoActivity.this, Select.class);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered = false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered = false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };
}