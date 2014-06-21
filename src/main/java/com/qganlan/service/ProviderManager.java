package com.qganlan.service;

import java.util.List;

import com.qganlan.model.Provider;
import com.qganlan.model.ProviderClass;

public interface ProviderManager {
	public Provider getProvider(Long providerId);
	public List<ProviderClass> getProviderClassList();
}
