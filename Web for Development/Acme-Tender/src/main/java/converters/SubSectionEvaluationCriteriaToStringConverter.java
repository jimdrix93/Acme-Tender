
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SubSectionEvaluationCriteria;

@Component
@Transactional
public class SubSectionEvaluationCriteriaToStringConverter implements Converter<SubSectionEvaluationCriteria, String> {

	@Override
	public String convert(final SubSectionEvaluationCriteria data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
