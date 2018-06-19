
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TenderResultRepository;
import domain.TenderResult;

@Component
@Transactional
public class StringToTenderResultConverter implements Converter<String, TenderResult> {

	@Autowired
	private TenderResultRepository	repository;


	@Override
	public TenderResult convert(final String str) {
		TenderResult result;
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
