package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EvaluationCriteriaRepository;

import domain.EvaluationCriteria;

@Component
@Transactional
public class StringToEvaluationCriteriaConverter implements Converter<String, EvaluationCriteria>{
	

	@Autowired
	private EvaluationCriteriaRepository repository;
	
	@Override
	public EvaluationCriteria convert(String text) {
		EvaluationCriteria result;
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
