/*
 * PopulateDatabase.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.util.Calendar;
import java.util.Date;

public class SandBox {

	public static void main(final String[] args) {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		System.out.println("--" + calendar.getTime() + "--");
		calendar.setTime(now); // Configuramos la fecha actual
		calendar.add(Calendar.MONTH, -5); // numero de días a añadir, o restar en caso de días<0
		Date from = calendar.getTime();
		calendar.add(Calendar.MONTH, 5); // numero de días a añadir, o restar en caso de días<0
		Date to = calendar.getTime();
		System.out.println(now);
		System.out.println(from);
		System.out.println(to);
	}
}
