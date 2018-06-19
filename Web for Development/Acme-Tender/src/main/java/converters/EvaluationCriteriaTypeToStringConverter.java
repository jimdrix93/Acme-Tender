
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EvaluationCriteriaType;

@Component
@Transactional
public class EvaluationCriteriaTypeToStringConverter implements Converter<EvaluationCriteriaType, String> {

	@Override
	public String convert(final EvaluationCriteriaType element) {
		String result;

		if (element == null)
			result = null;
		else
			result = String.valueOf(element.getId());

		return result;
	}

}
