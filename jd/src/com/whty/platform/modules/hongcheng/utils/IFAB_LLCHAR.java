package com.whty.platform.modules.hongcheng.utils;

import org.jpos.iso.AsciiInterpreter;
import org.jpos.iso.BcdPrefixer;
import org.jpos.iso.ISOStringFieldPackager;
import org.jpos.iso.NullPadder;

public class IFAB_LLCHAR extends ISOStringFieldPackager {
	public IFAB_LLCHAR() {
		super(NullPadder.INSTANCE, AsciiInterpreter.INSTANCE, BcdPrefixer.LL);
	}

	public IFAB_LLCHAR(int len, String description) {
		super(len, description, NullPadder.INSTANCE, AsciiInterpreter.INSTANCE,
				BcdPrefixer.LL);
		checkLength(len, 999);
	}

	@Override
	public void setLength(int len) {
		checkLength(len, 999);
		super.setLength(len);
	}
}
