package com.globalresolvewws.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.globalresolvewws.*;

import org.junit.Test;

public class RiskModelTest extends AndroidTestCase {

	@Test
	public void test() {
		RiskModel RM = new RiskModel();
		// should catch some alarms
		RM.calculateRisk(70, 60, 70, 0);
		RM.checkForAlarm();
		assertEquals(RM.checkForAlarm(), alarmEnum.MEDIUM);
		RM.setCurrentRisk(1);
		RM.checkForAlarm();
		assertEquals(RM.checkForAlarm(), alarmEnum.LOW);
		RM.setCurrentRisk(9);
		RM.checkForAlarm();
		assertEquals(RM.checkForAlarm(), alarmEnum.HIGH);
	}
}
