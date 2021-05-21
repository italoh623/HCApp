package me.aflak.heroicuidador.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.model.Atividade;

public class AlertaMovimentoDialog extends AppCompatDialogFragment {

    private TextView textViewInfo;
    private Atividade atividade;
    private VideoView videoView;
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
                        if (videoView != null) {
                            videoView.stopPlayback();
                        }
                    }
                })
                .setPositiveButton("Entendi!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoView.stopPlayback();
                        videoView = null;

                        listener.resetarAtividade(atividade);
                    }
                });

        textViewInfo = view.findViewById(R.id.textViewInfo);
        videoView = view.findViewById(R.id.videoView);

        textViewInfo.setText(atividade.getTextoInformativo());

//        videoView.setMediaController(new MediaController(view.getContext()));
        videoView.setVideoPath("android.resource://" + view.getContext().getPackageName() + "/" + R.raw.videoplayback);
        videoView.start();

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
