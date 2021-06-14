package com.algaworks.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.algaworks.brewer.validation.Sku;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "cerveja")
public class Cerveja {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotEmpty
//	@Pattern(regexp = "([a-zA-Z]{3}\\d{1})?", message = "Padrão deve ser AAA9")
	@Sku
	private String sku;
	
	@NotEmpty
	private String nome;
	
	@Size(min=1, max=50)
	private String descricao;
	
	private BigDecimal valor;
	
	@Column(name = "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	private BigDecimal comissao;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@ManyToOne
	@JoinColumn(name = "codigo_estilo")
	private Estilo estilo;

	private String foto;

	@Column(name = "content_type")
	private String contentType;
	
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public Sabor getSabor() {
		return sabor;
	}
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@PrePersist
	@PreUpdate
	public void preProcessa() {
		this.sku = this.sku.toUpperCase();
	}

	public String getFotoOuMock() {
		return StringUtils.isEmpty(this.foto) ? "cerveja-mock.png" : this.foto;
	}
}
// mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:/home/gleiton/eclipse-workspace/brewer/src/main/resources/db/hsql/brewer -dbname.0 xdb"
// mvn exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing" -Dexec.args="--url jdbc:hsqldb:hsql://localhost/xdb"