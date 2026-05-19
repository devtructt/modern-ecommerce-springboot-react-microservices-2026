package com.devtructt.ecommerce.commondataservice.util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelMapperUtil {

	private final ModelMapper modelMapper;

	public <S, D> D map(S source, Class<D> destinationClass) {
		if (source == null) {
			return null;
		}
		return modelMapper.map(source, destinationClass);
	}

	public <S, D> List<D> mapList(List<S> source, Class<D> destinationClass) {
		if (CollectionUtils.isEmpty(source)) {
			return Collections.emptyList();
		}

		Type listType = new TypeToken<List<D>>() {}.getType();
		return modelMapper.map(source, listType);
	}
}