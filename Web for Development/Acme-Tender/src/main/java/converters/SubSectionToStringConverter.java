
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SubSection;

@Component
@Transactional
public class SubSectionToStringConverter implements Converter<SubSection, String> {

	@Override
	public String convert(final SubSection subSection) {
		String result;

		if (subSection == null)
			result = null;
		else
			result = String.valueOf(subSection.getId());

		return result;
	}

}
