package com.globalresolvewws.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import com.globalresolvewws.*;

import org.junit.Test;

public class RiskModelTest {

	@Test
	public void test() {
		RiskModel RM = new RiskModel();
		// should catch some alarms
		RM.calculateRisk(70, 60, 70, 0);
		RM.checkForAlarm();
		assertEquals(RM.checkForAlarm(), alarmEnum.MEDIUM);
	}

}
