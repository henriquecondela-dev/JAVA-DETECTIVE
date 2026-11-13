/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafios;

/**
 *
 * @author antonio-nuvunga
 */
public class AnaliseDepoimentos {

    private String nomeSuspeito;
    private String localDeclarado;
    private String horarioDeclarado;

    public AnaliseDepoimentos(String nomeSuspeito,
                              String localDeclarado,
                              String horarioDeclarado) {

        this.nomeSuspeito = nomeSuspeito;
        this.localDeclarado = localDeclarado;
        this.horarioDeclarado = horarioDeclarado;
    }

    public boolean verificarContradicao(String horarioDeclarado,
                                        String horarioRegistado) {

        int declarado = converterHorario(horarioDeclarado);
        int registado = converterHorario(horarioRegistado);

        if (declarado == -1 || registado == -1) {
            return false;
        }

        return declarado != registado;
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
