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
package net.sourceforge.plantuml.svek.extremity;

import java.awt.geom.Point2D;

import net.sourceforge.plantuml.graphic.UDrawable;
import net.sourceforge.plantuml.ugraphic.UEllipse;
import net.sourceforge.plantuml.ugraphic.UGraphic;
import net.sourceforge.plantuml.ugraphic.UStroke;
import net.sourceforge.plantuml.ugraphic.UTranslate;
import net.sourceforge.plantuml.ugraphic.color.HColorUtils;

class ExtremityCircle extends Extremity {

	private static final double radius = 6;
	private final Point2D dest;
	private final boolean fill;

	@Override
	public Point2D somePoint() {
		return dest;
	}

	public static UDrawable create(Point2D center, boolean fill, double angle) {
		return new ExtremityCircle(center.getX(), center.getY(), fill, angle);
	}

	private ExtremityCircle(double x, double y, boolean fill, double angle) {
		this.dest = new Point2D.Double(x - radius * Math.cos(angle + Math.PI / 2), y - radius
				* Math.sin(angle + Math.PI / 2));
		this.fill = fill;
		// contact = new Point2D.Double(p1.getX() - xContact * Math.cos(angle + Math.PI / 2), p1.getY() - xContact
		// * Math.sin(angle + Math.PI / 2));
	}

	public void drawU(UGraphic ug) {

		ug = ug.apply(new UStroke(1.5));
		if (fill) {
			ug = ug.apply(HColorUtils.changeBack(ug));
		} else {
			ug = ug.apply(HColorUtils.WHITE.bg());
		}

		ug = ug.apply(new UTranslate(dest.getX() - radius, dest.getY() - radius));
		ug.draw(new UEllipse(radius * 2, radius * 2));
	}

}
