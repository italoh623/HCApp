package me.aflak.bluetoothterminal;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import me.aflak.bluetooth.Bluetooth;

public class Chat extends AppCompatActivity implements Bluetooth.CommunicationCallback {
    private String name;
    private Bluetooth b;
   // private EditText message;
    private Button iniciar;
    private Button finalizar;
    private Button finalizar_movimento;
    private Button iniciar_movimento;
    private Button finalizar_calibracao;
    private TextView text;
    private TextView textView_movimento;
    private ScrollView scrollView;
    private boolean registered = false;
    int soma = 0;
    int valor_inicial = 0;
    int valor_final = 0;
    double media = 0;
    int contador = 0;
    int i = 0;

    // Calibração
    float valores_calibracao[];
    int contador_calibracao = 0;
    float valor_calibrado = 0;
    float valor_atual_calibracao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        textView_movimento = (TextView) findViewById(R.id.textView_movimento);
       // message = (EditText) findViewById(R.id.message);
        iniciar = (Button) findViewById(R.id.iniciar);
        finalizar = (Button) findViewById(R.id.finalizar);
        finalizar_movimento = (Button) findViewById(R.id.finalizar_movimento);
        iniciar_movimento = (Button) findViewById(R.id.iniciar_movimento);
        finalizar_calibracao = (Button) findViewById(R.id.finalizar_calibracao);
       // scrollView = (ScrollView) findViewById(R.id.scrollView);

//        text.setMovementMethod(new ScrollingMovementMethod());
        iniciar.setEnabled(false);
        finalizar.setEnabled(false);

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        int pos = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(pos).getName();
        Toast.makeText(getApplicationContext(),  "Conectando...", Toast.LENGTH_SHORT).show();
      //  Display("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(pos));

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String msg = message.getText().toString();
                String msg = "operacao";
               // message.setText("");
                b.send(msg);
               // Display("You: " + msg);
            }
        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String msg = message.getText().toString();
                String msg = "calibracao";
               // message.setText("");
                b.send(msg);
              //  Display("You: " + msg);
            }
        });
        finalizar_movimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valores_calibracao[contador_calibracao] = valor_atual_calibracao;
                b.send("proxima_calibracao");
            }
        });
        iniciar_movimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador_calibracao++;
                b.send("iniciar_calibracao");
            }
        });
        finalizar_calibracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor_calibrado = 0;
                for(int i=0; i<10; i++) {
                    valor_calibrado += valores_calibracao[i];
                }

                valor_calibrado = valor_calibrado/10;

                Display(Float.toString(valor_calibrado));
                b.send("fim_calibracao");
            }
        });


        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registered) {
            unregisterReceiver(mReceiver);
            registered = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.close:
                b.removeCommunicationCallback();
                b.disconnect();
                Intent intent = new Intent(this, Select.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.rate:
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Display(final String s) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView_movimento.setText(s);
                //text.append(s + "\n");
                //scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onConnect(BluetoothDevice device) {
       // Display("Conectado " + device.getName() + " - " + device.getAddress());
       // Toast.makeText(getApplicationContext(),  "Connected", Toast.LENGTH_SHORT).show();

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iniciar.setEnabled(true);
                finalizar.setEnabled(true);
            }
        });
    }
    public void onBackPressed(){
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
    public void onMessage(String message) {
        String codigo = message.substring(0, 3);
        System.out.println(codigo);

        if (codigo.equals("CAL")) {
            Float valor = Float.parseFloat(message.substring(3));
            valor_atual_calibracao = valor;
            Display(message);
        }
        else {
            Display(message);
        }
    }
    @Override
    public void onError(String message) {
        Display("Error: "+message);
    }

    @Override
    public void onConnectError(final BluetoothDevice device, String message) {
        Display("Error: "+message);
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
                Intent intent1 = new Intent(Chat.this, Select.class);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };
}