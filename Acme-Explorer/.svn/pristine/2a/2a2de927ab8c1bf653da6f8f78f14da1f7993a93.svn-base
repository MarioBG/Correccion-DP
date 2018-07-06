
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.EducationRecord;

public class EducationRecordToStringConverter implements Converter<EducationRecord, String> {

	@Override
	public String convert(final EducationRecord educationRecord) {
		String result = null;

		if (educationRecord == null)
			result = null;
		else
			result = String.valueOf(educationRecord.getId());

		return result;
	}

}
