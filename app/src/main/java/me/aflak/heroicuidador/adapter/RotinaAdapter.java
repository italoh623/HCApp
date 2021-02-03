package me.aflak.heroicuidador.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.dialog.AlertaMovimentoDialog;
import me.aflak.heroicuidador.model.Atividade;

public class RotinaAdapter extends RecyclerView.Adapter<RotinaAdapter.MyViewHolder> {

    private List<Atividade> listaAtividades;
    private FragmentManager fragmentManager;

    public RotinaAdapter(List<Atividade> listaAtividades, FragmentManager fragmentManager) {
        this.listaAtividades = listaAtividades;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View atividade = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rotina_detalhe, viewGroup, false);

        return new MyViewHolder(atividade);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        final Atividade atividade = listaAtividades.get(i);

        myViewHolder.textViewNome.setText(atividade.getNome());
        myViewHolder.textViewHora.setText(atividade.getHorario());

        if (atividade.isConcluida()) {
            myViewHolder.buttonCheck.setBackgroundResource(R.drawable.ic_check_box_marcado_24);
        } else {
            myViewHolder.buttonCheck.setBackgroundResource(R.drawable.ic_check_box_desmarcada_24);
        }

        if (!atividade.isMovimentoCorreto()) {
            myViewHolder.buttonAlerta.setVisibility(View.VISIBLE);
        }

        myViewHolder.buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atividade.isConcluida()) {

                    myViewHolder.buttonCheck.setBackgroundResource(R.drawable.ic_check_box_desmarcada_24);
                    atividade.setConcluida(false);

                } else {

                    myViewHolder.buttonCheck.setBackgroundResource(R.drawable.ic_check_box_marcado_24);
                    atividade.setConcluida(true);

                }
            }
        });


        myViewHolder.buttonAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(atividade);
            }
        });

    }

    public void openDialog(Atividade atividade) {
        AlertaMovimentoDialog dialog = new AlertaMovimentoDialog();

        Bundle bundle  = new Bundle();
        bundle.putParcelable("atividade", atividade);
        dialog.setArguments(bundle);

        dialog.show(fragmentManager, "Movimento");
    }

    @Override
    public int getItemCount() {
        return listaAtividades.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewHora;
        private TextView textViewNome;
        private Button buttonCheck;
        private Button buttonAlerta;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            buttonCheck = itemView.findViewById(R.id.buttonCheck);
            buttonAlerta = itemView.findViewById(R.id.buttonAlerta);

        }

    }
}
