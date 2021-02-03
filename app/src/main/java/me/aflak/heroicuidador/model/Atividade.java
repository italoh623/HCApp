package me.aflak.heroicuidador.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

public class Atividade implements Comparable<Atividade>, Parcelable {

    private String horario;
    private String nome;
    private boolean concluida;
    private boolean movimentoCorreto;

    public Atividade() {
        this.concluida = false;
        this.movimentoCorreto = true;
    }

    public Atividade(String horario, String nome) {
        this.horario = horario;
        this.nome = nome;
        this.concluida = false;
        this.movimentoCorreto = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Atividade(Parcel in) {
        this.horario = in.readString();
        this.nome = in.readString();
        this.concluida = in.readBoolean();
        this.movimentoCorreto = in.readBoolean();
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public boolean isMovimentoCorreto() {
        return movimentoCorreto;
    }

    public void setMovimentoCorreto(boolean movimentoCorreto) {
        this.movimentoCorreto = movimentoCorreto;
    }

    @Override
    public int compareTo(Atividade obj) {
        return this.horario.compareTo(obj.getHorario());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horario);
        dest.writeString(nome);
        dest.writeBoolean(concluida);
        dest.writeBoolean(movimentoCorreto);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Atividade createFromParcel(Parcel in) {
            return new Atividade(in);
        }

        public Atividade[] newArray(int size) {
            return new Atividade[size];
        }
    };
}
