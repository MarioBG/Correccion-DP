
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

public class StringToProfessionalRecordConverter implements Converter<String, ProfessionalRecord> {

	@Autowired
	ProfessionalRecordRepository	professionalRecordRepository;


	@Override
	public ProfessionalRecord convert(final String text) {
		ProfessionalRecord result = null;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.professionalRecordRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
