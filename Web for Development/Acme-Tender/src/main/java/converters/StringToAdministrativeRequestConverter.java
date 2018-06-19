
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AdministrativeRequest;
import repositories.AdministrativeRequestRepository;

@Component
@Transactional
public class StringToAdministrativeRequestConverter implements Converter<String, AdministrativeRequest> {

	@Autowired
	private AdministrativeRequestRepository administrativeRequestRepository;


	@Override
	public AdministrativeRequest convert(final String str) {
		AdministrativeRequest result;
		Integer id;
		try {
			if (StringUtils.isEmpty(str))
				result = null;
			else {
				id = Integer.valueOf(str);
				result = this.administrativeRequestRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
