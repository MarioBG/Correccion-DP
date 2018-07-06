
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.MiscellaneousRecord;

public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord, String> {

	@Override
	public String convert(final MiscellaneousRecord miscellaneousRecord) {
		String result = null;

		if (miscellaneousRecord == null)
			result = null;
		else
			result = String.valueOf(miscellaneousRecord.getId());

		return result;
	}

}
