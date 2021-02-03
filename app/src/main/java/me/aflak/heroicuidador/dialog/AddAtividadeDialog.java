package me.aflak.heroicuidador.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import me.aflak.heroicuidador.R;

public class AddAtividadeDialog extends AppCompatDialogFragment{

    private TextView textViewHorario;
    private Spinner spinnerAtividade;
    private AddAtividadeDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_atividade_dialog, null);

        builder.setView(view)
                .setTitle("Adicionar nova atividade")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String horario = textViewHorario.getText().toString();
                        String nomeAtividade = spinnerAtividade.getSelectedItem().toString();

                        listener.addAtividade(horario, nomeAtividade);
                    }
                });

        textViewHorario = view.findViewById(R.id.textViewHorario);
        spinnerAtividade = view.findViewById(R.id.spinnerAtividade);

        textViewHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                String hora = hourOfDay + "";
                                String minuto = minute + "";

                                if (hourOfDay < 10) {
                                    hora = "0" + hora;
                                }

                                if (minute < 10) {
                                    minuto = "0" + minuto;
                                }

                                textViewHorario.setText(hora + ":" + minuto);
                            }
                        }, hour, minute, DateFormat.is24HourFormat(getActivity())
                );

                timePickerDialog.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.text_atividades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAtividade.setAdapter(adapter);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddAtividadeDialogListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + " must implement AddAtividadeDialogListener");
        }
    }


    public interface AddAtividadeDialogListener {
        void addAtividade(String horario, String atividade);
    }
}

