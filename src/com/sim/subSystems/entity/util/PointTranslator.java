package com.sim.subSystems.entity.util;

import java.awt.Point;

import com.sim.Direction;

public class PointTranslator {

	public static Point getNewPoint(int x, int y, Direction direction) {
		Point p = new Point(x, y);
		switch (direction) {
		case NORTH:
			p.y++;
			break;
		case EAST:
			p.x++;
			break;
		case SOUTH:
			p.y--;
			break;
		case WEST:
			p.x--;
			break;
		case NORTHEAST:
			p.x++;
			p.y++;
			break;
		case SOUTHEAST:
			p.x++;
			p.y--;
			break;
		case SOUTHWEST:
			p.x--;
			p.y--;
			break;
		case NORTHWEST:
			p.x--;
			p.y++;
			break;
		}

		return p;
	}
}
