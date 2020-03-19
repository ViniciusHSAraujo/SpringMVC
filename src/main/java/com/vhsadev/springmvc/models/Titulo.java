package com.vhsadev.springmvc.models;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.vhsadev.springmvc.models.enums.StatusTitulo;

@Entity
public class Titulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@NotEmpty(message = "O campo DESCRIÇÃO é requerido.")
	private String descricao;
	
	@NotNull(message = "O campo DATA DE VENCIMENTO é requerido.")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataVencimento;
	
	@NotNull(message = "O campo VALOR é requerido.")
	@DecimalMin(value = "0.01", message = "O valor deve ser maior que 0.01.")
	private BigDecimal valor;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusTitulo status;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	@NumberFormat(pattern = "##0.00")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public StatusTitulo getStatus() {
		return status;
	}

	public void setStatus(StatusTitulo status) {
		this.status = status;
	}
	
	public boolean isPendente() {
		return StatusTitulo.PENDENTE.equals(this.status);
	}
	
	public String getDataVencimentoFormatada() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(this.dataVencimento);
	}
}
