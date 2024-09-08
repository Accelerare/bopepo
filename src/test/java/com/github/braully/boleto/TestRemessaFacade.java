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

import static com.github.braully.boleto.TagLayout.TagCreator.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.utilix.FileUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author braully
 */
public class TestRemessaFacade {

        private static final Date dataHoraGeracao = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        

        private String getDataHoraFormatada(Date data) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		sdf.setCalendar(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC"))));

                return sdf.format(data);
        }

        private String getDataFormatada(Date data) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setCalendar(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC"))));

                return sdf.format(data);
        }


        private void verificarResultadoPorLinha(String remessaStr, StringBuilder textoEsperado) {
                //fazer split por FileUtil.NEW_LINE e verificar cada linha
                  String[] linhasExperadas = textoEsperado.toString().split(FileUtil.NEW_LINE);
                  String[] linhas = remessaStr.split(FileUtil.NEW_LINE);
  
                  for (int i = 0; i < linhasExperadas.length; i++) {
                          Assert.assertEquals(linhasExperadas[i], linhas[i]);
                          System.out.println("linhas " + i + " OK" );
                  }
        }

    @Ignore
    @Test
    public void testRemessaCobancaGenericaFebraban240V5SegmentoPQ() {
        RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA);
        remessa.addNovoCabecalho()
                .sequencialArquivo(1)
                .dataGeracao(new Date()).setVal("horaGeracao", new Date())
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoCabecalhoLote()
                .operacao("R")//Operação de remessa
                .servico(1)//Cobrança
                .forma(1)//Crédito em Conta Corrente
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1");

        remessa.addNovoDetalheSegmentoP()
                .valor(1)
                .valorDesconto(0).valorAcrescimo(0)//opcionais
                .dataGeracao(new Date())
                .dataVencimento(new Date())
                .numeroDocumento(1)
                .nossoNumero(1)
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(1);

        remessa.addNovoDetalheSegmentoQ()
                .sacado("Fulano de Tal", "0")
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(2);

        remessa.addNovoRodapeLote()
                .quantidadeRegistros(2)
                .valorTotalRegistros(new BigDecimal(1))
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoRodape()
                .quantidadeRegistros(1)
                .valorTotalRegistros(new BigDecimal(1))
                .setVal("codigoRetorno", "1")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        String remessaStr = remessa.render();
        System.err.println(remessaStr);
    }

    @Test
    public void testRemessaCobancaBBFebraban240V5SegmentoPQ() {
        RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.LAYOUT_BB_CNAB240_COBRANCA_REMESSA);
        remessa.addNovoCabecalho()
                .sequencialArquivo(1)
                .dataGeracao(new Date()).setVal("horaGeracao", new Date())
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .carteira("00");

        remessa.addNovoCabecalhoLote()
                .operacao("R")//Operação de remessa
                .servico(1)//Cobrança
                .forma(1)//Crédito em Conta Corrente
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .carteira("00");;

        remessa.addNovoDetalheSegmentoP()
                .valor(1)
                .valorDesconto(0).valorAcrescimo(0)//opcionais
                .dataGeracao(new Date())
                .dataVencimento(new Date())
                .numeroDocumento(1)
                .nossoNumero(1)
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(1)
                .carteira("00");

        remessa.addNovoDetalheSegmentoQ()
                .sacado("Fulano de Tal", "0")
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(2)
                .carteira("00");;

        remessa.addNovoRodapeLote()
                .quantidadeRegistros(2)
                .valorTotalRegistros(new BigDecimal(1))
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .carteira("00");;

        remessa.addNovoRodape()
                .quantidadeRegistros(1)
                .valorTotalRegistros(new BigDecimal(1))
                .setVal("codigoRetorno", "1")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1")
                .carteira("00");

        String remessaStr = remessa.render();
        System.err.println(remessaStr);
    }

    @Ignore
    @Test
    public void testRemessaCobancaGenericaFebraban240V5() {
        RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA);
        remessa.addNovoCabecalho()
                .sequencialArquivo(1)
                .dataGeracao(new Date()).setVal("horaGeracao", new Date())
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoCabecalhoLote()
                .operacao("R")//Operação de remessa
                .servico(1)//Cobrança
                .forma(1)//Crédito em Conta Corrente
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1");

        remessa.addNovoDetalheSegmentoJ()
                .sacado("Fulano de Tal", "0")
                .codigoBarras("0")
                .valor(1)
                .valorDesconto(0).valorAcrescimo(0)//opcionais
                .dataVencimento(new Date())
                .numeroDocumento(1)
                .nossoNumero(1)
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(1);

        remessa.addNovoDetalheSegmentoJ52()
                .sacado("Fulano de Tal", "0")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(2);

        remessa.addNovoRodapeLote()
                .quantidadeRegistros(2)
                .valorTotalRegistros(new BigDecimal(1))
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoRodape()
                .quantidadeRegistros(1)
                .valorTotalRegistros(new BigDecimal(1))
                .setVal("codigoRetorno", "1")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        String remessaStr = remessa.render();
        System.err.println(remessaStr);
