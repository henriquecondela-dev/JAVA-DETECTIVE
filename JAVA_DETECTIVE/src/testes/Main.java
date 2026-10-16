package testes; 

import java.sql.SQLException;
import recursos.*;

public class Main {
    public static void main(String[] args){
        try {
            //Localizacao.listarLocalizacao();
            
            //Localizacao.listarLocalizacao();
            //Mensagem.adicionarMensagem("A testar");
            //Mensagem.listarMensagens();
            
            
            Acusacao.listarAcusacao();
            Acusacao.adicionarAcusacao(1, 1, 3, 4);
            
            
            //Mensagem.listarMensagens();
            
        } catch (SQLException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
       
    }
}
