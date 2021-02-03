package me.aflak.heroicuidador.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import me.aflak.heroicuidador.R;

public class Atividade implements Comparable<Atividade>, Parcelable {

    private String horario;
    private String nome;
    private String textoInformativo;
    private boolean concluida;
    private boolean movimentoCorreto;

    private static final String[] informacoes = {
            "Caminhar:Essa é a atividade mais importante para o idoso, aqui ele vai deixar a cama ou a cadeira para realizar uma atividade física, mas também é a atividade que tem um risco muito elevado de queda quando o idoso tem limitações do movimento, por isso esteja sempre perto e atento. Caso o idoso use andador, apenas acompanhe sempre estando perto e de preferência com uma mão no braço dele por segurança para evitar quedas. Para ajudar um idoso sem andador passe seu braço de apoio por debaixo das axilas dele, e peça para ele segurar em você, em seguida com o seu outro braço segure o bíceps do idoso. Lembre-se de manter a coluna ereta, e na medida do possível deixar seus ombros alinhados, caso a caminhada seja por muito tempo, recomenda-se trocar de braço para não forçar apenas um lado do seu corpo.",
            "Banho:Essa atividade precisa ter muita atenção, pois o risco de queda é muito maior, tanto do cuidador quanto do idoso. Na medida do possível é recomendado a adaptação do local de banho, com barras de segurança, evitar box, tapetes anti-derrapantes e cadeira de banho. A transferência para a cadeira de banho, faça num local seco, para evitar sua queda. Também, lembre-se de deixar a coluna ereta e flexionar os joelhos, pois você levantará o idoso com a extensão dos joelhos obtendo assim mais força. Durante o banho não se abaixe sem flexionar os joelhos, e também use chinelos para aumentar a segurança. Para tirar o idoso da cadeira de banho, fique fora da área molhada na medida do possível, lembrando da flexão dos joelhos e da coluna ereta.",
            "Jantar",
            "Café da Manhã",
            "Almoço",
            "Dormir:Essa atividade é a que o cuidador exerce maior esforço, então o cuidador precisa ter cuidado no seu posicionamento. Quando o idoso tem a oportunidade de ter uma cama hospitalar, vai diminuir seu esforço, então sempre que possível use-a. Na impossibilidade, siga os próximos passos para evitar o maior esforço. Primeiro, para colocar o idoso na cama, deixe ele perto da beira da cama, se agache com a coluna ereta até você ficar confortável em segurá-lo pelas axilas. Em seguida levante-o, aplicando força no braço e estendendo seu joelho. Com isso, você evitará curvar sua coluna, diminuindo sua carga na lombar, ao colocar na cama, desça com idoso seu curvar sua coluna, deixando sempre ereta. Para retirá-lo, deixe o idoso sentado na cama e refaça o movimento para a cadeira, sempre flexionando o joelho e deixando a coluna ereta."
    };

    public Atividade() {
        this.concluida = false;
        this.movimentoCorreto = true;
    }

    public Atividade(String horario, String nome) {
        this.horario = horario;
        this.nome = nome;
        setTextoInformativo(nome);
        this.concluida = false;
        this.movimentoCorreto = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Atividade(Parcel in) {
        this.horario = in.readString();
        this.nome = in.readString();
        this.textoInformativo = in.readString();
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
        setTextoInformativo(nome);
    }

    public String getTextoInformativo() {
        return textoInformativo;
    }

    public void setTextoInformativo(String nome) {
        for(String info: informacoes) {
            String[] infoFragmentada = info.split(":");

            if(infoFragmentada[0].equals(nome)) {
                if(infoFragmentada.length == 2) {
                    this.textoInformativo = infoFragmentada[1];
                } else {
                    this.textoInformativo = "Informação não disponível no momento";
                }

                return;
            }
        }
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
        String[] temposA = this.horario.split(":");
        String[] temposB = obj.getHorario().split(":");

        Integer horaA = new Integer(temposA[0] + temposA[1]);
        Integer horaB = new Integer(temposB[0] + temposB[1]);


        return horaA.compareTo(horaB);
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
        dest.writeString(textoInformativo);
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
