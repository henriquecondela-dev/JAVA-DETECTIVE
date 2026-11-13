/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafios;

/**
 *
 * @author antonio-nuvunga
 */
import java.util.ArrayList;

public class AnaliseAcessos {

    public boolean existeRegisto(ArrayList<String> horarios,
                                 String horarioProcurado) {

        if (horarios == null || horarioProcurado == null) {
            return false;
        }

        int horarioPesquisa = converterHorario(horarioProcurado);

        if (horarioPesquisa == -1) {
            return false;
        }

        for (String horario : horarios) {

            int horarioRegistado = converterHorario(horario);

            if (horarioRegistado != -1 &&
                horarioRegistado == horarioPesquisa) {

                return true;
            }
        }

        return false;
    }

    private int converterHorario(String horario) {

        if (horario == null) {
            return -1;
        }

        try {
            String[] partes = horario.trim().split(":");

            if (partes.length != 2) {
                return -1;
            }

            int hora = Integer.parseInt(partes[0].trim());
            int minuto = Integer.parseInt(partes[1].trim());

            if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                return -1;
            }

            return hora * 60 + minuto;

        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
