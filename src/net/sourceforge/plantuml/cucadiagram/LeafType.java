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
 * Contribution :  Hisashi Miyashita
 * Contribution :  Serge Wenger
 */
package net.sourceforge.plantuml.cucadiagram;

import net.sourceforge.plantuml.StringUtils;

public enum LeafType {

	EMPTY_PACKAGE,

	ABSTRACT_CLASS, CLASS, INTERFACE, ANNOTATION, LOLLIPOP_FULL, LOLLIPOP_HALF, NOTE, TIPS, OBJECT, MAP, ASSOCIATION, ENUM, CIRCLE,
	
	USECASE, 

	DESCRIPTION,

	ARC_CIRCLE,

	ACTIVITY, BRANCH, SYNCHRO_BAR, CIRCLE_START, CIRCLE_END, POINT_FOR_ASSOCIATION, ACTIVITY_CONCURRENT,

	STATE, STATE_CONCURRENT, PSEUDO_STATE, DEEP_HISTORY, STATE_CHOICE, STATE_FORK_JOIN,

	BLOCK, ENTITY,
	
	DOMAIN, REQUIREMENT,
	
	PORT, PORTIN, PORTOUT,

	STILL_UNKNOWN;

	public static LeafType getLeafType(String type) {
		type = StringUtils.goUpperCase(type);
		if (type.startsWith("ABSTRACT")) {
			return LeafType.ABSTRACT_CLASS;
		}
		if (type.startsWith("DIAMOND")) {
			return LeafType.STATE_CHOICE;
		}
		return LeafType.valueOf(type);
	}

	public boolean isLikeClass() {
		return this == LeafType.ANNOTATION || this == LeafType.ABSTRACT_CLASS || this == LeafType.CLASS
				|| this == LeafType.INTERFACE || this == LeafType.ENUM || this == LeafType.ENTITY;
	}

	public String toHtml() {
		final String html = StringUtils.goLowerCase(toString().replace('_', ' '));
		return StringUtils.capitalize(html);
	}

	public boolean manageModifier() {
		if (this == ANNOTATION || this == ABSTRACT_CLASS || this == CLASS || this == INTERFACE || this == ENUM
				|| this == OBJECT || this == ENTITY) {
			return true;
		}
		return false;
	}
}
