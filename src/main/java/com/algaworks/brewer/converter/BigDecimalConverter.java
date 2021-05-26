package com.algaworks.brewer.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class BigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String valor) {
		if (StringUtils.isEmpty(valor)) {
			return null;
		}
		final NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
		try {
			return new BigDecimal(nf.parse(valor).toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
