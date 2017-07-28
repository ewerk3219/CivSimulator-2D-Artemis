package com.sim.util;

import com.sim.Simulator;
import com.sim.simStates.SimulationState;

public class MathUtil {

	public static float percentDisFromOrigin(float axisCoord, double maxDim) {
		if (maxDim <= 0) {
			throw new IllegalArgumentException(
					"maxDim cannot be less than or equal to 0");
		}
		if (axisCoord == 0) {
			return 0;
		} else {
			return (float) (axisCoord / maxDim);
		}
	}

	public static float percentDisOffset(float axisCoord, double maxDim,
			float multiplier) {
		return percentDisFromOrigin(axisCoord, maxDim) * multiplier;
	}

	public static float distanceFromOrigin(float x, float y) {
		double mag = Math.pow(x, 2) + Math.pow(y, 2);
		mag = Math.sqrt(mag);
		return (float) mag;
	}
}
