package com.tincore.gsp.server.domain;

public enum SessionType {

	running(true, false, false, true), //
	running_indoor(true, false, true, running, true), //
	walking(true, false, false, false), //
	walking_indoor(true, false, true, walking, false), //
	cycling(false, true, false, false), //
	cycling_indoor(false, true, true, cycling, false), //
	other(true, false, false, false), //
	other_indoor(false, true, true, other, false), //
	global(true, false, false, other, false);

	public final boolean onFoot;
	public final boolean onRun;
	public final boolean onBicycle;
	public final boolean indoor;

	private final SessionType parentType;

	SessionType(boolean onfoot, boolean onBicycle, boolean indoor, boolean onRun) {
		this(onfoot, onBicycle, indoor, null, onRun);
	}

	SessionType(boolean onFoot, boolean onBicycle, boolean indoor, SessionType parentType, boolean onRun) {
		this.onFoot = onFoot;
		this.onRun = onRun;
		this.onBicycle = onBicycle;
		this.parentType = parentType;
		this.indoor = indoor;
	}

	public SessionType getParentType() {
		return parentType != null ? parentType : this;
	}

}
