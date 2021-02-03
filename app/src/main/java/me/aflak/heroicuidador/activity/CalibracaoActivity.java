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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.adapter.RotinaAdapter;
import me.aflak.heroicuidador.model.Atividade;

public class CalibracaoActivity extends AppCompatActivity implements Bluetooth.CommunicationCallback {


    // Calibração
    int soma = 0;
    int valor_inicial = 0;
    int valor_final = 0;
    double media = 0;
    int contador = 0;
    int i = 0;
    float valores_calibracao[]=new float[10];
    int contador_calibracao = 0;
    float valor_calibrado = 0;
    float valor_atual_calibracao = 0;

    // Bluetooth
    private String name;
    private Bluetooth b;
    private boolean registered = false;
    //Text views
    private TextView textView_calibracao;

    //Buttons
    private Button finalizar_calibracao;
    private Button iniciar_movimento;
    private Button finalizar_movimento;


    private List<Atividade> atividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibracao);

        // Bluetooth
        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        final int position = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(position).getName();

        atividades = getIntent().getExtras().getParcelableArrayList("atividades");

        Toast.makeText(getApplicationContext(),  "Conectando...", Toast.LENGTH_SHORT).show();
        //  Display("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(position));

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;


        iniciar_movimento = (Button) findViewById(R.id.iniciar_movimento);
        finalizar_movimento = (Button) findViewById(R.id.iniciar_movimento);
        finalizar_calibracao = (Button) findViewById(R.id.finalizar_calibracao);
        textView_calibracao = findViewById(R.id.textView_calibracao);



        iniciar_movimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    contador_calibracao++;
                b.send("iniciar_movimento");
            }
        });
        finalizar_movimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.send("finalizar_movimento");

                if(contador_calibracao<=10){
                    //Display(Integer.toString(contador_calibracao));
                    valores_calibracao[contador_calibracao-1] = valor_atual_calibracao;
                    System.out.println("Valor de calibracao["+(contador_calibracao-1)+"] ="+valor_atual_calibracao);
                 //   b.send("proxima_calibracao");
                }else if(contador_calibracao==11){
                    valores_calibracao[0] = valor_atual_calibracao;
                    finalizar_movimento.setVisibility(View.INVISIBLE);
                    iniciar_movimento.setVisibility(View.INVISIBLE);
                    finalizar_calibracao.setVisibility(View.VISIBLE);
                  //  b.send("proxima_calibracao");
                }else{

                }
            }
        });

        finalizar_calibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.send("finalizar_calibracao");
                valor_calibrado = 0;
                System.out.print("Valores: ");
                for(int i=0; i<10; i++) {
                    System.out.print(valores_calibracao[i]+" ");
                    valor_calibrado += valores_calibracao[i];
                }
                System.out.println();
                valor_calibrado = valor_calibrado/10;

               // b.send("fim_calibracao");
               // Display(Float.toString(valor_calibrado));
                finalizar_calibracao.setVisibility(View.INVISIBLE);

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
                b.send("calibracao");
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

        Intent intent = new Intent(this, ApresentacaoCalibracaoActivity.class);
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
                    if (isMesmoHorario(atividade.getHorario(), horaAtual)) {

                        atividade.setConcluida(true);
                        atividade.setMovimentoCorreto(false);

                    }
                }
            }
        } else {
            Display(message);
        }
    }

    public boolean isMesmoHorario(String horaA, String horaB) {

        String temposA[] = horaA.split(":");
        String temposB[] = horaB.split(":");

        if (temposA[0].equals(temposB[0])) {

            Integer segundosA = new Integer(temposA[1]);
            Integer segundosB = new Integer(temposB[1]);

            if (segundosA - 3 <= segundosB && segundosA + 3 >= segundosB ) {
                return true;
            }
        }

        return false;
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
                textView_calibracao.setText(s);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(CalibracaoActivity.this, Select.class);

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