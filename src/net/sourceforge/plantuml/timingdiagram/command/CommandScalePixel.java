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
package net.sourceforge.plantuml.timingdiagram.command;

import net.sourceforge.plantuml.LineLocation;
import net.sourceforge.plantuml.command.CommandExecutionResult;
import net.sourceforge.plantuml.command.SingleLineCommand2;
import net.sourceforge.plantuml.command.regex.IRegex;
import net.sourceforge.plantuml.command.regex.RegexConcat;
import net.sourceforge.plantuml.command.regex.RegexLeaf;
import net.sourceforge.plantuml.command.regex.RegexResult;
import net.sourceforge.plantuml.timingdiagram.TimingDiagram;

public class CommandScalePixel extends SingleLineCommand2<TimingDiagram> {

	public CommandScalePixel() {
		super(getRegexConcat());
	}

	private static IRegex getRegexConcat() {
		return RegexConcat.build(CommandScalePixel.class.getName(), RegexLeaf.start(), //
				new RegexLeaf("scale"), //
				RegexLeaf.spaceOneOrMore(), //
				new RegexLeaf("TICK", "(\\d+)"), //
				RegexLeaf.spaceOneOrMore(), //
				new RegexLeaf("as"), //
				RegexLeaf.spaceOneOrMore(), //
				new RegexLeaf("PIXEL", "(\\d+)"), //
				RegexLeaf.spaceOneOrMore(), //
				new RegexLeaf("pixels?"), //
				RegexLeaf.spaceZeroOrMore(), RegexLeaf.end());
	}

	@Override
	final protected CommandExecutionResult executeArg(TimingDiagram diagram, LineLocation location, RegexResult arg) {
		final long tick = Long.parseLong(arg.get("TICK", 0));
		final long pixel = Long.parseLong(arg.get("PIXEL", 0));
		diagram.scaleInPixels(tick, pixel);
		return CommandExecutionResult.ok();
	}

}
