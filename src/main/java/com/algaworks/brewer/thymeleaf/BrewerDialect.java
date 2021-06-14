package com.algaworks.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import com.algaworks.brewer.thymeleaf.processor.OrderElementTagProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.algaworks.brewer.thymeleaf.processor.ClassForErrorAttributeProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("Algaworks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
