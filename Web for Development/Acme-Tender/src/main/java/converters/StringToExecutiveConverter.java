
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ExecutiveRepository;
import domain.Executive;

@Component
@Transactional
public class StringToExecutiveConverter implements Converter<String, Executive> {

	@Autowired
	private ExecutiveRepository	repository;


	@Override
	public Executive convert(final String str) {
		Executive result;
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
