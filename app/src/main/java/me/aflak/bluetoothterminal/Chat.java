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

import me.aflak.bluetooth.Bluetooth;

public class Chat extends AppCompatActivity implements Bluetooth.CommunicationCallback {
    private String name;
    private Bluetooth b;
    private EditText message;
    private Button iniciar;
    private Button finalizar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        message = (EditText) findViewById(R.id.message);
        iniciar = (Button) findViewById(R.id.iniciar);
        finalizar = (Button) findViewById(R.id.finalizar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        text.setMovementMethod(new ScrollingMovementMethod());
        iniciar.setEnabled(false);
        finalizar.setEnabled(false);

        b = new Bluetooth(this);
        b.enableBluetooth();

        b.setCommunicationCallback(this);

        int pos = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(pos).getName();

        Display("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(pos));

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String msg = message.getText().toString();
                String msg = "INICIAR";
                message.setText("");
                b.send(msg);
                Display("You: " + msg);
            }
        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String msg = message.getText().toString();
                String msg = "FINALIZAR";
                message.setText("");
                b.send(msg);
                Display("You: " + msg);
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
                text.append(s + "\n");
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onConnect(BluetoothDevice device) {
        Display("Connected to " + device.getName() + " - " + device.getAddress());
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
        Display(name+"/"+message+"/");

     /*  if(message.equals("Olá")){
            Display(name+": "+message);
        }
        else{
            if(message.equals("Tchau")){
                Display(name+": "+"Bye");
            }
        }*/
       /*
        i=Integer.parseInt(message);

        Display(name+": "+  Integer.toString(i));
        */
/*
       if(contador<100){
           i=Integer.parseInt(message);
           if(contador==0){
            valor_inicial=i;
           }
           Display(name+": "+  Integer.toString(i));
           soma+=i;

       }else{
           if(contador==100){
               i=Integer.parseInt(message);
               soma+=i;
               valor_final=i;
               media=soma/contador;
               Display(name+"Media: "+  Double.toString(media));
               Display(name+"Valor inicial: "+  Integer.toString(valor_inicial));
               Display(name+"Valor final: "+  Integer.toString(valor_final));
           }
           else{

           }


       }
        contador++;*/


        // i=Integer.parseInt(message);

     /*
if(message.length()>3){
    int tamanho = message.length();
    String codigo=  message.substring(0, 3);

    Display(name+"/"+codigo+"/");
    if(codigo.equals("NUM")){
        Display(name+": NUMERO");
    }
    else{
        if(codigo.equals("MSG")){
            Display(name+": MENSAGEM");
        }
    }

   // Display(name+": "+  message.substring(3, tamanho));
}
*/
        //Display(name+": "+  message);
/*
        if (message.substring(0, 2) == "NUM") {
            if (contador < 100) {
                i = Integer.parseInt(message.substring(2, message.length()));
                if (contador == 0) {
                    valor_inicial = i;
                }
                Display(name + ": " + Integer.toString(i));
                soma += i;

            } else {
                if (contador == 100) {
                    i = Integer.parseInt(message);
                    soma += i;
                    valor_final = i;
                    media = soma / contador;
                    Display(name + "Media: " + Double.toString(media));
                    Display(name + "Valor inicial: " + Integer.toString(valor_inicial));
                    Display(name + "Valor final: " + Integer.toString(valor_final));
                } else {

                }


            }
            contador++;
        }
        else{
            if (message.substring(0, 2) == "MSG") {
                Display(name + "Valor recebido não é um número");
            }
            else{
                Display(name + "nÃO RECEBI MENSAGEM");

            }

        }*/
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
