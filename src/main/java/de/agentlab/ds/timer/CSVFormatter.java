/*
Copyright(C) 2011 by agentlab.de

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package de.agentlab.ds.timer;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("squid:S2384") // mutable members
public class CSVFormatter {
    private PrintStream out;
    private int[] colWidths = new int[]{30, 5, 8, 8, 8};

    public CSVFormatter() {
        super();
        this.out = System.out;
    }

    public CSVFormatter(int[] colWidths) {
        super();
        this.colWidths = colWidths;
    }

    public CSVFormatter(PrintStream out) {
        this.out = out;
    }

    public CSVFormatter(PrintStream out, int[] colWidths) {
        this.out = out;
        this.colWidths = colWidths;
    }

    public void print(List<Checkpoint> data) {
        out.print("Depth;");
        out.print("Name;");
        out.print("HC;");
        out.print("ET;");
        out.print("EC;");
        out.print("ES;");
        out.println();

        for (Iterator<Checkpoint> i = data.iterator(); i.hasNext(); ) {
            Checkpoint c = i.next();
            print(c, 0);
        }
    }

    public void print(Checkpoint c, int indent) {

        out.print(indent + ";");
        out.print(c.getTags().get("name").toString() + ";");
        out.print(c.getHitcount() + ";");
        out.print(c.getElapsedTotal() + ";");
        out.print(c.getElapsedChildren() + ";");
        out.print(c.getElapsedSelf() + ";");
        out.println();
        for (Iterator<Checkpoint> i = c.getChildren().iterator(); i.hasNext(); ) {
            Checkpoint child = i.next();
            print(child, indent + 1);
        }
    }
}
