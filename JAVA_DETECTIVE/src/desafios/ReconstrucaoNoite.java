/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafios;

/**
 *
 * @author antonio-nuvunga
 */
public class ReconstrucaoNoite {

    public boolean estaNaJanelaDoCrime(int hora, int minuto) {

        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
            return false;
        }

        int horario = hora * 60 + minuto;

        int inicio = 22 * 60 + 15;
        int fim = 22 * 60 + 30;

        return horario >= inicio && horario <= fim;
    }
}
