package com.qganlan.webapp.services;

import java.util.List;

import org.apache.tapestry5.SelectModel;

public interface SelectIdModelFactory {
	public SelectModel create(List<?> objects, String labelProperty, String idProperty);
}
