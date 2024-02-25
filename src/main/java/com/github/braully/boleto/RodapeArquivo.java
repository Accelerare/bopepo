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

import static com.github.braully.boleto.TagLayout.TagCreator.rodapeLote;

import java.math.BigDecimal;

/**
 *
 * @author braully
 */
public class RodapeArquivo extends RegistroArquivo {

    private RemessaFacade remessa;

    public RodapeArquivo(TagLayout get, RemessaFacade remessa) {        
        super(get);
        this.remessa = remessa;
    }

    public RodapeArquivo quantidadeRegistros(Number valorQuantidade) {
        return (RodapeArquivo) setValue(valorQuantidade);
    }

    public RodapeArquivo quantidadeLotes(Number quantidade) {
        return (RodapeArquivo) setValue(quantidade);
    }

    public Number quantidadeRegistros() {
        return getValueAsNumber();
    }

    public RodapeArquivo valorTotalRegistros(BigDecimal valorTotal) {

        String valorAsString = valorTotal.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();

        if (remessa.isPermiteQtdeMoeda()) {
            //qtdeMoeda deve ter 5 casas decimais, por isso multiplicamos por 100.000
			this.setValue("qtdeMoeda",valorTotal.multiply(new BigDecimal(100000)).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		}

        return (RodapeArquivo) setValue(valorAsString);
    }

}
