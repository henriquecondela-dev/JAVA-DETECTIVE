/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author antonio-nuvunga
 */
public class Evidencia {
    private long id;
    private String titulo;
    private String descricao;
    private Long documento_id;
    private Importancia importancia;
    
    // Construtor vazio
    public Evidencia() {
    }

    // Construtor com todos os atributos
    public Evidencia(long id, String titulo, String descricao, Long documento_id, Importancia importancia) {
        this.id = id;
        if(titulo==null || titulo.isBlank()){
          throw new IllegalArgumentException("Título de evidencia obrigatório!");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.documento_id = documento_id;
        this.importancia = importancia;
    }
    
    public Evidencia(String titulo, String descricao, Long documento_id, Importancia importancia) {
        if(titulo==null || titulo.isBlank()){
          throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.documento_id = documento_id;
        this.importancia = importancia;
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo==null || titulo.isBlank()){
          throw new IllegalArgumentException("Título de etapa obrigatório!");
        }
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getDocumentoId() {
        return documento_id;
    }

    public void setDocumentoId(Long documento_id) {
        this.documento_id = documento_id;
    }

    public Importancia getImportancia() {
        return importancia;
    }

    public void setImportancia(Importancia importancia) {
        this.importancia = importancia;
    }
}