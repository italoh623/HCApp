package me.aflak.heroicuidador.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;

import me.aflak.heroicuidador.R;

public class RotinaAdapter extends RecyclerView.Adapter<RotinaAdapter.MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View atividade = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rotina_detalhe, viewGroup, false);

        return new MyViewHolder(atividade);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.textViewNome.setText("Nome da Atividade");
        myViewHolder.textViewHora.setText("10:00");
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewHora;
        private TextView textViewNome;
        private Button buttonCheck;
        private Button buttonAlerta;

        private boolean realizada;
        private boolean movimentoCorreto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHora = itemView.findViewById(R.id.textViewHora);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            buttonCheck = itemView.findViewById(R.id.buttonCheck);
            buttonAlerta = itemView.findViewById(R.id.buttonAlerta);

            realizada = false;

            buttonCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (realizada) {

                        buttonCheck.setBackgroundResource(R.drawable.ic_check_box_desmarcada_24);

                        realizada = false;
                    } else {
                        buttonCheck.setBackgroundResource(R.drawable.ic_check_box_marcado_24);

                        realizada = true;
                    }
                }
            });
        }

    }
}
