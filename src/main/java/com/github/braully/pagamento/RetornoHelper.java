package com.github.braully.pagamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RetornoHelper {

    public static boolean  validarArquivoRetornoCNAB240(String pathArquivoRemessaEnvio, String pathArquivoRemessaRetorno) {

        InputStreamReader remessaEnvio = null;
        InputStreamReader remessaRetorno = null;


        try {
            File arquivoEnvio = new File(pathArquivoRemessaEnvio);
            File arquivoRetorno = new File(pathArquivoRemessaRetorno);
            remessaEnvio = new InputStreamReader(Files.newInputStream(arquivoEnvio.toPath()));
            remessaRetorno = new InputStreamReader(Files.newInputStream(arquivoRetorno.toPath()));
            return validarArquivoRetornoCNAB240(remessaEnvio, remessaRetorno);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean  validarArquivoRetornoCNAB240(InputStreamReader remessaEnvio, InputStreamReader remessaRetorno) {

        List<String> linhasEnvio = new ArrayList<>();
        List<String> linhasRetorno = new ArrayList<>();

        //            remessaEnvio.mark(Integer.MAX_VALUE);
//            remessaRetorno.mark(Integer.MAX_VALUE);

        BufferedReader readerEnvio = new BufferedReader(remessaEnvio);
        BufferedReader readerRetorno = new BufferedReader(remessaRetorno);

        linhasEnvio = readerEnvio.lines().collect(Collectors.toList());
        linhasRetorno = readerRetorno.lines().collect(Collectors.toList());

        //            try {
//                remessaEnvio.reset();
//            } catch (IOException ignored) {
//            }
//            try {
//                remessaRetorno.reset();
//            } catch (IOException ignored) {
//            }
//        }

        return validarArquivoRetornoCNAB240(linhasEnvio, linhasRetorno);
    }

    public static boolean  validarArquivoRetornoCNAB240(List<String> remessaEnvio, List<String> remessaRetorno) {

        if (remessaEnvio == null || remessaRetorno == null) {
            return false;
        }

        if (remessaEnvio.size() != remessaRetorno.size()) {
            return false;
        }

        if (remessaEnvio.isEmpty()) {
            return false;
        }

        if (remessaEnvio.size() < 2) {
            return false;
        }


        //o horario dos arquivos estará diferente (colunas 143 a 151)
        String linhaEnvio = remessaEnvio.get(0);
        String linhaRetorno = remessaRetorno.get(0);

        if (linhaEnvio.substring(0, 142).equals(linhaRetorno.substring(0, 142)) ) {
            //primeira linha está correta
        }

        for (int i = 0; i < 2; i++) {
            if (linhaEnvio == null || !linhaEnvio.equals(linhaRetorno)) {
                return false;
            }
        }
        return true;
    }

    public static ResourceBundle bundleCodigosRejeicao(String codigoDoBanco) {
        return ResourceBundle.getBundle("properties/codigosRejeicao/banco" + codigoDoBanco);
    }

    public static boolean isPagamentoEfetivado(String codigoDoBanco, String codigoRetorno) {
        return "00".equals(codigoRetorno);

//        switch (codigoDoBanco) {
//            case "033": // santander
//            case "756": // sicoob
//
//            default:
//                return false;
//        }
    }

}