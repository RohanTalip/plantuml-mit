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
package net.sourceforge.plantuml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AFileZipEntry implements AFile {

	private final File zipFile;
	private final String entry;

	public AFileZipEntry(File file, String entry) {
		this.zipFile = file;
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "AFileZipEntry::" + zipFile + " " + entry;
	}

	public InputStream open() throws IOException {
		final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {
			final String fileName = ze.getName();
			if (ze.isDirectory()) {
			} else if (fileName.trim().equalsIgnoreCase(entry.trim())) {
				return zis;
			}
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		throw new IOException();
	}

	public boolean isOk() {
		if (zipFile.exists() && zipFile.isDirectory() == false) {
			InputStream is = null;
			try {
				is = open();
				return true;
			} catch (IOException e) {
				// e.printStackTrace();
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return zipFile.hashCode() + entry.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AFileZipEntry == false) {
			return false;
		}
		final AFileZipEntry other = (AFileZipEntry) obj;
		return this.zipFile.equals(other.zipFile) && this.entry.equals(other.entry);
	}

	public AParentFolder getParentFile() {
		return new AParentFolderZip(zipFile, entry);
	}

	public String getAbsolutePath() {
		return zipFile.getAbsolutePath() + "~" + entry;
	}

	public File getUnderlyingFile() {
		return zipFile;
	}

}
