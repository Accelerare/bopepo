/*
 * Copyright 2019 Projeto JRimum.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.braully.boleto;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jrimum.texgit.Fillers;
import org.jrimum.texgit.FixedField;
import org.jrimum.texgit.MetaOrderedField;
import org.jrimum.texgit.FixedField;

/**
 *
 * @author braully
 */
public class RemessaFacade extends ArquivoFacade {

	public static Logger logger = LogManager.getLogger(ArquivoFacade.class);


    int quantidadeSegmentoA = 0;

    public boolean isPermiteQtdeMoeda() {
		return this.template.isPermiteQtdeMoeda();
	}

    public String getNomeDoArquivo() {
		return this.template.getNomeDoArquivo();
	}

    public boolean isExigeNumeroDocumento() {
		return this.template.isExigeNumeroDocumento();
	}

    public boolean isCodigoFinalidadeEmBrancoSeCreditoEmConta() {
		return this.template.isCodigoFinalidadeEmBrancoSeCreditoEmConta();
	}

    public boolean isExigeCPFCNPJFavorecidoNoSegmentoA() {
		return this.template.isExigeCPFCNPJFavorecidoNoSegmentoA();
	}

	public RemessaFacade(TagLayout template) {
        this.template = template;
    }

    public CabecalhoArquivo addNovoCabecalho() {
        CabecalhoArquivo cabecalho = novoCabecalho("cabecalho");
        this.add(cabecalho);
        return cabecalho;
    }

    public CabecalhoArquivo addNovoCabecalhoLote() {
        CabecalhoArquivo cabecalho = novoCabecalho("cabecalhoLote");
        this.add(cabecalho);
        quantidadeSegmentoA = 0;
        return cabecalho;
    }

    public CabecalhoArquivo novoCabecalho(String tipoCabecalho) {
        CabecalhoArquivo cabecalho = new CabecalhoArquivo(template.get(tipoCabecalho), this);
        return cabecalho;
    }

    public TituloArquivo addNovoDetalhe() {
        TituloArquivo titulo = this.novoTitulo("detalhe");
        this.add(titulo);
        return titulo;
    }

    public TituloArquivo addNovoDetalhe(String segmento) {
        TituloArquivo titulo = this.novoTitulo("detalheSegmento" + segmento);


        if (segmento.equals("A")) {

            if (this.isExigeNumeroDocumento()) {
                this.quantidadeSegmentoA++;
                titulo.getField("numeroDocumento").setFiller(Fillers.WHITE_SPACE_RIGHT);
                titulo.setValue("numeroDocumento", quantidadeSegmentoA);
            } else {
                titulo.setValue("numeroDocumento", "");
            }
        }

        this.add(titulo);
        
        return titulo;
    }
    
    public TituloArquivo addNovoDetalheSegmentoA() {
        return addNovoDetalhe("A");
    }

    public TituloArquivo addNovoDetalheSegmentoB() {
        return addNovoDetalhe("B");
    }

    public TituloArquivo addNovoDetalheSegmentoJ() {
        return addNovoDetalhe("J");
    }

    public TituloArquivo addNovoDetalheSegmentoJ52() {
        return addNovoDetalhe("J52");
    }

    public TituloArquivo addNovoDetalheSegmentoP() {
        return addNovoDetalhe("P");
    }

    public TituloArquivo addNovoDetalheSegmentoQ() {
        return addNovoDetalhe("Q");
    }

    public TituloArquivo addNovoDetalheSegmentoR() {
        return addNovoDetalhe("R");
    }

    public TituloArquivo addNovoDetalheTransacao() {
        return addNovoDetalhe();
    }

    public TituloArquivo novoTitulo(String tipoTitulo) {
        TituloArquivo titulo = new TituloArquivo(template.get(tipoTitulo), this);
        return titulo;
    }

    public RodapeArquivo addNovoRodape() {
        RodapeArquivo rodape = this.novoRodape("rodape");
        this.add(rodape);
        return rodape;
    }

    public RodapeArquivo addNovoRodapeLote() {
        RodapeArquivo rodape = this.novoRodape("rodapeLote");
        this.add(rodape);
        return rodape;
    }

    public RodapeArquivo novoRodape(String tipoRodape) {
        RodapeArquivo rodape = new RodapeArquivo(template.get(tipoRodape), this);
        return rodape;
    }

    public RegistroArquivo addNovoRegistro(String tipoRegistro) {
        RegistroArquivo novoRegistro = novoRegistro(tipoRegistro);
        this.add(novoRegistro);
        return novoRegistro;
    }

    public RegistroArquivo novoRegistro(String tipoRegistro) {
        TagLayout layoutRegistro = template.get(tipoRegistro);
        if (layoutRegistro == null) {
            throw new IllegalArgumentException("Não existe registro do tipo " + tipoRegistro + " no layout " + template);
        }
        return new RegistroArquivo(layoutRegistro);
    }
}
