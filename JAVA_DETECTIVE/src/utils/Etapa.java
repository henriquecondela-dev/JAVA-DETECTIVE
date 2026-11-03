/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.util.ArrayList;

/**
 *
 * @author Maunze
 */
public class Etapa {
    private long id;
    private int numero;
    private int quantidadeMinimaEnvidencias;
    private String titulo;
    private String descricao;
    private String objectivo;
    private boolean desbloqueada;
    private boolean concluida;
    private ArrayList<String> evidencias;
    private ArrayList<String> desafios;
    
    public Etapa(long id,int numero, int quantidadeMinimaEnvidencias, String titulo, String descricao, String objectivo, boolean desbloqueada, boolean concluida ){
      this.id=id;
      this.numero= numero;
      this.quantidadeMinimaEnvidencias= quantidadeMinimaEnvidencias;
      this.titulo=titulo;
      this.objectivo=objectivo;
      this.descricao=descricao;
      this.concluida= false;
      this.desbloqueada= false;
      this.evidencias = new ArrayList<>();
      this.desafios = new ArrayList<>();
    }
    
    
    public void desbloquear(){
        setDesbloqueada(true);
    }
    
    public void concluir(){
        if(isDesbloqueada() && getEvidencias().size()>= getQuantidadeMinimaEnvidencias()){
            setConcluida(true);
        }
    }
    
    public boolean podeAvancar(){
        return isConcluida();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }


    /**
     * @return the quantidadeMinimaEnvidencias
     */
    public int getQuantidadeMinimaEnvidencias() {
        return quantidadeMinimaEnvidencias;
    }

    /**
     * @param quantidadeMinimaEnvidencias the quantidadeMinimaEnvidencias to set
     */
    public void setQuantidadeMinimaEnvidencias(int quantidadeMinimaEnvidencias) {
        this.quantidadeMinimaEnvidencias = quantidadeMinimaEnvidencias;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the objectivo
     */
    public String getObjectivo() {
        return objectivo;
    }

    /**
     * @param objectivo the objectivo to set
     */
    public void setObjectivo(String objectivo) {
        this.objectivo = objectivo;
    }

    /**
     * @return the desbloqueada
     */
    public boolean isDesbloqueada() {
        return desbloqueada;
    }

    /**
     * @param desbloqueada the desbloqueada to set
     */
    public void setDesbloqueada(boolean desbloqueada) {
        this.desbloqueada = desbloqueada;
    }

    /**
     * @return the concluida
     */
    public boolean isConcluida() {
        return concluida;
    }

    /**
     * @param concluida the concluida to set
     */
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    /**
     * @return the evidencias
     */
    public ArrayList<String> getEvidencias() {
        return evidencias;
    }

    /**
     * @param evidencias the evidencias to set
     */
    public void setEvidencias(ArrayList<String> evidencias) {
        this.evidencias = evidencias;
    }

    /**
     * @return the desafios
     */
    public ArrayList<String> getDesafios() {
        return desafios;
    }

    /**
     * @param desafios the desafios to set
     */
    public void setDesafios(ArrayList<String> desafios) {
        this.desafios = desafios;
    }
    
}
