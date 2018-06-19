package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ActorRepository;

import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor>{
	

	@Autowired
	private ActorRepository repository;
	
	@Override
	public Actor convert(String text) {
		Actor result;
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
