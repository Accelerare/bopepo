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

import static com.github.braully.boleto.TagLayout.TagCreator.fforma;
import static com.github.braully.boleto.TagLayout.TagCreator.fnumeroRemessa;
import static com.github.braully.boleto.TagLayout.TagCreator.foperacao;
import static com.github.braully.boleto.TagLayout.TagCreator.fsequencialArquivo;
import static com.github.braully.boleto.TagLayout.TagCreator.fservico;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author braully
 */
/* Reune atributos recorrentes nos registros do tipo cabecalho */
public class CabecalhoArquivo extends RegistroArquivo {

    public CabecalhoArquivo(TagLayout get) {
        super(get);
    }

    public CabecalhoArquivo agencia(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo conta(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo numeroConvenio(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo cedente(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo dataGeracao(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo dataGeracao(Date data) {
        return (CabecalhoArquivo) setValue(data);
    }
    public CabecalhoArquivo horaGeracao(Date hora) {
        return (CabecalhoArquivo) setValue(hora);
    }

    public CabecalhoArquivo cedenteCnpj(String string) {
        return (CabecalhoArquivo) setValue(string);
    }

    public CabecalhoArquivo sequencialArquivo(Integer i) {
        setValue(fsequencialArquivo().nome, i);
        return this;
    }

    public CabecalhoArquivo numeroRemessa(Integer i) {
        setValue(fnumeroRemessa().nome, i);
        return this;
    }

    public Integer sequencialArquivo() {
        return getValueAsInteger(fsequencialArquivo().nome);
    }

    public CabecalhoArquivo operacao(Object op) {
        setValue(foperacao().nome, op);
        return this;
    }

    public String operacao() {
        return getValue(foperacao().nome);
    }

    public CabecalhoArquivo servico(Object op) {
        setValue(fservico().nome, op);
        return this;
    }

    public String servico() {
        return getValue(fservico().nome);
    }

    public CabecalhoArquivo forma(Object op) {

        if (Objects.equals(this.bancoCodigo(),"341" ) || Objects.equals(this.bancoCodigo(),"748" )) {
            //regra itau e sicredi. só o itau exige que seja 41 para TED
            //2024 doc vai deixar de existir
            if (op.equals(3)) {
                op = 41;
            }
        }


        setValue(fforma().nome, op);
        return this;
    }

    public Object forma() {
        return getValue(fforma().nome);
    }
}
