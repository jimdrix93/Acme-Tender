
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Offer;

@Component
@Transactional
public class OfferToStringConverter implements Converter<Offer, String> {

	@Override
	public String convert(final Offer data) {
		String result;

		if (data == null)
			result = null;
		else
			result = String.valueOf(data.getId());

		return result;
	}

}
