package suporte;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LeitorProperties {
	
	private File arquivo;
	
	public LeitorProperties(String arq){
		arquivo = new File(arq);
	}
	/**
	 * Método para obter properties do arquivo
	 * @throws Exception 
	 */
	public Properties getProperties() {
		Properties props = new Properties();
		try{
			FileInputStream fis = new FileInputStream(arquivo.getAbsolutePath());
			props.load(fis);
			fis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return props;
	}
}
