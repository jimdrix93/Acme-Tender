
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tender;

@Component
@Transactional
public class TenderToStringConverter implements Converter<Tender, String> {

	@Override
	public String convert(final Tender data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
