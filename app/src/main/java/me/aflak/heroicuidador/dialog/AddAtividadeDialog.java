package me.aflak.heroicuidador.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import me.aflak.heroicuidador.R;

public class AddAtividadeDialog extends AppCompatDialogFragment {

    private EditText editTextHorario;
    private EditText editTextAtividade;
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
                        String horario = editTextHorario.getText().toString();
                        String nomeAtividade = editTextAtividade.getText().toString();

                        listener.applyTexts(horario, nomeAtividade);
                    }
                });

        editTextHorario = view.findViewById(R.id.editTextHorario);
        editTextAtividade = view.findViewById(R.id.editTextAtividade);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddAtividadeDialogListener) context;
        } catch (ClassCastException e) {
           throw new ClassCastException(context.toString() + "must implement AddAtividadeDialogListener");
        }
    }

    public interface AddAtividadeDialogListener {

        void applyTexts(String horario, String atividade);
    }
}

