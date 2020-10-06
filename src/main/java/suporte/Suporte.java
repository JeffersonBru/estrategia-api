package suporte;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Suporte {

	/**
	 * Obter data atual de acordo com o formato
	 * @param format
	 * @return
	 */
	public String obterData(String format) {
		DateFormat formato = new SimpleDateFormat(format);
		Date data = new Date();
		return formato.format(data).toString();
	}
	
	/**
	 * Randomizar qualquer array de string
	 * @param array
	 * @return
	 */
	public String random(String array[]) {
		Random generator = new Random();
		int randomIndex = generator.nextInt(array.length);
		return array[randomIndex];
	}
	
}
