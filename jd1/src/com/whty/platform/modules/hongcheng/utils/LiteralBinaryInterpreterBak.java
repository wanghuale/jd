package com.whty.platform.modules.hongcheng.utils;

import org.jpos.iso.BinaryInterpreter;

public class LiteralBinaryInterpreterBak implements BinaryInterpreter {
	/**
	 * The only instance of this interpreter.
	 */
	public static final LiteralBinaryInterpreterBak INSTANCE = new LiteralBinaryInterpreterBak();

	/**
	 * Private constructor so we don't allow multiple instances.
	 */
	private LiteralBinaryInterpreterBak() {
	}

	/**
	 * Copies the input to the output.
	 * 
	 * @see org.jpos.iso.BinaryInterpreter#interpret(byte[], byte[], int)
	 */
	@Override
	public void interpret(byte[] data, byte[] b, int offset) {
		System.arraycopy(data, 0, b, offset, data.length);
	}

	/**
	 * Copies the data out of the byte array.
	 * 
	 * @see org.jpos.iso.BinaryInterpreter#uninterpret(byte[], int, int)
	 */
	@Override
	public byte[] uninterpret(byte[] rawData, int offset, int length) {
		byte[] ret = new byte[length];
		System.arraycopy(rawData, offset, ret, 0, length);
		return ret;
	}

	/**
	 * Returns nBytes because we are not doing any conversion.
	 * 
	 * @see org.jpos.iso.BinaryInterpreter#getPackedLength(int)
	 */
	@Override
	public int getPackedLength(int nBytes) {
		// TODO Auto-generated method stub
		return nBytes * 2;
	}
}
