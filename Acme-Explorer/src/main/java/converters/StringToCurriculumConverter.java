
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.CurriculumRepository;
import domain.Curriculum;

public class StringToCurriculumConverter implements Converter<String, Curriculum> {

	@Autowired
	CurriculumRepository	curriculumRepository;


	@Override
	public Curriculum convert(final String text) {
		Curriculum result = null;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.curriculumRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException();
		}

		return result;
	}
}
