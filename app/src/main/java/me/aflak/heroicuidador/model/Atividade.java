package me.aflak.heroicuidador.model;

public class Atividade {

    private String horario;
    private String nome;
    private Boolean concluida;
    private Boolean movimentoCorreto;

    public Atividade() {
        this.concluida = false;
        this.movimentoCorreto = false;
    }

    public Atividade(String horario, String nome) {
        this.horario = horario;
        this.nome = nome;
        this.concluida = false;
        this.movimentoCorreto = false;
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

    public Boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Boolean isMovimentoCorreto() {
        return movimentoCorreto;
    }

    public void setMovimentoCorreto(Boolean movimentoCorreto) {
        this.movimentoCorreto = movimentoCorreto;
    }
}
