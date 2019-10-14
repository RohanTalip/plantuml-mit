/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
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
package net.sourceforge.plantuml.activitydiagram3;

import net.sourceforge.plantuml.Url;
import net.sourceforge.plantuml.activitydiagram3.ftile.BoxStyle;
import net.sourceforge.plantuml.activitydiagram3.ftile.Ftile;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileFactory;
import net.sourceforge.plantuml.activitydiagram3.ftile.FtileKilled;
import net.sourceforge.plantuml.activitydiagram3.ftile.Swimlane;
import net.sourceforge.plantuml.cucadiagram.Display;
import net.sourceforge.plantuml.graphic.color.Colors;

public class InstructionSimple extends MonoSwimable implements Instruction {

	private boolean killed = false;
	private final Display label;
	private final Colors colors;
	private final LinkRendering inlinkRendering;
	private final BoxStyle style;
	private final Url url;
	
	public boolean containsBreak() {
		return false;
	}


	public InstructionSimple(Display label, LinkRendering inlinkRendering, Swimlane swimlane, BoxStyle style, Url url,
			Colors colors) {
		super(swimlane);
		if (colors == null) {
			throw new IllegalArgumentException();
		}
		this.url = url;
		this.style = style;
		this.label = label;
		this.inlinkRendering = inlinkRendering;
		if (inlinkRendering == null) {
			throw new IllegalArgumentException();
		}
		this.colors = colors;
	}

	public Ftile createFtile(FtileFactory factory) {
		Ftile result = factory.activity(label, getSwimlaneIn(), style, colors);
		if (url != null) {
			result = factory.addUrl(result, url);
		}
		result = eventuallyAddNote(factory, result, result.getSwimlaneIn());
		if (killed) {
			return new FtileKilled(result);
		}
		return result;
	}

	public void add(Instruction other) {
		throw new UnsupportedOperationException();
	}

	final public boolean kill() {
		this.killed = true;
		return true;
	}

	public LinkRendering getInLinkRendering() {
		return inlinkRendering;
	}

}
