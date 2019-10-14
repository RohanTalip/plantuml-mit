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
package net.sourceforge.plantuml.vizjs;

import java.io.File;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import net.sourceforge.plantuml.cucadiagram.dot.ExeState;
import net.sourceforge.plantuml.cucadiagram.dot.Graphviz;
import net.sourceforge.plantuml.cucadiagram.dot.GraphvizVersion;
import net.sourceforge.plantuml.cucadiagram.dot.ProcessState;

public class GraphvizJs implements Graphviz {

	private final static ExecutorService executorService = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactory() {
				public Thread newThread(Runnable runnable) {
					return new JsThread(runnable);
				}
			});

	static class JsThread extends Thread {

		private final Runnable runnable;
		private VizJsEngine engine;

		public JsThread(Runnable runnable) {
			this.runnable = runnable;
		}

		@Override
		public void run() {
			if (engine == null) {
				try {
					this.engine = new VizJsEngine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			runnable.run();
		}

	}

	private final String dotString;

	public GraphvizJs(String dotString) {
		this.dotString = dotString;
	}

	public ProcessState createFile3(OutputStream os) {
		try {
			final String svg = submitJob().get();
			os.write(svg.getBytes());
			return ProcessState.TERMINATED_OK();
		} catch (Exception e) {
			e.printStackTrace();
			throw new GraphvizJsRuntimeException(e);
		}
	}

	private Future<String> submitJob() {
		return executorService.submit(new Callable<String>() {
			public String call() throws Exception {
				final JsThread th = (JsThread) Thread.currentThread();
				final VizJsEngine engine = th.engine;
				return engine.execute(dotString);
			}
		});
	}

	public File getDotExe() {
		return null;
	}

	public String dotVersion() {
		return "VizJs";
	}

	public ExeState getExeState() {
		return ExeState.OK;
	}

	public static GraphvizVersion getGraphvizVersion(final boolean modeSafe) {
		return new GraphvizVersion() {
			public boolean useShield() {
				return true;
			}

			public boolean useProtectionWhenThereALinkFromOrToGroup() {
				return true;
			}

			public boolean useXLabelInsteadOfLabel() {
				return modeSafe;
			}

			public boolean isVizjs() {
				return true;
			}

			public boolean ignoreHorizontalLinks() {
				return false;
			}
		};
	}

}
