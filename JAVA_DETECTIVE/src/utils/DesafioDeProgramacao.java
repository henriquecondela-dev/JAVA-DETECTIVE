/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author antonio-nuvunga
 */
public class DesafioDeProgramacao {

    private long id;
    private long casoId;
    private long etapaId;
    private String titulo;
    private String descricao;
    private String classeEsperada;
    private String metodoEsperado;

    public DesafioDeProgramacao() {
    }

    public DesafioDeProgramacao(long id, long casoId, long etapaId, String titulo, String descricao, String classeEsperada, String metodoEsperado) {
        this.id = id;
        this.casoId = casoId;
        this.etapaId = etapaId;
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo obrigatório.");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.classeEsperada = classeEsperada;
        this.metodoEsperado = metodoEsperado;

    }

    @Override
    public String toString() {
        return titulo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCasoId(long caso_id) {
        this.casoId = caso_id;
    }

    public void setEtapaId(long etapa_id) {
        this.etapaId = etapa_id;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo obrigatório.");
        }
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setClasseEsperada(String classeEsperada) {
        this.classeEsperada = classeEsperada;
    }

    public void setMetodoEsperado(String metodoEsperado) {
        this.metodoEsperado = metodoEsperado;
    }

    public long getId() {
        return this.id;
    }

    public long getCasoId() {
        return this.casoId;
    }

    public long getEtapaId() {
        return this.etapaId;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getClasseEsperada() {
        return this.classeEsperada;
    }

    public String getMetodoEsperado() {
        return this.metodoEsperado;
    }

}