//        assertEquals(remessaStr, "");
    }

    @Ignore
    @Test
    public void testRemessaCobancaGenericaSicredi240V5() {
        RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.LAYOUT_FEBRABAN_CNAB240_COBRANCA_REMESSA);
        remessa.addNovoCabecalho()
                .sequencialArquivo(1)
                .dataGeracao(new Date()).setVal("horaGeracao", new Date())
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoCabecalhoLote()
                .operacao("R")//Operação de remessa
                .servico(1)//Cobrança
                .forma(1)//Crédito em Conta Corrente
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1")
                .convenio(null,"1", "1", "1", "1");

        remessa.addNovoDetalheSegmentoJ()
                .sacado("Fulano de Tal", "0")
                .codigoBarras("0")
                .valor(1)
                .valorDesconto(0).valorAcrescimo(0)//opcionais
                .dataVencimento(new Date())
                .numeroDocumento(1)
                .nossoNumero(1)
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(1);

        remessa.addNovoDetalheSegmentoJ52()
                .sacado("Fulano de Tal", "0")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1")
                .sequencialRegistro(2);

        remessa.addNovoRodapeLote()
                .quantidadeRegistros(2)
                .valorTotalRegistros(new BigDecimal(1))
                .banco("0", "Banco")
                .cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        remessa.addNovoRodape()
                .quantidadeRegistros(1)
                .valorTotalRegistros(new BigDecimal(1))
                .setVal("codigoRetorno", "1")
                .banco("0", "Banco").cedente("ACME S.A LTDA.", "1").convenio(null,"1", "1", "1", "1");

        String remessaStr = remessa.render();
        System.err.println(remessaStr);
