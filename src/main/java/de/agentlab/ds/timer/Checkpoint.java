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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Checkpoint {
    private static long count;

    private long ord;
    private long start;
    private long end;

    private long elapsed = 0;
    private long hitcount = 0;

    private List<Checkpoint> children = new ArrayList<>();
    private Map<String, Object> tags = new HashMap<>();

    public Checkpoint() {
        this.ord = count++;
    }

    public long getOrd() {
        return this.ord;
    }

    public long getHitcount() {
        return this.hitcount;
    }

    public long getElapsedTotal() {
        return this.elapsed;
    }

    public long getElapsedChildren() {
        long result = 0;
        for (Iterator<Checkpoint> i = this.children.iterator(); i.hasNext(); ) {
            Checkpoint child = i.next();
            result += child.getElapsedTotal();
        }

        return result;
    }

    public long getElapsedSelf() {
        return this.getElapsedTotal() - this.getElapsedChildren();
    }

    public void start() {
        this.start = System.currentTimeMillis();
        this.hitcount++;
    }

    public void stop() {
        this.end = System.currentTimeMillis();

        this.elapsed += (this.end - this.start);
    }

    public void addChild(Checkpoint child) {
        this.children.add(child);
    }

    public List<Checkpoint> getChildren() {
        return this.children;
    }

    public Checkpoint getChild(String key, Object value) {
        for (Checkpoint child : this.children) {
            Object tmp = child.getTagValue(key);
            if (tmp != null && tmp.equals(value)) {
                return child;
            }
        }
        return null;
    }

    public void addTag(String key, String value) {
        this.tags.put(key, value);
    }

    public List<String> getTagNames() {
        List<String> result = new ArrayList<>();
        result.addAll(this.tags.keySet());
        return result;
    }

    public Object getTagValue(String key) {
        return this.tags.get(key);
    }

    public Map<String, Object> getTags() {
        return this.tags;
    }

    @Override
    public String toString() {
        return "Checkpoint [end=" + end + ", ord=" + ord + ", start=" + start + ", " + (tags != null ? "tags=" + tags : "") + "]";
    }

}
