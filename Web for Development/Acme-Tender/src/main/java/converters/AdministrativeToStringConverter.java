
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrative;

@Component
@Transactional
public class AdministrativeToStringConverter implements Converter<Administrative, String> {

	@Override
	public String convert(final Administrative data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
