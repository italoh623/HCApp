package me.aflak.heroicuidador.model;

public class ExtraUtils {

    public static boolean isMesmoHorario(String horaA, String horaB) {

        String temposA[] = horaA.split(":");
        String temposB[] = horaB.split(":");

        if (temposA[0].equals(temposB[0])) {

            Integer minutosA = new Integer(temposA[1]);
            Integer minutosB = new Integer(temposB[1]);

            if (minutosA - 3 <= minutosB && minutosA + 3 >= minutosB ) {
                return true;
            }
        }

        return false;
    }
}
