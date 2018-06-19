
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CompanyResultRepository;
import domain.CompanyResult;

@Component
@Transactional
public class StringToCompanyResultConverter implements Converter<String, CompanyResult> {

	@Autowired
	private CompanyResultRepository	repository;


	@Override
	public CompanyResult convert(final String str) {
		CompanyResult result;
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
