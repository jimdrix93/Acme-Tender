
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SubSection;
import repositories.SubSectionRepository;

@Component
@Transactional
public class StringToSubSectionConverter implements Converter<String, SubSection> {

	@Autowired
	SubSectionRepository subSectionRepository;


	@Override
	public SubSection convert(final String text) {
		SubSection result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.subSectionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
