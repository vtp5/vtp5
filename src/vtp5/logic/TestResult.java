package vtp5.logic;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*VTP5 Copyright (C) 2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along*/
public class TestResult implements Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private File[] files;
	private double successRate;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private Date date = new Date();

	public TestResult(File[] files, double successRate) {
		this.files = files;
		this.successRate = successRate;
		df.format(date);
	}

}
