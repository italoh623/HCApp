package me.aflak.heroicuidador.model;

public class Atividade implements Comparable<Atividade> {

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
}
