/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafios;

/**
 *
 * @author antonio-nuvunga
 */
public class InvestigacaoFinanceira {

    public int contarTransferenciasSuspeitas(double[] transferencias) {

        int quantidade = 0;

        if (transferencias == null) {
            return 0;
        }

        for (double valor : transferencias) {

            if (valor > 100000) {
                quantidade++;
            }
        }

        return quantidade;
    }
}
