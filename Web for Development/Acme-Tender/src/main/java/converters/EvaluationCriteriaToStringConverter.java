
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EvaluationCriteria;

@Component
@Transactional
public class EvaluationCriteriaToStringConverter implements Converter<EvaluationCriteria, String> {

	@Override
	public String convert(final EvaluationCriteria element) {
		String result;

		if (element == null)
			result = null;
		else
			result = String.valueOf(element.getId());

		return result;
	}

}
