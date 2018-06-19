package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EvaluationCriteriaTypeRepository;

import domain.EvaluationCriteriaType;

@Component
@Transactional
public class StringToEvaluationCriteriaTypeConverter implements Converter<String, EvaluationCriteriaType>{
	

	@Autowired
	private EvaluationCriteriaTypeRepository repository;
	
	@Override
	public EvaluationCriteriaType convert(String text) {
		EvaluationCriteriaType result;
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
