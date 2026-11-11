/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.time.LocalDateTime;

/**
 *
 * @author antonio-nuvunga
 */
public class ProgressoDesafio {
    private long progressoCasoId;
    private long desafioId;
    private boolean concluido;
    private LocalDateTime dataConclusao;
    
    public ProgressoDesafio(){ }
    
    public ProgressoDesafio(long progressoCasoId, long desafioId){
        this.progressoCasoId = progressoCasoId;
        this.desafioId = desafioId;
        this.dataConclusao=null;
        this.concluido = false;
    }
    
    public ProgressoDesafio(long progressoCasoId, long desafioId, boolean concluido, LocalDateTime dataConclusao){
        this.progressoCasoId = progressoCasoId;
        this.desafioId = desafioId;
        this.concluido=concluido;
        if(concluido==false){
            this.dataConclusao = null;
        }else{
            this.dataConclusao = dataConclusao;
        }
    }
    
    public void setProgressoCasoId(long progressoCasoId){
        this.progressoCasoId = progressoCasoId;
    }
    
    public long getProgressoCasoId(){
        return this.progressoCasoId;
    }
    
    public void setDesafioId(long desafioId){
        this.desafioId = desafioId;
    }
    
    public long getDesafioId(){
        return this.desafioId;
    }
    
    public void marcarComoConcluido(){
        if(this.estadoConclusao()==false){
            this.concluido = true;
            this.dataConclusao = LocalDateTime.now();
        }else{
            throw new IllegalArgumentException("Desafio concluído");
        }
    }
    
    public boolean estadoConclusao(){
        return this.concluido;
    }
    
    public LocalDateTime getDataConclusao(){
        return this.dataConclusao;
    }
}
