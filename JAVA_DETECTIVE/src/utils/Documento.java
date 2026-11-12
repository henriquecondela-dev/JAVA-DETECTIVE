package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author antonio-nuvunga
 */
import java.time.LocalDate;

public class Documento {

    private long id;
    private String nome;
    private String tipo;
    private String conteudo;
    private LocalDate dataDocumento;

    public Documento() {
    }

    public Documento(
            long id,
            String nome,
            String tipo,
            String conteudo,
            LocalDate dataDocumento
    ) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.conteudo = conteudo;
        this.dataDocumento = dataDocumento;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDate getDataDocumento() {
        return dataDocumento;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setDataDocumento(LocalDate dataDocumento) {
        this.dataDocumento = dataDocumento;
    }
}
