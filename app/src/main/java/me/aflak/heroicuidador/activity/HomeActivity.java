package me.aflak.heroicuidador.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.adapter.RotinaAdapter;
import me.aflak.heroicuidador.dialog.AddAtividadeDialog;
import me.aflak.heroicuidador.dialog.AlertaMovimentoDialog;
import me.aflak.heroicuidador.model.Atividade;
import me.aflak.heroicuidador.model.ExtraUtils;

public class HomeActivity extends AppCompatActivity implements Bluetooth.CommunicationCallback, AddAtividadeDialog.AddAtividadeDialogListener, AlertaMovimentoDialog.AlertaMovimentoDialogListener {

    // Bluetooth
    private String name;
    private Bluetooth b;
    private boolean registered = false;

    private RecyclerView recyclerView;
    private Button buttonAddAtividade;

    // Navegação
    private TextView textViewOperacao;
    private TextView textViewCalibracao;
    private TextView textViewResultados;

    // Lista de atividades
    private List<Atividade> atividades;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewOperacao = findViewById(R.id.textViewOperacao);
        textViewCalibracao = findViewById(R.id.textViewCalibracao);
        textViewResultados = findViewById(R.id.textViewResultados);


        recyclerView = findViewById(R.id.recyclerView);
        buttonAddAtividade = findViewById(R.id.buttonAddAtividade);


        // Bluetooth

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        final int position = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(position).getName();

        Toast.makeText(getApplicationContext(),  "Conectando...", Toast.LENGTH_SHORT).show();
        b.connectToDevice(b.getPairedDevices().get(position));

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;

        // Navegação

        textViewCalibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.removeCommunicationCallback();
                b.disconnect();

                Intent intent = new Intent(getApplicationContext(), ApresentacaoCalibracaoActivity.class);
                intent.putExtra("pos", position);
                intent.putParcelableArrayListExtra("atividades", new ArrayList<Atividade>(atividades));

                startActivity(intent);
            }
        });

        textViewOperacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.removeCommunicationCallback();
                b.disconnect();

                Intent intent = new Intent(getApplicationContext(), OperacaoActivity.class);
                intent.putExtra("pos", position);
                intent.putParcelableArrayListExtra("atividades", new ArrayList<Atividade>(atividades));

                startActivity(intent);
            }
        });

        textViewResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.removeCommunicationCallback();
                b.disconnect();

                Intent intent = new Intent(getApplicationContext(), ResultadosActivity.class);
                intent.putExtra("pos", position);
                intent.putParcelableArrayListExtra("atividades", new ArrayList<Atividade>(atividades));

                startActivity(intent);
            }
        });


        buttonAddAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        // RecyclerView Layout

        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {

            System.out.println("Oiiiii");
            atividades = savedInstanceState.getParcelableArrayList("atividades");

        } else {
            if (getIntent().getExtras().getParcelableArrayList("atividades") != null) {

                atividades = getIntent().getExtras().getParcelableArrayList("atividades");


            } else {
                System.out.println("Tchauu");
                atividades = new ArrayList<>();
                this.prepararAtividades();
                Collections.sort(atividades);
            }
        }

        RotinaAdapter adapter = new RotinaAdapter(atividades, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("Ihuuu");
        outState.putParcelableArrayList("atividades", new ArrayList<Atividade>(atividades));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registered) {
            unregisterReceiver(mReceiver);
            registered = false;
        }
    }

    public void prepararAtividades() {
        Atividade atividade = new Atividade("8:00","Banho");
        atividade.setMovimentoCorreto(false);
        atividade.setConcluida(true);
        this.atividades.add(atividade);

        atividade = new Atividade("9:00","Café da Manhã");
        this.atividades.add(atividade);

        atividade = new Atividade("10:00","Caminhar");
        this.atividades.add(atividade);

        atividade = new Atividade("12:00","Almoço");
        this.atividades.add(atividade);

        atividade = new Atividade("14:00","Dormir");
        this.atividades.add(atividade);

        atividade = new Atividade("19:00","Jantar");
        this.atividades.add(atividade);

        atividade = new Atividade("22:30","Dormir");
        this.atividades.add(atividade);
    }


    public void Display(final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                textViewLogs.setText(s);
            }
        });
    }

    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Conectado " + device.getName() + " - " + device.getAddress());

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.send("operacao");
            }
        });
    }

    @Override
    public void onBackPressed() {
        b.removeCommunicationCallback();
        b.disconnect();
        Intent intent = new Intent(this, Select.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display("Connecting again...");
        b.connectToDevice(device);
    }

    @Override
    public void onMessage(final String message) {

        // Troca de mensagem

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String horaAtual = dateFormat.format(date).toString();

        String codigo = message.substring(0, 3);

        if (codigo.equals("MOV")) {
            String movimento = message.substring(3);
            System.out.println(movimento);

            if (movimento.equals("incorreto")) {
                Display("Movimento Incorreto");
                for(Atividade atividade : atividades){
                    if (ExtraUtils.isMesmoHorario(atividade.getHorario(), horaAtual)) {

                        atividade.setConcluida(true);
                        atividade.setMovimentoCorreto(false);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                // Stuff that updates the UI
                                RotinaAdapter adapter = new RotinaAdapter(atividades, getSupportFragmentManager());
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    }
                }
            } else if (movimento.equals("analise")) {
                Display("Analisando Movimento");
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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(HomeActivity.this, Select.class);

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

    // Adicionar nova atividade

    public void openDialog() {
        AddAtividadeDialog dialog = new AddAtividadeDialog();
        dialog.show(getSupportFragmentManager(), "Add Atividade Dialog");
    }

    @Override
    public void addAtividade(String horario, String atividade) {
        atividades.add(new Atividade(horario, atividade));
        Collections.sort(atividades);

        RotinaAdapter adapter = new RotinaAdapter(atividades, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void resetarAtividade(Atividade atividade) {

        atividades.remove(atividade);

        Atividade novaAtividade = new Atividade(atividade.getHorario(), atividade.getNome());
        atividades.add(novaAtividade);

        Collections.sort(atividades);


        RotinaAdapter adapter = new RotinaAdapter(atividades, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }
}