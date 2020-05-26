package util;

import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import domain.Article;

@Converter
public class StringToMapConverter implements AttributeConverter<Article,String> {	@Override
	public String convertToDatabaseColumn(Article arg0) {
		System.out.println("RUNNING CONVERTER A");
		// TODO Auto-generated method stub
		return null;
	}	@Override
	public Article convertToEntityAttribute(String arg0) {
		System.out.println("RUNNING CONVERTER B");
		// TODO Auto-generated method stub
		return null;
	}
	}


//@Converter
//public class StringToMapConverter implements AttributeConverter<String,Map<Article,Integer>> {
//
//	@Override
//	public Map<Article, Integer> convertToDatabaseColumn(String arg0) {
//		System.out.println("RUNNING CONVERTER A");
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String convertToEntityAttribute(Map<Article, Integer> arg0) {
//		System.out.println("RUNNING CONVERTER B");
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
