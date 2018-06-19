
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministrativeRepository;
import domain.Administrative;

@Component
@Transactional
public class StringToAdministrativeConverter implements Converter<String, Administrative> {

	@Autowired
	private AdministrativeRepository	repository;


	@Override
	public Administrative convert(final String str) {
		Administrative result;
		Integer id;
		try {
			if (StringUtils.isEmpty(str))
				result = null;
			else {
				id = Integer.valueOf(str);
				result = this.repository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
