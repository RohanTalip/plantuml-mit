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

import net.sourceforge.plantuml.security.SFile;

public class BaseFile {

	private final String basename;
	private final SFile basedir;

	public BaseFile() {
		this.basedir = null;
		this.basename = null;
	}

	public BaseFile(SFile file) {
		this.basedir = file.getParentFile();
		this.basename = extractBasename(file.getName());
	}

	private static String extractBasename(String name) {
		final int idx = name.lastIndexOf('.');
		if (idx == -1) {
			return name;
		}
		return name.substring(0, idx);
	}

	@Override
	public String toString() {
		if (basedir == null || basename == null) {
			return "(DEFAULT)";
		}
		return basedir + " " + basename;
	}

	public String getBasename() {
		return basename;
	}

	public SFile getBasedir() {
		return basedir;
	}

	public SFile getTraceFile(String tail) {
		if (basedir == null || basename == null) {
			return new SFile(tail);
		}
		return basedir.file(basename + "_" + tail);
	}

}
