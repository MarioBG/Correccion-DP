
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.EndorserRecord;

public class EndorserRecordToStringConverter implements Converter<EndorserRecord, String> {

	@Override
	public String convert(final EndorserRecord endorserRecord) {
		String result = null;

		if (endorserRecord == null)
			result = null;
		else
			result = String.valueOf(endorserRecord.getId());

		return result;
	}

}
