package com.whty.platform.modules.kamen.Exception;

import com.whty.platform.modules.kamen.entity.Charge;

public class KamenException extends Exception {

	private static final long serialVersionUID = 1L;

	private Charge charge;

	public KamenException(Charge charge) {
		super(charge.getErrorCode() + "," + charge.getErrorMsg());
		this.charge = charge;
	}

	public Charge getCharge() {
		return charge;
	}

	public void setCharge(Charge charge) {
		this.charge = charge;
	}

}
