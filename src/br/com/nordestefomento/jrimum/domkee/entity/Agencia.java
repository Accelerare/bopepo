/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:57:33
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode usar
 * esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigência legal ou acordo por escrito, a distribuição de software sob
 * esta LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER
 * TIPO, sejam expressas ou tácitas. Veja a LICENÇA para a redação específica a
 * reger permissões e limitações sob esta LICENÇA.
 * 
 * Criado em: 30/03/2008 - 18:57:33
 * 
 */

package br.com.nordestefomento.jrimum.domkee.entity;

import java.io.Serializable;

/**
 * 
 * <p>
 *  Para ver o conceito de negócio, consulte o 
 *  {@link http://jrimum.nordestefomento.com.br/wprojeto/wiki/Glossario glossário}.
 * </p>
 * 
 * @author Romulo
 * @author Gilmar
 * @author Misael
 * @author Samuel
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class Agencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3512980818455792739L;
	
	private Integer codigoDaAgencia = 0;

	private String digitoDaAgencia  = "";
	
	public Agencia() {}
	
	public Agencia(Integer codigoDaAgencia) {
		
		this.codigoDaAgencia = codigoDaAgencia;
	}
	
	public Agencia(Integer codigoDaAgencia, String digitoDaAgencia) {
		
		this.codigoDaAgencia = codigoDaAgencia;
		this.digitoDaAgencia = digitoDaAgencia;
	}

	public Integer getCodigoDaAgencia() {
		return codigoDaAgencia;
	}

	public void setCodigoDaAgencia(Integer codigoDaAgencia) {
		this.codigoDaAgencia = codigoDaAgencia;
	}

	public String getDigitoDaAgencia() {
		return digitoDaAgencia;
	}

	public void setDigitoDaAgencia(String digitoDaAgencia) {
		this.digitoDaAgencia = digitoDaAgencia;
	}

}
