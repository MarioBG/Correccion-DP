
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.HasValueRepository;
import domain.HasValue;

public class StringToHasValueConverter implements Converter<String, HasValue> {

	@Autowired
	HasValueRepository	hasValueRepository;


	@Override
	public HasValue convert(final String text) {
		HasValue result = null;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.hasValueRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException();
		}

		return result;
	}
}
