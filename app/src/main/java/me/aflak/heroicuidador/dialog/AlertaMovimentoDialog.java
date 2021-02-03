package me.aflak.heroicuidador.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.model.Atividade;

public class AlertaMovimentoDialog extends AppCompatDialogFragment {

    private TextView textViewInfo;
    private Atividade atividade;
    private AlertaMovimentoDialog.AlertaMovimentoDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        atividade = getArguments().getParcelable("atividade");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alerta_movimento_dialog, null);

        builder.setView(view)
                .setTitle(atividade.getNome())
                .setNegativeButton("Ainda não entendi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Entendi!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.resetarAtividade(atividade);
                    }
                });

        textViewInfo = view.findViewById(R.id.textViewInfo);
        textViewInfo.setText(atividade.getTextoInformativo());

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AlertaMovimentoDialog.AlertaMovimentoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AlertaMovimentoDialogListener");
        }
    }


    public interface AlertaMovimentoDialogListener {
        void resetarAtividade(Atividade atividade);
    }
}
