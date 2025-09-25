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
 * Created at: 30/03/2008 - 18:17:40
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
 * Criado em: 30/03/2008 - 18:17:40
 * 
 */

package org.jrimum.utilix;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.jrimum.utilix.ObjectUtil.isNotNull;


public class BigDecimalUtil {


	public static BigDecimal toBigDecimal(String number, int casasDecimais) {

		if (number == null || number.isEmpty()) {
			return null;
		}

		try {
			// Remove any grouping separators and replace the decimal separator
			String fixedString = number.replace(".", "").replace(",", "");

			// Convert the normalized string to BigDecimal
			BigDecimal bd = new BigDecimal(fixedString);

			// Compute the division factor as 10^casasDecimais
			BigDecimal divisor = BigDecimal.TEN.pow(casasDecimais);

			// Divide to adjust the decimal place
			bd = bd.divide(divisor, casasDecimais, RoundingMode.HALF_UP);

			return bd;
		} catch (Exception e) {
			return null;
		}
	}

}