//        assertEquals(remessaStr, "");
    }

    @Ignore
    @Test
    public void testRemessaVazia() {
        RemessaFacade remessa = new RemessaFacade(layoutGenericoTest());
        remessa.addNovoCabecalho().agencia("1")
                .conta("1").numeroConvenio("1")
                .cedente("ACME S.A LTDA.").cedenteCnpj("1")
                .dataGeracao(new Date()).setValue("codigoRetorno", "1");

        remessa.addNovoDetalhe().valor("1").vencimento("1")
                .numeroDocumento("1").nossoNumero("1")
                .dataEmissao("1").carteira("1")
                .sacado("Fulano de Tal").sacadoCpf("1")
                .sacadoEndereco("Rua 1, Numero 1, Cidade Z")
                .instrucao("Senhor caixa não receber nunca");

        remessa.addNovoRodape()
                .quantidadeRegistros(1)
                .valorTotalRegistros(new BigDecimal(1))
                .setValue("codigoRetorno", "1");

        String remessaStr = remessa.render();
        System.err.println(remessaStr);
//        assertEquals(remessaStr, "");
    }


	@Test
	public void testGetLayoutCNAB240PagamentoRemessa() {

		System.out.println(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("033"));
		System.out.println(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("001"));
		System.out.println(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("237"));
		System.out.println(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("341"));

	}

	@Test
	public void testRemessaPagamentoSantander240() {

                String codigoBanco = "033";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa(codigoBanco));

		Assert.assertEquals(true, remessa.isPermiteQtdeMoeda());

		String razaoSocial = "ACME S.A LTDA.";
		String cnpj = "111.222.33.0001/44";

		String numeroConvenio = "1234567890-1234567890";
		//testando preenchimento automatico do digito veriricador como 0
		String agenciaComDigito = "0123";
		String contaComDigito = "0000123-4";
		String DAC = " ";
		int sequencialRegistro = 1;

                
		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC);

		remessa.addNovoCabecalhoLote()
				.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");


                BigDecimal valorPagamento = new BigDecimal(47292.00);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("033")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva")
		.dataPagamento(new Date())
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro);

		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);


		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();

		rodapeLote
		.quantidadeRegistros(24)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

		remessa.addNovoRodape()
		.quantidadeRegistros(14)
		.quantidadeLotes(1);

		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("03300000         211122233000144123456789012345678900012300000000001234 ACME S.A LTDA.                BANCO SANTANDER (BRASIL) S.A.           1" + getDataHoraFormatada(dataHoraGeracao) + "00002206000000                                                                     " + FileUtil.NEW_LINE 
                + "03300001C2001031 211122233000144123456789012345678900012300000000001234 ACME S.A LTDA.                                                        RUA XYZ                       123                 SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "0330000300001A0000000330123450000000012345 JOSE DA SILVA                                     " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000004729200                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "0330001300001B   100011122233455                                                                                               " + getDataFormatada(dataHoraGeracao) + "000000004729200000000000000000000000000000000000000000000000000000000000000                   0          " + FileUtil.NEW_LINE
                + "03300015         000024000000000004729200000000004729200000                                                                                                                                                                                     " + FileUtil.NEW_LINE
                + "03399999         000001000014                                                                                                                                                                                                                   " + FileUtil.NEW_LINE);



                verificarResultadoPorLinha(remessaStr, textoEsperado);

        }


	@Test
	public void testRemessaPagamentoBradesco240() throws IOException {

                String codigoBanco = "237";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa(codigoBanco));

		Assert.assertEquals(false, remessa.isPermiteQtdeMoeda());
                Assert.assertEquals(false, remessa.isExigeCPFCNPJFavorecidoNoSegmentoA());

                System.out.println("remessa.nomeArquivo = " + remessa.getNomeDoArquivo());


		String razaoSocial = "EMPRESA TESTE XYZ";
		String cnpj = "55.975.206/0001-29";

		String numeroConvenio = "555555";
		
                //testando preenchimento automatico do digito verificador como 0
		String agenciaComDigito = "01110";
		String contaComDigito = "0011111-1";
		String DAC = " ";
		int sequencialRegistro = 1;

                String numeroDoArquivoDeLote = "1";

		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC);



                //vai ser 1 lote para transferencia no mesmo banco (transf mesmo banco), outro lote para outros bancos (TED) e um lote para PIX


                //LOTE 1 - MESMO BANCO
		remessa.addNovoCabecalhoLote()
				.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("AV TESTE","111","","São Paulo","01104010", "SP");


		BigDecimal valorPagamento = new BigDecimal(5.82);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("237")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva 1")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro);

                sequencialRegistro++;
                
		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);


		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();

		rodapeLote
		.quantidadeRegistros(2)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

                //******************/
                //FIM LOTE MESMO BANCO
                //******************/

                //******************/
                //LOTE OUTRO BANCO
                //******************/
                sequencialRegistro = 1;
		remessa.addNovoCabecalhoLote()
				.forma(3)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("AV TESTE","111","","São Paulo","01104010", "SP");


		BigDecimal valorPagamento2 = new BigDecimal(10);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("018")
		.favorecidoCodigoBanco("033")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva 2")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro);

                sequencialRegistro++;
                
		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);


		RodapeArquivo rodapeLote2 = remessa.addNovoRodapeLote();

		rodapeLote2
		.quantidadeRegistros(2)
		.valorTotalRegistros(valorPagamento2)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

                //FIM LOTE 2 - OUTROS BANCOS


                //******************/
                //LOTE PIX
                //******************/
                sequencialRegistro = 1;
		remessa.addNovoCabecalhoLote()
				.forma(45) // 1 = Crédito em Conta Corrente mesmo banco | 3 = doc/ted outro banco | 45 = pix transferencia
				.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("AV TESTE","111","","São Paulo","01104010", "SP");


		BigDecimal valorPagamento3 = new BigDecimal(10);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("009") //009 = PIX

		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva 3")

		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro);

                sequencialRegistro++;
                
		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
                .tipoChavePIX("02") //EMAIL
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);


		RodapeArquivo rodapeLote3 = remessa.addNovoRodapeLote();

		rodapeLote3
		.quantidadeRegistros(2)
		.valorTotalRegistros(valorPagamento3)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

                //FIM LOTE 3 - PIX


                remessa.addNovoRodape() 
		.quantidadeRegistros(12)
		.quantidadeLotes(3);


		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("23700000         255975206000129555555              0011100000000111111 EMPRESA TESTE XYZ             BRADESCO                                1" + getDataHoraFormatada(dataHoraGeracao) + "00002208901600                                                                     " + FileUtil.NEW_LINE
                + "23700001C2001045 255975206000129555555              0011100000000111111 EMPRESA TESTE XYZ                                                     AV TESTE                      111                 SAO PAULO           01104010SP01                " + FileUtil.NEW_LINE
                + "2370000300001A0000002370123450000000012345 JOSE DA SILVA 1               1                   " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000000582                    00000000000000000000000                                                    0          " + FileUtil.NEW_LINE
                + "2370001300002B   100011122233455                                                                                     00000000  " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE                                                                                                                                                                                        
                + "23700015         000002000000000000000582000000000000000000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "23700001C2003045 255975206000129555555              0011100000000111111 EMPRESA TESTE XYZ                                                     AV TESTE                      111                 SAO PAULO           01104010SP01                " + FileUtil.NEW_LINE
                + "2370000300001A0000180330123450000000012345 JOSE DA SILVA 2               1                   " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000001000                    00000000000000000000000                                          00010CC   0          " + FileUtil.NEW_LINE
                + "2370001300002B   100011122233455                                                                                     00000000  " + getDataFormatada(dataHoraGeracao) + "000000000001000000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE                                                                                                                                                                                        
                + "23700015         000002000000000000001000000000000000000000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "23700001C2045045 255975206000129555555              0011100000000111111 EMPRESA TESTE XYZ                                                     AV TESTE                      111                 SAO PAULO           01104010SP01                " + FileUtil.NEW_LINE
                + "2370000300001A0000090000000000000000000000 JOSE DA SILVA 3               1                   " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000001000                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "2370001300002B02 100011122233455                                                                                     00000000  " + getDataFormatada(dataHoraGeracao) + "000000000001000000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE                                                                                                                                                                                        
                + "23700015         000002000000000000001000000000000000000000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "23799999         000003000012000000                                                                                                                                                                                                             " + FileUtil.NEW_LINE);


                verificarResultadoPorLinha(remessaStr, textoEsperado);

                java.io.File file = new java.io.File("target/testRemessaPagamentoBradesco240.txt");
                //salvar o arquivo usando o nome completo do pacote para salvar
                java.io.FileWriter fw = new java.io.FileWriter(file);
                fw.write(remessaStr);
                fw.close();
                


                





                

	}

	@Test
	public synchronized void testRemessaPagamentoBB240() {

                String codigoBanco = "001";
		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("001"));

		Assert.assertEquals(false, remessa.isPermiteQtdeMoeda());
                Assert.assertEquals(false, remessa.isExigeCPFCNPJFavorecidoNoSegmentoA());

		String razaoSocial = "ACME S.A LTDA.";
		String cnpj = "111.222.33.0001/44";

		String numeroConvenio = "12345678";
		String agenciaComDigito = "0123-X";
		String contaComDigito = "0000123-X";
		String DAC = " ";
		int sequencialRegistro = 1;
                String numeroDocumento = "";

		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC);

		remessa.addNovoCabecalhoLote()
		.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.cedente(razaoSocial, cnpj)
		.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");

		BigDecimal valorPagamento = new BigDecimal(5.82);

                if (remessa.isExigeNumeroDocumento()) {
                        numeroDocumento = "1";
		}

		remessa.addNovoDetalheSegmentoA()
		.numeroDocumento("1")
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("033")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
                .numeroDocumento(numeroDocumento)
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro);

		sequencialRegistro++;

		remessa.addNovoDetalheSegmentoB()
		.numeroDocumento(1)
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();


		rodapeLote
		.quantidadeRegistros(24)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

		remessa.addNovoRodape()
		.quantidadeRegistros(18)
		.quantidadeLotes(1);

		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("00100000         2111222330001440123456780126       00123X000000000123X ACME S.A LTDA.                BANCO DO BRASIL S.A.                    1" + getDataHoraFormatada(dataHoraGeracao) + "00002204000000                                                                     " + FileUtil.NEW_LINE
                + "00100001C2001030 2111222330001440123456780126       00123X000000000123X ACME S.A LTDA.                                                        RUA XYZ                       123                 SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "0010000300001A0000000330123450000000012345 JOSE DA SILVA                                     " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000000582                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "0010001300002B   100011122233455                                                                                               " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000                              " + FileUtil.NEW_LINE
                + "00100015         000024000000000000000582000000000000000000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "00199999         000001000018000000                                                                                                                                                                                                             " + FileUtil.NEW_LINE);


              
                verificarResultadoPorLinha(remessaStr, textoEsperado);

	}

	@Test
	public void testRemessaPagamentoItau240() {
                        
                String codigoBanco = "341";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("341"));

		Assert.assertEquals(false, remessa.isPermiteQtdeMoeda());
                Assert.assertEquals(true, remessa.isExigeCPFCNPJFavorecidoNoSegmentoA());

                System.out.println("remessa.nomeArquivo = " + remessa.getNomeDoArquivo());

		String razaoSocial = "ACME S.A LTDA.";
		String cnpj = "111.222.33.0001/44";

		String numeroConvenio = "12345678";
		String agenciaComDigito = "0123-1";
		String contaComDigito = "00004567-8";
		String DAC = " ";
		int sequencialRegistro = 1;
                String numeroDocumento = "";
                String favorecidoCPFCNPJSegmentoA = "";

                //******************/
                //LOTE MESMO BANCO
                //******************/

		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC);

		remessa.addNovoCabecalhoLote()
		.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.cedente(razaoSocial, cnpj)
		.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");


		BigDecimal valorPagamento = new BigDecimal(5.82);

                if (remessa.isExigeNumeroDocumento()) {
                        numeroDocumento = "123456";
		}

                if (remessa.isExigeCPFCNPJFavorecidoNoSegmentoA()) {
                        favorecidoCPFCNPJSegmentoA = "111.222.33.0001/44";
                }

		remessa.addNovoDetalheSegmentoA()
		.numeroDocumento(numeroDocumento)
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("341")
		.favorecidoAgencia("8118-0")
		.favorecidoConta("07137-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
                .favorecidoCPFCNPJ(favorecidoCPFCNPJSegmentoA);



		sequencialRegistro++;

		remessa.addNovoDetalheSegmentoB()
		.numeroDocumento(1)
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

                //******************/
                //FIM LOTE MESMO BANCO
                //******************/

                //******************/
                //LOTE OUTRO BANCO
                //******************/
                sequencialRegistro++;

		remessa.addNovoCabecalhoLote()
		.forma(3)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.cedente(razaoSocial, cnpj)
		.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");


		BigDecimal valorPagamento2 = new BigDecimal(1200.00);

                if (remessa.isExigeNumeroDocumento()) {
                        numeroDocumento = "654321";
		}

                favorecidoCPFCNPJSegmentoA = "";
                if (remessa.isExigeCPFCNPJFavorecidoNoSegmentoA()) {
                        favorecidoCPFCNPJSegmentoA = "444.333.222.0001/11";
                }

		remessa.addNovoDetalheSegmentoA()
		.numeroDocumento(numeroDocumento)
		.formaDeTransferencia("018")
		.favorecidoCodigoBanco("001")
		.favorecidoAgencia("1178-9")
		.favorecidoConta("39346-0")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva BB")
		.dataPagamento(new Date())
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
                .favorecidoCPFCNPJ(favorecidoCPFCNPJSegmentoA);


		sequencialRegistro++;

		remessa.addNovoDetalheSegmentoB()
		.numeroDocumento(1)
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

	
                //******************/
                //FIM LOTE OUTRO BANCO
                //******************/


                //******************/
                //LOTE PIX
                //******************/
                sequencialRegistro++;

		remessa.addNovoCabecalhoLote()
		.forma(45)// 1 = Crédito em Conta Corrente mesmo banco | 3 = doc/ted outro banco 45 = PIX
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.cedente(razaoSocial, cnpj)
		.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");


		BigDecimal valorPagamento3 = new BigDecimal(1200.00);

                if (remessa.isExigeNumeroDocumento()) {
                        numeroDocumento = "654321";
		}

                favorecidoCPFCNPJSegmentoA = "";
                if (remessa.isExigeCPFCNPJFavorecidoNoSegmentoA()) {
                        favorecidoCPFCNPJSegmentoA = "444.333.222.0001/11";
                }

		remessa.addNovoDetalheSegmentoA()
		.numeroDocumento(numeroDocumento)
		.formaDeTransferencia("009") //009 = pix
		.favorecidoCodigoBanco("001")
		.favorecidoAgencia("1178-9")
		.favorecidoConta("39346-0")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva BB")
		.dataPagamento(new Date())
		.valor(valorPagamento3)
		.sequencialRegistro(sequencialRegistro)
                .favorecidoCPFCNPJ(favorecidoCPFCNPJSegmentoA);

		sequencialRegistro++;

		remessa.addNovoDetalheSegmentoB()
		.numeroDocumento(1)
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento3)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

	
                //******************/
                //FIM LOTE PIX
                //******************/


		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();

                BigDecimal valorTotalRegistros = valorPagamento.add(valorPagamento2).add(valorPagamento3);
                //5.82 + 1200.00 + 1200.00 = 2405.82

		rodapeLote
		.quantidadeRegistros(24)
		.valorTotalRegistros(valorTotalRegistros)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

		remessa.addNovoRodape()
		.quantidadeRegistros(18)
		.quantidadeLotes(1);

		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("34100000      080211122233000144                    00123 000000004567 8ACME S.A LTDA.                BANCO ITAU SA                           1" + getDataHoraFormatada(dataHoraGeracao) + "00000000000000                                                                     " + FileUtil.NEW_LINE
                + "34100001C2001040 211122233000144                    00123 000000004567 8ACME S.A LTDA.                                                        RUA XYZ                       00123               SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "3410000300001A00000034108118 000000007137 5JOSE DA SILVA                                     " + getDataFormatada(dataHoraGeracao) + "009000000000000000000000000000582                    00000000000000000000000                    000000111222330001440100010     0          " + FileUtil.NEW_LINE
                + "3410001300002B   100011122233455                              00000                                                  00000000  " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000                              " + FileUtil.NEW_LINE
                + "34100001C2041040 211122233000144                    00123 000000004567 8ACME S.A LTDA.                                                        RUA XYZ                       00123               SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "3410000300003A00000000101178 000000039346 0JOSE DA SILVA BB                                  " + getDataFormatada(dataHoraGeracao) + "009000000000000000000000000120000                    00000000000000000000000                    000000444333222000110100010     0          " + FileUtil.NEW_LINE
                + "3410001300004B   100011122233455                              00000                                                  00000000  " + getDataFormatada(dataHoraGeracao) + "000000000120000000000000000000000000000000000000000000000000000000000000000                              " + FileUtil.NEW_LINE
                + "34100001C2045040 211122233000144                    00123 000000004567 8ACME S.A LTDA.                                                        RUA XYZ                       00123               SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "3410000300005A00000900101178 000000039346 0JOSE DA SILVA BB                                  " + getDataFormatada(dataHoraGeracao) + "009000000000000000000000000120000                    00000000000000000000000                    000000444333222000110100010     0          " + FileUtil.NEW_LINE
                + "3410001300006B   100011122233455                              00000                                                  00000000  " + getDataFormatada(dataHoraGeracao) + "000000000120000000000000000000000000000000000000000000000000000000000000000                              " + FileUtil.NEW_LINE
                + "34100015         000024000000000000240582000000000000000000                                                                                                                                                                                     " + FileUtil.NEW_LINE
                + "34199999         000001000018                                                                                                                                                                                                                   " + FileUtil.NEW_LINE);
                                                                                                                                                                                                               
                  verificarResultadoPorLinha(remessaStr, textoEsperado);
	}

	@Test
	public void testRemessaPagamentoUnicred240() {
                        
                String codigoBanco = "136";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa("136"));

		Assert.assertEquals(true, remessa.isPermiteQtdeMoeda());
                Assert.assertEquals(false, remessa.isExigeCPFCNPJFavorecidoNoSegmentoA());


                System.out.println("remessa.nomeArquivo = " + remessa.getNomeDoArquivo());


		String razaoSocial = "ACME S.A LTDA.";
		String cnpj = "111.222.33.0001/44";

		String numeroConvenio = "12345678";
		String agenciaComDigito = "0123-1";
		String contaComDigito = "00001232-3";
		String DAC = " ";
		int sequencialRegistro = 1;
                String numeroDocumento = "";                


		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)                
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
                .banco("136","UNICRED")
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC);

		remessa.addNovoCabecalhoLote()
		.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
                .banco("136","UNICRED")
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.cedente(razaoSocial, cnpj)
		.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");

		BigDecimal valorPagamento = new BigDecimal(5.82);

                if (remessa.isExigeNumeroDocumento()) {
                        numeroDocumento = "123456";
		}

		remessa.addNovoDetalheSegmentoA()
		.numeroDocumento(numeroDocumento)
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("341")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento)
                .banco("136","UNICRED")
		.sequencialRegistro(sequencialRegistro);

		sequencialRegistro++;

		remessa.addNovoDetalheSegmentoB()
		.numeroDocumento(1)
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
                .banco("136","UNICRED")
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();


		rodapeLote
		.quantidadeRegistros(24)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
                .banco("136","UNICRED")
		.setValue("lote",1);

		remessa.addNovoRodape()
		.quantidadeRegistros(18)                
		.quantidadeLotes(1).banco("136","UNICRED");

		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("13600000         211122233000144000000000000123456780012310000000012323 ACME S.A LTDA.                UNICRED                                 1" + getDataHoraFormatada(dataHoraGeracao) + "00002210300000                                                                     " + FileUtil.NEW_LINE
                + "13600001C2001046 211122233000144000000000000123456780012310000000012323 ACME S.A LTDA.                                                        RUA XYZ                       123                 SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "1360000300001A0000003410123450000000012345 JOSE DA SILVA                                     " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000000582                                                                                   0100010     0          " + FileUtil.NEW_LINE
                + "1360001300002B   100011122233455                                                                                               " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000                              " + FileUtil.NEW_LINE
                + "13600015         000024000000000000000582000000000000582000                                                                                                                                                                                     " + FileUtil.NEW_LINE
                + "13699999         000001000018000000                                                                                                                                                                                                             " + FileUtil.NEW_LINE);

                verificarResultadoPorLinha(remessaStr, textoEsperado);

	}

	@Test
	public void testRemessaPagamentoSicredi240() {
                        
                String codigoBanco = "748";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa(codigoBanco));

                System.out.println("remessa.nomeArquivo = " + remessa.getNomeDoArquivo());

		Assert.assertEquals(true, remessa.isPermiteQtdeMoeda());

		String razaoSocial = "ACME S.A LTDA.";
		String cnpj = "111.222.33.0001/44";

		String numeroConvenio = "2NZ6";
		//testando preenchimento automatico do digito veriricador como 0
		String agenciaComDigito = "0123";
		String contaComDigito = "0000123-4";
		String DAC = " ";
		int sequencialRegistro = 1;

                

		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC);

		remessa.addNovoCabecalhoLote()
				.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("Rua XYZ","123","","São Paulo","12345-123", "SP");


		BigDecimal valorPagamento = new BigDecimal(5.82);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("033")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva")
		.dataPagamento(new Date())
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro);

		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",1);

		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();

		rodapeLote
		.quantidadeRegistros(24)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco, numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",1);

		remessa.addNovoRodape()
		.quantidadeRegistros(14)
		.quantidadeLotes(1);

		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("74800000         2111222330001442NZ6                0012300000000001234 ACME S.A LTDA.                SICREDI                                 1" + getDataHoraFormatada(dataHoraGeracao) + "00002208200000                                                                     " + FileUtil.NEW_LINE 
                + "74800001C2041042 2111222330001442NZ6                0012300000000001234 ACME S.A LTDA.                                                        RUA XYZ                       123                 SAO PAULO           12345123SP                  " + FileUtil.NEW_LINE
                + "7480000300001A0000000330123450000000012345 JOSE DA SILVA                                     " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000000582                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "7480001300001B   100011122233455                                                                                               " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE
                + "74800015         000024000000000000000582000000000000582000                                                                                                                                                                                     " + FileUtil.NEW_LINE
                + "74899999         000001000014000000                                                                                                                                                                                                             " + FileUtil.NEW_LINE);


                Assert.assertEquals(textoEsperado.toString(),  remessaStr);


	}


        @Test
	public void testRemessaPagamentoSicoob240() throws IOException {
                        
                String codigoBanco = "756";

		RemessaFacade remessa = new RemessaFacade(LayoutsSuportados.getLayoutCNAB240PagamentoRemessa(codigoBanco));

		Assert.assertEquals(true, remessa.isPermiteQtdeMoeda());
                Assert.assertEquals(false, remessa.isExigeCPFCNPJFavorecidoNoSegmentoA());

		System.out.println("remessa.nomeArquivo = " + remessa.getNomeDoArquivo());


		String razaoSocial = "EMPRESA TESTE XYZ";
		String cnpj = "55.975.206/0001-29";

		String numeroConvenio = "555";
		
                //testando preenchimento automatico do digito verificador como 0
		String agenciaComDigito = "5122-8";
		String contaComDigito = "14044-9";
		String DAC = " ";
		int sequencialLote = 1;
		int sequencialRegistro = 1;

		remessa.addNovoCabecalho()
		.dataGeracao(dataHoraGeracao)
		.horaGeracao(dataHoraGeracao)
		.sequencialArquivo(22)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC);


                // .convenio(grupoGestorContaBancaria.getBanco().getCodigoFebraban(),
                // grupoGestorContaBancaria.getConvenioBancario(), grupoGestorContaBancaria.getAgencia(),
                // grupoGestorContaBancaria.getConta(), " ");

                //vai ser 1 lote para transferencia no mesmo banco (transf mesmo banco), outro lote para outros bancos (TED)

                //LOTE 1 - MESMO BANCO
		remessa.addNovoCabecalhoLote()
				.forma(1)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("AV TESTE","111","","São Paulo","01104010", "SP")
				.setValue("lote", sequencialLote);


		BigDecimal valorPagamento = new BigDecimal(5.82);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("000")
		.favorecidoCodigoBanco("756")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva 1")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("lote", sequencialLote);

		sequencialRegistro++;
                
		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",sequencialLote);


		RodapeArquivo rodapeLote = remessa.addNovoRodapeLote();

		rodapeLote
		.quantidadeRegistros(2)
		.valorTotalRegistros(valorPagamento)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",sequencialLote);

                //******************/
                //FIM LOTE MESMO BANCO
                //******************/

                //******************/
                //LOTE OUTRO BANCO
                //******************/
                sequencialRegistro = 1;
				sequencialLote = 2;
		remessa.addNovoCabecalhoLote()
				.forma(3)// 1 = Crédito em Conta Corrente mesmo banco 3 = doc/ted outro banco
				.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
				.cedente(razaoSocial, cnpj)
				.endereco("AV TESTE","111","","São Paulo","01104010", "SP")
				.setValue("lote", sequencialLote);


		BigDecimal valorPagamento2 = new BigDecimal(10);

		remessa.addNovoDetalheSegmentoA()
		.formaDeTransferencia("018")
		.favorecidoCodigoBanco("033")
		.favorecidoAgencia("1234-5")
		.favorecidoConta("1234-5")
		 //testando sanitize remover acentos e transformar em maiusculo
		.favorecidoNome("José da Silva 2")
		.dataPagamento(dataHoraGeracao)
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
		.setValue("lote", sequencialLote);

                sequencialRegistro++;
                
		remessa.addNovoDetalheSegmentoB()
		.favorecidoTipoInscricao("1")
		 //testando sanitize apenasNumeros
		.favorecidoCPFCNPJ("111.222.33/4-----55")
		.valor(valorPagamento2)
		.sequencialRegistro(sequencialRegistro)
		.setValue("data",new Date())
		.setValue("lote",sequencialLote);


		RodapeArquivo rodapeLote2 = remessa.addNovoRodapeLote();

		rodapeLote2
		.quantidadeRegistros(2)
		.valorTotalRegistros(valorPagamento2)
		.cedente(razaoSocial, cnpj)
		.convenio(codigoBanco,numeroConvenio, agenciaComDigito, contaComDigito, DAC)
		.setValue("lote",sequencialLote);

                //FIM LOTE 2 - OUTROS BANCOS
              
                remessa.addNovoRodape() 
		.quantidadeRegistros(10)
		.quantidadeLotes(2);


		String remessaStr = remessa.render();
		System.out.println(remessaStr);

                StringBuilder textoEsperado = new StringBuilder();

                textoEsperado.append("75600000         255975206000129555                 05122 00000001404490EMPRESA TESTE XYZ             SICOOB                                  1" + getDataHoraFormatada(dataHoraGeracao) + "00002208700000                                                                     " + FileUtil.NEW_LINE
                + "75600011C2001045 255975206000129555                 05122 00000001404490EMPRESA TESTE XYZ                                                     AV TESTE                      111                 SAO PAULO           01104010SP01                " + FileUtil.NEW_LINE
                + "7560001300001A00000075601234 0000000012345 JOSE DA SILVA 1                                   " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000000582                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "7560001300002B   100011122233455                                                                                     00000000  " + getDataFormatada(dataHoraGeracao) + "000000000000582000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE                                                                                                                                                                                        
                + "75600015         000002000000000000000582000000000000582000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "75600021C2041045 255975206000129555                 05122 00000001404490EMPRESA TESTE XYZ                                                     AV TESTE                      111                 SAO PAULO           01104010SP01                " + FileUtil.NEW_LINE
                + "7560002300001A00001803301234 0000000012345 JOSE DA SILVA 2                                   " + getDataFormatada(dataHoraGeracao) + "BRL000000000000000000000000001000                    00000000000000000000000                                        0100010     0          " + FileUtil.NEW_LINE
                + "7560002300002B   100011122233455                                                                                     00000000  " + getDataFormatada(dataHoraGeracao) + "000000000001000000000000000000000000000000000000000000000000000000000000000               0              " + FileUtil.NEW_LINE
                + "75600025         000002000000000000001000000000000001000000000000                                                                                                                                                                               " + FileUtil.NEW_LINE
                + "75699999         000002000010000000                                                                                                                                                                                                             " + FileUtil.NEW_LINE);


                verificarResultadoPorLinha(remessaStr, textoEsperado);

                java.io.File file = new java.io.File("target/testRemessaPagamentoSicoob240.txt");
                //salvar o arquivo usando o nome completo do pacote para salvar
                java.io.FileWriter fw = new java.io.FileWriter(file);
                fw.write(remessaStr);
                fw.close();
                
	}


    public TagLayout layoutGenericoTest() {
        TagLayout flatfileLayout = flatfile(
                /*
                <layout>
                <name>Arquivo-Febraban_CNAB400</name>
                <version>Version 00</version>
                <description>
                Layout padrão do Febraban
                </description>
                </layout>
                 */
                layout(
                        nome("Arquivo-Febraban_CNAB400"),
                        descricao("Layout padrão do Febraban"),
                        versao("01"),
                        banco(BancosSuportados.BANCO_DO_BRASIL.create()),
                        cnab(CNAB.CNAB_400),
                        servico(CNABServico.COBRANCA_REMESSA)
                ),
                /*
                        <GroupOfRecords>
                        <Record name="cabecalho" description="Protocolo de comunicação">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1" value="0" />
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="AGENCIA" length="4" type="INTEGER" padding="ZERO_LEFT" />
                        <Field name="DATA_ARQUIVO" length="6" type="DATE" format="DATE_DDMMYY" />
                 */
                cabecalho(
                        fcodigoRegistro().value(0),
                        fcodigoRetorno(),
                        fagencia().length(4),
                        fconta().length(7),
                        fdataGeracao()
                ),
                titulo(
                        fcodigoRegistro().value(7)
                ),
                /*
                        <Record name="TRAILLER">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1"  value="9"/>
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="Filler" length="2" />
                        <Field name="CODIGO_BANCO" length="3" />
                        <Field name="Filler" length="10" />
                        <Field name="QUANTIDADE_TITULOS" length="8" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="VALOR_TOTAL_TITULOS" length="15" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="Filler" length="8" />
                        </GroupOfFields>
                        </Record>
                 */
                rodape(
                        fcodigoRegistro().value(9),
                        fcodigoRetorno(),
                        fzero().length(2),
                        fbranco().length(3),
                        fzero().length(10),
                        fquantidadeRegistros().length(8),
                        fvalorTotalRegistros().length(8),
                        fzero().length(8)
                )
        );
        return flatfileLayout;
    }

    public TagLayout layoutBancoBradescoTest() {
        TagLayout flatfileLayout = flatfile(
                /*
                <layout>
                <name>Arquivo-Febraban_CNAB400</name>
                <version>Version 00</version>
                <description>
                Layout padrão do Febraban
                </description>
                </layout>
                 */
                layout(
                        nome("Arquivo-Febraban_CNAB400"),
                        descricao("Layout padrão do Febraban"),
                        versao("01"),
                        banco(BancosSuportados.BANCO_BRADESCO.create()),
                        cnab(CNAB.CNAB_400),
                        servico(CNABServico.COBRANCA_REMESSA)
                ),
                /*
                        <GroupOfRecords>
                        <Record name="cabecalho" description="Protocolo de comunicação">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1" value="0" />
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="AGENCIA" length="4" type="INTEGER" padding="ZERO_LEFT" />
                        <Field name="DATA_ARQUIVO" length="6" type="DATE" format="DATE_DDMMYY" />
                 */
                cabecalho(
                        fcodigoRegistro().value(0),
                        fcodigoRetorno(),
                        fagencia().length(4),
                        fconta().length(7),
                        fdataGeracao()
                ),
                titulo(
                        fcodigoRegistro().value(7)
                ),
                /*
                        <Record name="TRAILLER">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1"  value="9"/>
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="Filler" length="2" />
                        <Field name="CODIGO_BANCO" length="3" />
                        <Field name="Filler" length="10" />
                        <Field name="QUANTIDADE_TITULOS" length="8" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="VALOR_TOTAL_TITULOS" length="15" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="Filler" length="8" />
                        </GroupOfFields>
                        </Record>
                 */
                rodape(
                        fcodigoRegistro().value(9),
                        fcodigoRetorno(),
                        fzero().length(2),
                        fbranco().length(3),
                        fzero().length(10),
                        fquantidadeRegistros().length(8),
                        fvalorTotalRegistros().length(8),
                        fzero().length(8)
                )
        );
        return flatfileLayout;
    }

    public TagLayout layoutBancoSantander() {
        TagLayout flatfileLayout = flatfile(
                /*
                <layout>
                <name>Arquivo-Febraban_CNAB400</name>
                <version>Version 00</version>
                <description>
                Layout padrão do Febraban
                </description>
                </layout>
                 */
                layout(
                        nome("Arquivo-Febraban_CNAB400"),
                        descricao("Layout padrão do Febraban"),
                        versao("01"),
                        banco(BancosSuportados.BANCO_SANTANDER.create()),
                        cnab(CNAB.CNAB_400),
                        servico(CNABServico.COBRANCA_REMESSA)
                ),
                /*
                        <GroupOfRecords>
                        <Record name="cabecalho" description="Protocolo de comunicação">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1" value="0" />
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="AGENCIA" length="4" type="INTEGER" padding="ZERO_LEFT" />
                        <Field name="DATA_ARQUIVO" length="6" type="DATE" format="DATE_DDMMYY" />
                 */
                cabecalho(
                        fcodigoRegistro().value(0),
                        fcodigoRetorno(),
                        fagencia().length(4),
                        fconta().length(7),
                        fdataGeracao()
                ),
                titulo(
                        fcodigoRegistro().value(7)
                ),
                /*
                        <Record name="TRAILLER">
                        <GroupOfFields>
                        <IdType name="CODIGO_REGISTRO" length="1" position="1"  value="9"/>
                        <Field name="CODIGO_RETORNO" length="1" />
                        <Field name="Filler" length="2" />
                        <Field name="CODIGO_BANCO" length="3" />
                        <Field name="Filler" length="10" />
                        <Field name="QUANTIDADE_TITULOS" length="8" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="VALOR_TOTAL_TITULOS" length="15" type="BIGDECIMAL" format="DECIMAL_DD" />
                        <Field name="Filler" length="8" />
                        </GroupOfFields>
                        </Record>
                 */
                rodape(
                        fcodigoRegistro().value(9),
                        fcodigoRetorno(),
                        fzero().length(2),
                        fbranco().length(3),
                        fzero().length(10),
                        fquantidadeRegistros().length(8),
                        fvalorTotalRegistros().length(8),
                        fzero().length(8)
                )
        );
        return flatfileLayout;
    }
}
