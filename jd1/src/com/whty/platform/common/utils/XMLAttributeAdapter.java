package com.whty.platform.common.utils;

import java.util.Map;

public interface XMLAttributeAdapter<E> {
	public abstract void rowdata(String key, String value, Map attribute ,E Bean);
}
