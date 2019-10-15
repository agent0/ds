# My data structures

This project is a collection of some basic data strutures that I use frequently in my projects.
The data structures are taylored towards my personal needs and thus may not be universally usable.

## Tree

The tree is the most advanced data structure of the collection. It is a collection container that arranges the elements 
in a tree-like structure. Each element can only be added to the tree once (as in a set). The elements that are managed 
by the container do not have to implement a particular interface or extend a given base class. Instead, the container 
internally wraps each element within an internal object that manages the relationships with other elements.
 
A tree can have multiple root nodes, thus making it effectively a _forrest_. Since this denomination is far
less known than the term _tree_, I will stick with the latter term.

## Table

The table data structure provides means to deal with two-dimensional mappings: each table entry consists of a row key,
a column key and a value. The row and column keys are unordered, any structure on these keys has to be provided
by the user.

## Graph

The graph data structure reprents simple graphs that consist of nodes and edges between these nodes. Furthermore, graphs
can be nested using subgraphs. The library provides some means to render graphs into different format. Currently supported
formats are dot, GraphML and GEXF.

## Timer

The timer is essentially not data structure but a small library to do micro-benchmarking in an application. Timer 
checkpoints can be nested to collect hierarchical call time data.

The following code shows a basic use case with one out timer that aggregates two inner timers.

```
Checkpoint outer = Timer.start("outer");

  /* code to measure */

Checkpoint inner_1 = Timer.start("inner_1");

Timer.stop(inner_1);

  /* code to measure */

Checkpoint inner_2 = Timer.start("inner_2");

Timer.stop(inner_2);

  /* code to measure */

Timer.stop(outer);

TextTableFormatter f = new TextTableFormatter();
f.print(Timer.getData());
```

The following table shows the output of the previous code: the colums are the name of the timer,
the elapsed total time within the timer (ET), the time spent in the children of the timee (EC) and the time spent in
the timer itself (ES). So we also have ET = EC + ES.

```
Name                          | HC   | ET      | EC      | ES      
------------------------------+------+---------+---------+---------
outer                         | 1    | 0       | 0       | 0       
  inner_1                     | 1    | 0       | 0       | 0       
  inner_2                     | 1    | 0       | 0       | 0       
```

