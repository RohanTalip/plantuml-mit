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
package net.sourceforge.plantuml;

import net.sourceforge.plantuml.cucadiagram.Stereotype;
import net.sourceforge.plantuml.graphic.color.ColorType;
import net.sourceforge.plantuml.graphic.color.Colors;
import net.sourceforge.plantuml.ugraphic.color.HColor;

public class SkinParamColors extends SkinParamDelegator {

	public final Colors getColors() {
		return colors;
	}

	final private Colors colors;

	public SkinParamColors(ISkinParam skinParam, Colors colors) {
		super(skinParam);
		this.colors = colors;
	}

	@Override
	public String toString() {
		return super.toString() + colors;
	}

	@Override
	public boolean shadowing(Stereotype stereotype) {
		if (colors.getShadowing() == null) {
			return super.shadowing(stereotype);
		}
		return colors.getShadowing();
	}

	@Override
	public HColor getFontHtmlColor(Stereotype stereotype, FontParam... param) {
		final HColor value = colors.getColor(ColorType.TEXT);
		if (value == null) {
			return super.getFontHtmlColor(stereotype, param);
		}
		return value;
	}

	@Override
	public HColor getHtmlColor(ColorParam param, Stereotype stereotype, boolean clickable) {
		final ColorType type = param.getColorType();
		if (type == null) {
			return super.getHtmlColor(param, stereotype, clickable);
		}
		final HColor value = colors.getColor(type);
		if (value != null) {
			return value;
		}
		assert value == null;
		return super.getHtmlColor(param, stereotype, clickable);
	}

}
