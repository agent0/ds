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
public class TextTableFormatter {
    private PrintStream out;
    private int[] colWidths = new int[]{30, 5, 8, 8, 8};

    public TextTableFormatter() {
        super();
    }

    public TextTableFormatter(int[] colWidths) {
        super();
        this.colWidths = colWidths;
    }

    public TextTableFormatter(PrintStream out) {
        this.out = out;
    }

    public TextTableFormatter(PrintStream out, int[] colWidths) {
        this.out = out;
        this.colWidths = colWidths;
    }

    public void print(List<Checkpoint> data) {
        out.print(col("Name", this.colWidths[0]));
        out.print("| ");
        out.print(col("HC", this.colWidths[1]));
        out.print("| ");
        out.print(col("ET", this.colWidths[2]));
        out.print("| ");
        out.print(col("EC", this.colWidths[3]));
        out.print("| ");
        out.print(col("ES", this.colWidths[4]));
        sep();
        for (Iterator<Checkpoint> i = data.iterator(); i.hasNext(); ) {
            Checkpoint c = i.next();
            print(c, 0);
        }
    }

    private void sep() {
        out.println();
        out.print(rep("-", this.colWidths[0]));
        out.print("+");
        out.print(rep("-", this.colWidths[1] + 1));
        out.print("+");
        out.print(rep("-", this.colWidths[2] + 1));
        out.print("+");
        out.print(rep("-", this.colWidths[3] + 1));
        out.print("+");
        out.print(rep("-", this.colWidths[4] + 1));
        out.println();
    }

    private String rep(String s, int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += s;
        }
        return result;
    }

    private String col(String s, int len) {
        if (s.length() >= len) {
            s = s.substring(0, len);
        } else {
            for (int i = s.length(); i < len; i++) {
                s += " ";
            }
        }
        return s;
    }

    public void print(Checkpoint c, int indent) {

        String name = "";
        for (int i = 0; i < indent; i++) {
            name += "  ";
        }
        name += c.getTags().get("name").toString();

        out.print(col(name, this.colWidths[0]));
        out.print("| ");
        out.print(col(String.valueOf(c.getHitcount()), this.colWidths[1]));
        out.print("| ");
        out.print(col(String.valueOf(c.getElapsedTotal()), this.colWidths[2]));
        out.print("| ");
        out.print(col(String.valueOf(c.getElapsedChildren()), this.colWidths[3]));
        out.print("| ");
        out.print(col(String.valueOf(c.getElapsedSelf()), this.colWidths[4]));
        out.print("\n");
        for (Iterator<Checkpoint> i = c.getChildren().iterator(); i.hasNext(); ) {
            Checkpoint child = i.next();
            print(child, indent + 1);
        }
    }
}
