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
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

public class Timer {
    private static final Logger log = Logger.getLogger(Timer.class.getName());

    private static ThreadLocal<Stack<Checkpoint>> checkpoints = new ThreadLocal<>();

    private static ThreadLocal<List<Checkpoint>> data = new ThreadLocal<>();

    private static boolean enabled = true;

    public static Checkpoint start(String name) {
        if (!enabled) {
            return new Checkpoint();
        }
        Checkpoint c = Timer.startInternal(name);
        return c;
    }

    public static void stop(Checkpoint c) {
        if (!enabled) {
            return;
        }
        stopInternal(c);
    }

    public static synchronized List<Checkpoint> getData() {
        return new ArrayList<>(data.get());
    }

    public static void clear() {
        data = new ThreadLocal<>();
        checkpoints = new ThreadLocal<>();
    }

    private static Checkpoint startInternal(String name) {

        Stack<Checkpoint> s = checkpoints.get();

        if (s == null) {
            s = new Stack<>();
            checkpoints.set(s);
        }

        Checkpoint c = new Checkpoint();
        c.addTag("name", name);

        if (!s.isEmpty()) {
            Checkpoint tos = s.peek();

            Checkpoint tmp = tos.getChild("name", name);
            if (tmp != null) {
                c = tmp;
            } else {
                tos.addChild(c);
            }
        }

        c.start();
        s.push(c);

        return c;
    }

    private static void stopInternal(Checkpoint c) {

        Stack<Checkpoint> s = checkpoints.get();
        if (s == null) {
            log.warning("Stack is null, maybe stop was called before start or timer data was cleared by another thread!");
            return;
        }

        if (!s.isEmpty()) {
            Checkpoint tos = s.pop();

            if (tos.getOrd() != c.getOrd()) {
                String name = tos.getTagValue("name").toString();
                if (name == null) {
                    name = "??";
                }
                log.warning("Checkpoint '" + tos.getOrd() + "' (" + name + ") not stopped correctly, stopping it");
                while (!s.isEmpty() && tos.getOrd() != c.getOrd()) {
                    tos.stop();
                    tos = s.pop();
                }
            }

            tos.stop();

            if (s.isEmpty()) {
                addData(tos);
            }
        } else {
            log.warning("empty stack while trying to stop Checkpoint " + c.getOrd() + ", this cannot happen!!!)");
        }
    }

    private static synchronized void addData(Checkpoint tos) {
        if (data.get() == null) {
            data.set(new ArrayList<>());
        }
        data.get().add(tos);
    }

    public static void off() {
        enabled = false;
    }

    public static void on() {
        enabled = true;
    }
}
