package com.whty.platform.modules.hongcheng.utils;

import org.jpos.iso.AsciiInterpreter;
import org.jpos.iso.BcdPrefixer;
import org.jpos.iso.ISOStringFieldPackager;
import org.jpos.iso.NullPadder;

public class IFAB_LLLCHAR extends ISOStringFieldPackager {
	public IFAB_LLLCHAR() {
		super(NullPadder.INSTANCE, AsciiInterpreter.INSTANCE, BcdPrefixer.LLL);
	}

	public IFAB_LLLCHAR(int len, String description) {
		super(len, description, NullPadder.INSTANCE, AsciiInterpreter.INSTANCE,
				BcdPrefixer.LLL);
		checkLength(len, 999);
	}

	@Override
	public void setLength(int len) {
		checkLength(len, 999);
		super.setLength(len);
	}
}
