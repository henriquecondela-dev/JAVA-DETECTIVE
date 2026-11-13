/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package desafios;

/**
 *
 * @author antonio-nuvunga
 */
import java.text.Normalizer;

public class AcusacaoFinal {

    private String nomeSuspeito;

    public AcusacaoFinal(String nomeSuspeito) {
        this.nomeSuspeito = nomeSuspeito;
    }

    public boolean verificarCulpado() {

        if (nomeSuspeito == null || nomeSuspeito.trim().isEmpty()) {
            return false;
        }

        String primeiroNome = nomeSuspeito.trim().split("\\s+")[0];

        primeiroNome = Normalizer.normalize(
                primeiroNome,
                Normalizer.Form.NFD
        );

        primeiroNome = primeiroNome.replaceAll("\\p{M}", "");

        primeiroNome = primeiroNome.replaceAll("[^\\p{L}]", "");

        return primeiroNome.equalsIgnoreCase("Antonio");
    }
}
