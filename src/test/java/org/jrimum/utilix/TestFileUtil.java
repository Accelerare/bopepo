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
 * Created at: 24/02/2010 - 14:13:34
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
 * Criado em: 24/02/2010 - 14:13:34
 * 
 */

package org.jrimum.utilix;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.FileUtil;
import org.junit.Test;

/**
 * 
 * Teste da classe FileUtil.
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 *
 * @version 0.2.3
 * 
 * @since 0.2
 */
public class TestFileUtil {

	@Test(expected = NullPointerException.class)
	public void testBytes2FilePathNameNull() throws FileNotFoundException, IOException {
		FileUtil.bytes2File(null, new byte[] {});
	}
	
	@Test(expected = NullPointerException.class)
	public void testBytes2FileBytesNull() throws FileNotFoundException, IOException {
		FileUtil.bytes2File("/home", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBytes2FilePathNameEmpty() throws FileNotFoundException, IOException {
		FileUtil.bytes2File(StringUtils.EMPTY, new byte[] {});
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBytes2FilePathNameBlank() throws FileNotFoundException, IOException {
		FileUtil.bytes2File(StringUtils.EMPTY, new byte[] {});
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testBytes2FileFileNotFound() throws FileNotFoundException, IOException {
		FileUtil.bytes2File("/home/fileInexistente", new byte[] {1, 2, 3});
	}
	
	@Test
	public void testBytes2File() throws FileNotFoundException, IOException {
		
		byte valor1 = (byte) 1;
		byte valor2 = (byte) 2;
		byte valor3 = (byte) 3;
		
		//Criar o arquivo antes de testar
		Assert.assertNotNull(FileUtil.bytes2File("/home/romulo/teste/fileExistente", new byte[] {valor1, valor2, valor3}));
	}
	
	@Test(expected = NullPointerException.class)
	public void testBytes2StreamBytesNull() throws IOException {
		FileUtil.bytes2Stream(null);
	}
	
	@Test
	public void testBytes2Stream() throws IOException {
		
		byte valor1 = (byte) 1;
		byte valor2 = (byte) 2;
		byte valor3 = (byte) 3;

		ByteArrayOutputStream out = FileUtil.bytes2Stream(new byte[] {valor1, valor2, valor3});
		
		Assert.assertNotNull(out);
		
		out.close();
	}
}
