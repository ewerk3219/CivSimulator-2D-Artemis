package com.sim.subSystems.entity.util;

import java.util.Random;

/*
 * To access this, go to Simulator.diceRoller
 * Don't need to make a local object of this.
 */
public class DiceRoller {

	private Random random = new Random();

	public int roll(int count, int magnitude) {
		int sum = 0;
		for (int i = 0; i < count; i++) {
			sum += random.nextInt(magnitude) + 1;
		}
		return sum;
	}

	public int roll100() {
		return random.nextInt(100) + 1;
	}

	public int roll20() {
		return random.nextInt(20) + 1;
	}

	/*
	 * Allows for critical rolls. critMin is the minimum roll required for a
	 * critical. If there is a critical, then 10 is added to the roll and
	 * returned.
	 */
	public int roll20WithCrit(int critMin) {
		int num = roll20();
		if (num >= critMin) {
			return num + 10;
		} else
			return num;
	}

	public int roll12() {
		return random.nextInt(12) + 1;
	}

	public int roll10() {
		return random.nextInt(10) + 1;
	}

	public int roll8() {
		return random.nextInt(8) + 1;
	}

	public int roll6() {
		return random.nextInt(6) + 1;
	}

	public int roll4() {
		return random.nextInt(4) + 1;
	}
}
