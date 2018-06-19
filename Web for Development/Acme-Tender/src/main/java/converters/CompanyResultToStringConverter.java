
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CompanyResult;

@Component
@Transactional
public class CompanyResultToStringConverter implements Converter<CompanyResult, String> {

	@Override
	public String convert(final CompanyResult data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
