package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.OfferRepository;
import domain.Offer;

@Component
@Transactional
public class StringToOfferConverter implements Converter<String, Offer>{
	

	@Autowired
	private OfferRepository repository;
	
	@Override
	public Offer convert(String text) {
		Offer result;
		int id;		
		try {
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}
		} catch(Throwable oops) {
			throw new IllegalArgumentException(oops);
		}		
		return result;
	}	
}
