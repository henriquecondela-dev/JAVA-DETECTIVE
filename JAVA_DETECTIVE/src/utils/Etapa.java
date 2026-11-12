/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maunze
 */
public class Etapa{
    private long id;
    private long caso_id;
    private int numero;
    private int minimoEvidencias;
    private String titulo;
    private String descricao;
    private List<Evidencia> evidencias;
    private List<DesafioDeProgramacao> desafios;
    private List<Objetivo> objetivos;
    private List<Documento> documentos;
    private Mensagem mensagem;

    public Etapa() {
        this.documentos = new ArrayList<>();
        this.mensagem = new Mensagem();
        this.objetivos = new ArrayList<>();
    }

    /**
     *
     * @param id
     * @param caso_id
     * @param numero
     * @param minimoEvidencias
     * @param titulo
     * @param descricao
     * @param evidencias
     * @param desafios
     */
    public Etapa(
            long id,
            long caso_id,
            int numero,
            int minimoEvidencias,
            String titulo,
            String descricao,
            List<Evidencia> evidencias,
            List<DesafioDeProgramacao> desafios,
            List<Objetivo> objetivos
    ) {
        this(id, caso_id, numero, minimoEvidencias,
                titulo, descricao, evidencias, desafios);

        this.objetivos = objetivos != null ? new ArrayList<>(objetivos) : new ArrayList<>();
    }

    public Etapa(long id, long caso_id, int numero, int minimoEvidencias, String titulo, String descricao,
            List<Evidencia> evidencias,
            List<DesafioDeProgramacao> desafios) {
        this.id = id;
    public Etapa(long id, long caso_id, int numero, int minimoEvidencias, String titulo, String descricao, List<Evidencia> evidencias, List<DesafioDeProgramacao> desafios){
        this.id=id;
        this.caso_id = caso_id;
        this.numero = numero;
        this.minimoEvidencias = minimoEvidencias;

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título de etapa obrigatório!");
        }

        this.titulo = titulo;
        this.descricao = descricao;

        this.evidencias = evidencias != null ? new ArrayList<>(evidencias) : new ArrayList<>();

        this.desafios = desafios != null ? new ArrayList<>(desafios) : new ArrayList<>();
        this.objetivos = new ArrayList<>();
        this.documentos = new ArrayList<>();
        this.mensagem = new Mensagem();

    }

    public Etapa(long id, long caso_id, int numero, int minimoEvidencias, String titulo, String descricao) {
        this.id = id;
        this.caso_id = caso_id;
        this.numero = numero;
        this.minimoEvidencias = minimoEvidencias;
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.evidencias = new ArrayList<>();
        this.desafios = new ArrayList<>();
        this.objetivos = new ArrayList<>();
        this.documentos = new ArrayList<>();
        this.mensagem = new Mensagem();
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos != null
                ? new ArrayList<>(objetivos)
                : new ArrayList<>();
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos != null ? new ArrayList<>(documentos) : new ArrayList<>();
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the caso_id
     */
    public long getCasoId() {
        return caso_id;
    }

    /**
     * @param caso_id the id to set
     */
    public void setCasoId(long caso_id) {
        this.caso_id = caso_id;
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
    public int getMinimoEvidencias() {
        return minimoEvidencias;
    }

    /**
     * @param minimoEvidencias the minimoEvidencias to set
     */
    public void setMinimoEvidencias(int minimoEvidencias) {
        this.minimoEvidencias = minimoEvidencias;
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
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
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
     * @return the evidencias
     */
    public List<Evidencia> getEvidencias() {
        return evidencias;
    }

    /**
     * @param evidencias the evidencias to set
     */
    public void setEvidencias(List<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    /**
     * @return the desafios
     */
    public List<DesafioDeProgramacao> getDesafios() {
        return desafios;
    }

    /**
     * @param desafios the desafios to set
     */
    public void setDesafios(List<DesafioDeProgramacao> desafios) {
        this.desafios = desafios;
    }

    public void adicionarEvidencia(Evidencia evidencia) {
        if (evidencia == null) {
            throw new IllegalArgumentException("Evidência inválida!");
        }
        this.evidencias.add(evidencia);
    }

    public void adicionarDesafio(DesafioDeProgramacao desafio) {
        if (desafio == null) {
            throw new IllegalArgumentException("Desafio inválido!");
        }
        this.desafios.add(desafio);
    }

}
