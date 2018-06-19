
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CollaborationRequest;
import repositories.CollaborationRequestRepository;

@Component
@Transactional
public class StringToCollaborationRequestConverter implements Converter<String, CollaborationRequest> {

	@Autowired
	private CollaborationRequestRepository collaborationRequestRepository;


	@Override
	public CollaborationRequest convert(final String str) {
		CollaborationRequest result;
		Integer id;
		try {
			if (StringUtils.isEmpty(str))
				result = null;
			else {
				id = Integer.valueOf(str);
				result = this.collaborationRequestRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
