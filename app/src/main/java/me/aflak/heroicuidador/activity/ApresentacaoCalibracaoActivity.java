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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.model.Atividade;
import me.aflak.heroicuidador.model.ExtraUtils;

public class ApresentacaoCalibracaoActivity extends AppCompatActivity implements Bluetooth.CommunicationCallback {

    // Bluetooth
    private String name;
    private Bluetooth b;
    private boolean registered = false;

    private Button buttonStartCalibracao;

    private List<Atividade> atividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao_calibracao);

        buttonStartCalibracao = findViewById(R.id.buttonStartCalibracao);


        // Bluetooth

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        final int position = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(position).getName();

        atividades = getIntent().getExtras().getParcelableArrayList("atividades");

        b.connectToDevice(b.getPairedDevices().get(position));

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;



        buttonStartCalibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.removeCommunicationCallback();
                b.disconnect();

                Intent intent = new Intent(getApplicationContext(), CalibracaoActivity.class);
                intent.putExtra("pos", position);
                intent.putParcelableArrayListExtra("atividades", new ArrayList<Atividade>(atividades));

                startActivity(intent);
            }
        });
    }

    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Conectado " + device.getName() + " - " + device.getAddress());

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonStartCalibracao.setEnabled(true);
            }
        });
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display("Connecting again...");

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonStartCalibracao.setEnabled(false);
            }
        });

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
        intent.putParcelableArrayListExtra("atividades", new ArrayList<Atividade>(atividades));
        startActivity(intent);

        finish();
    }

    @Override
    public void onMessage(String message) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String horaAtual = dateFormat.format(date).toString();

        String codigo = message.substring(0, 3);

        if (codigo.equals("MOV")) {
            String movimento = message.substring(3);
            System.out.println(movimento);

            if (movimento.equals("incorreto")) {

                for(Atividade atividade : atividades){
                    if (ExtraUtils.isMesmoHorario(atividade.getHorario(), horaAtual)) {

                        atividade.setConcluida(true);
                        atividade.setMovimentoCorreto(false);

                    }
                }
            }
        } else {
            Display(message);
        }
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
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(ApresentacaoCalibracaoActivity.this, Select.class);

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