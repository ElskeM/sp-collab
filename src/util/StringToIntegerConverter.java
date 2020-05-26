package util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import domain.Article;
import domain.Customer;

/**
 * @author Peter
 *
 */
@Converter(autoApply = true)
public class StringToIntegerConverter implements AttributeConverter<Integer,String> {

	@Override
	public String convertToDatabaseColumn(Integer arg0) {
		System.out.println("RUNNING CONVERTER A");
		// TODO Auto-generated method stub
		return arg0.toString();
	}

	@Override
	public Integer convertToEntityAttribute(String arg0) {
		System.out.println("RUNNING CONVERTER B");
		// TODO Auto-generated method stub
		return Integer.parseInt(arg0);
	}

}
