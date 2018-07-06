
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.ProfessionalRecord;

public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord, String> {

	@Override
	public String convert(final ProfessionalRecord professionalRecord) {
		String result = null;

		if (professionalRecord == null)
			result = null;
		else
			result = String.valueOf(professionalRecord.getId());

		return result;
	}

}
