/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * Licensed under The MIT License (Massachusetts Institute of Technology License)
 * 
 * See http://opensource.org/licenses/MIT
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.sequencediagram.teoz;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.Iterator;

import net.sourceforge.plantuml.ISkinParam;
import net.sourceforge.plantuml.Log;
import net.sourceforge.plantuml.graphic.StringBounder;
import net.sourceforge.plantuml.real.Real;
import net.sourceforge.plantuml.sequencediagram.Event;
import net.sourceforge.plantuml.sequencediagram.Message;
import net.sourceforge.plantuml.skin.Area;
import net.sourceforge.plantuml.skin.ArrowComponent;
import net.sourceforge.plantuml.skin.ArrowConfiguration;
import net.sourceforge.plantuml.skin.Component;
import net.sourceforge.plantuml.skin.Context2D;
import net.sourceforge.plantuml.skin.rose.Rose;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UTranslate;

public class CommunicationTileSelf extends AbstractTile implements TileWithUpdateStairs {

	private final LivingSpace livingSpace1;
	private final Message message;
	private final Rose skin;
	private final ISkinParam skinParam;
	private final LivingSpaces livingSpaces;

	public Event getEvent() {
		return message;
	}

	@Override
	public double getYPoint(StringBounder stringBounder) {
		return getComponent(stringBounder).getYPoint(stringBounder);
	}

	public CommunicationTileSelf(LivingSpace livingSpace1, Message message, Rose skin, ISkinParam skinParam,
			LivingSpaces livingSpaces) {
		this.livingSpace1 = livingSpace1;
		this.livingSpaces = livingSpaces;
		this.message = message;
		this.skin = skin;
		this.skinParam = skinParam;
	}

	// private boolean isReverse(StringBounder stringBounder) {
	// final Real point1 = livingSpace1.getPosC(stringBounder);
	// final Real point2 = livingSpace2.getPosC(stringBounder);
	// if (point1.getCurrentValue() > point2.getCurrentValue()) {
	// return true;
	// }
	// return false;
	//
	// }

	private ArrowComponent getComponent(StringBounder stringBounder) {
		ArrowConfiguration arrowConfiguration = message.getArrowConfiguration();
		arrowConfiguration = arrowConfiguration.self();
		final ArrowComponent comp = skin.createComponentArrow(message.getUsedStyles(), arrowConfiguration, skinParam,
				message.getLabelNumbered());
		return comp;
	}

	public void updateStairs(StringBounder stringBounder, double y) {
		final ArrowComponent comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		final Point2D p1 = comp.getStartPoint(stringBounder, dim);
		final Point2D p2 = comp.getEndPoint(stringBounder, dim);

		if (message.isActivate()) {
			livingSpace1.addStepForLivebox(getEvent(), y + p2.getY());
			Log.info("CommunicationTileSelf::updateStairs activate y=" + (y + p2.getY()) + " " + message);
		} else if (message.isDeactivate()) {
			livingSpace1.addStepForLivebox(getEvent(), y + p1.getY());
			Log.info("CommunicationTileSelf::updateStairs deactivate y=" + (y + p1.getY()) + " " + message);
		}

		// livingSpace1.addStep(y + arrowY, level1);
		// livingSpace1.addStep(y + dim.getHeight(), level1);
		// final int level2 = livingSpace2.getLevelAt(this);
		// livingSpace2.addStep(y + arrowY, level2);
		// livingSpace2.addStep(y + dim.getHeight(), level2);
	}

	public void drawU(UGraphic ug) {
		final StringBounder stringBounder = ug.getStringBounder();
		final Component comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		double x1 = getPoint1(stringBounder).getCurrentValue();
		final int levelIgnore = livingSpace1.getLevelAt(this, EventsHistoryMode.IGNORE_FUTURE_ACTIVATE);
		final int levelConsidere = livingSpace1.getLevelAt(this, EventsHistoryMode.CONSIDERE_FUTURE_DEACTIVATE);
		Log.info("CommunicationTileSelf::drawU levelIgnore=" + levelIgnore + " levelConsidere=" + levelConsidere);
		x1 += CommunicationTile.LIVE_DELTA_SIZE * levelIgnore;
		if (levelIgnore < levelConsidere) {
			x1 += CommunicationTile.LIVE_DELTA_SIZE;
		}

		final Area area = new Area(dim.getWidth(), dim.getHeight());
		// if (message.isActivate()) {
		// area.setDeltaX1(CommunicationTile.LIVE_DELTA_SIZE);
		// } else if (message.isDeactivate()) {
		// // area.setDeltaX1(CommunicationTile.LIVE_DELTA_SIZE);
		// // x1 += CommunicationTile.LIVE_DELTA_SIZE * levelConsidere;
		// }
		area.setDeltaX1((levelIgnore - levelConsidere) * CommunicationTile.LIVE_DELTA_SIZE);
		ug = ug.apply(UTranslate.dx(x1));
		comp.drawU(ug, area, (Context2D) ug);
	}

	public double getPreferredHeight(StringBounder stringBounder) {
		final Component comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		return dim.getHeight();
	}

	public void addConstraints(StringBounder stringBounder) {
		// final Component comp = getComponent(stringBounder);
		// final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		// final double width = dim.getWidth();

		final LivingSpace next = getNext();
		if (next != null) {
			next.getPosC(stringBounder).ensureBiggerThan(getMaxX(stringBounder));
		}
	}

	// private boolean isSelf() {
	// return livingSpace1 == livingSpace2;
	// }

	private LivingSpace getNext() {
		for (Iterator<LivingSpace> it = livingSpaces.values().iterator(); it.hasNext();) {
			final LivingSpace current = it.next();
			if (current == livingSpace1 && it.hasNext()) {
				return it.next();
			}
		}
		return null;
	}

	private Real getPoint1(final StringBounder stringBounder) {
		return livingSpace1.getPosC(stringBounder);
	}

	public Real getMinX(StringBounder stringBounder) {
		return getPoint1(stringBounder);
	}

	public Real getMaxX(StringBounder stringBounder) {
		final Component comp = getComponent(stringBounder);
		final Dimension2D dim = comp.getPreferredDimension(stringBounder);
		final double width = dim.getWidth();
		return livingSpace1.getPosC2(stringBounder).addFixed(width);
	}

}
