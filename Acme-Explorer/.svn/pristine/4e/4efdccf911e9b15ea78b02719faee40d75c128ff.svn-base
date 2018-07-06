
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HasValue;

@Component
@Transactional
public class HasValueToStringConverter implements Converter<HasValue, String> {

	@Override
	public String convert(final HasValue hasValue) {
		String result = null;

		if (hasValue == null)
			result = null;
		else
			result = String.valueOf(hasValue.getId());
		return result;
	}
}
