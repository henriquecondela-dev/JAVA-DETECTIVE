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
public class ProgressoEvidencia {
    private long progressoCasoId;
    private long evidenciaId;
    private boolean coletada;
    private LocalDateTime dataColeta;
    
    public ProgressoEvidencia(){ }
    
    public ProgressoEvidencia(long progressoCasoId, long evidenciaId, boolean coletada, LocalDateTime dataColeta){
        this.progressoCasoId = progressoCasoId;
        this.evidenciaId = evidenciaId;
        this.coletada = coletada;
        if(coletada==false){
            this.dataColeta = null;    
        }else{
            this.dataColeta = dataColeta;
        }
    }
    
    public ProgressoEvidencia(long progressoCasoId, long evidenciaId){
        this.progressoCasoId = progressoCasoId;
        this.evidenciaId = evidenciaId;
        this.dataColeta = null;
        this.coletada = false;
    }
    
    public void setProgressoCasoId(long progressoCasoId){
        this.progressoCasoId = progressoCasoId;
    }
    
    public void setEvidenciaId(long evidenciaId){
        this.evidenciaId = evidenciaId;
    }
    
    public long getProgressoCasoId(){
        return this.progressoCasoId;
    }
    
    public long getEvidenciaId(){
        return this.evidenciaId;
    }
    
    public void coletarEvidencia(){
        if(this.getEstadoColeta() == false){
            this.coletada = true;
            this.dataColeta = LocalDateTime.now();
        }else{
            throw new IllegalArgumentException("Evidência já coletada");
        }
    }
    
    public boolean getEstadoColeta(){
        return this.coletada;
    }
    
    public LocalDateTime getDataColeta(){
        return this.dataColeta;
    }
}
