package com.whty.platform.modules.hongcheng.utils;

import org.jpos.iso.BcdPrefixer;
import org.jpos.iso.Prefixer;

public class BcdPrefixerBak implements Prefixer {
	/**
	 * A length prefixer for upto 9 chars. The length is encoded with 1 ASCII
	 * char representing 1 decimal digit.
	 */
	public static final BcdPrefixer L = new BcdPrefixer(1);
	/**
	 * A length prefixer for upto 99 chars. The length is encoded with 2 ASCII
	 * chars representing 2 decimal digits.
	 */
	public static final BcdPrefixer LL = new BcdPrefixer(2);
	/**
	 * A length prefixer for upto 999 chars. The length is encoded with 3 ASCII
	 * chars representing 3 decimal digits.
	 */
	public static final BcdPrefixer LLL = new BcdPrefixer(3);
	/**
	 * A length prefixer for upto 9999 chars. The length is encoded with 4 ASCII
	 * chars representing 4 decimal digits.
	 */
	public static final BcdPrefixer LLLL = new BcdPrefixer(4);
	/**
	 * A length prefixer for upto 99999 chars. The length is encoded with 5
	 * ASCII chars representing 5 decimal digits.
	 */
	public static final BcdPrefixer LLLLL = new BcdPrefixer(5);

	/** The number of digits allowed to express the length */
	private int nDigits;

	public BcdPrefixerBak(int nDigits) {
		this.nDigits = nDigits;
	}

	/**
	 * @see org.jpos.iso.Prefixer#encodeLength(int, byte[])
	 */
	@Override
	public void encodeLength(int length, byte[] b) {
		for (int i = getPackedLength() - 1; i >= 0; i--) {
			int twoDigits = length % 100;
			length /= 100;
			b[i] = (byte) (((twoDigits / 10) << 4) + twoDigits % 10);
		}
	}

	/**
	 * @see org.jpos.iso.Prefixer#decodeLength(byte[], int)
	 */
	@Override
	public int decodeLength(byte[] b, int offset) {
		int len = 0;
		for (int i = 0; i < (nDigits + 1) / 2; i++) {
			len = 100 * len + ((b[offset + i] & 0xF0) >> 4) * 10
					+ ((b[offset + i] & 0x0F));
		}
		return len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jpos.iso.Prefixer#getLengthInBytes()
	 */
	@Override
	public int getPackedLength() {
		return (nDigits + 1);
	}
}
