package com.whty.platform.modules.hongcheng.utils;

import org.jpos.iso.LiteralBinaryInterpreter;

public class IFAB_LLLBINARY extends ISOBinaryFieldBakPackager {
	public IFAB_LLLBINARY() {
		super(LiteralBinaryInterpreter.INSTANCE, BcdPrefixerBak.LLL);
	}

	/**
	 * @param len
	 *            - field len
	 * @param description
	 *            symbolic descrption
	 */
	public IFAB_LLLBINARY(int len, String description) {
		super(len, description, LiteralBinaryInterpreter.INSTANCE,
				BcdPrefixerBak.LLL);
		checkLength(len, 999);
	}

	@Override
	public void setLength(int len) {
		checkLength(len, 999);
		super.setLength(len);
	}
}
