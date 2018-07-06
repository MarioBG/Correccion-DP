
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Tag;

public class TagToStringConverter implements Converter<Tag, String> {

	@Override
	public String convert(final Tag tag) {

		String result;

		if (tag == null)
			result = null;
		else
			result = String.valueOf(tag.getId());

		return result;
	}

}
