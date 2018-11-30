package de.agentlab.ds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection container that arranges the elements in a tree-like structure. Each element can only be added to the
 * tree once (as in a set). The elements that are managed by the container do not have to implement a particular
 * interface or extend a given base class. Instead, the container internally wraps each element within a {@link Node}
 * object that manages the relationships with other elements.
 * <p>
 * A tree can have multiple root nodes, thus making it effectively a <em>forrest</em>. Since this denomination is far
 * less known than the term <em>tree</em>, we will stick with the latter term.
 *
 * @author Jürgen Lind
 * @version $Id:$
 */
public class Tree<T> implements Serializable {

    public static final long serialVersionUID = 42L;

    private long version = 0;

    private Node<T> root = new Node<>();
    private Map<T, Node<T>> nodes = new HashMap<>();

    /**
     * Adds the given element to the tree. The element is added as the last root node.
     *
     * @param data the element to add
     * @return the added element
     * @throws IllegalArgumentException if the element has already been added to the tree
     */
    public T add(T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> newNode = new Node<>(this.root, data);
        this.root.add(newNode);
        this._put(data, newNode);

        return data;
    }

    /**
     * Adds the given element to the tree. The element is added as the first root node.
     *
     * @param data the element to add
     * @return the added element
     * @throws IllegalArgumentException if the element has already been added to the tree
     */
    public T push(T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> newNode = new Node<>(this.root, data);
        this.root.push(newNode);
        this._put(data, newNode);

        return data;
    }

    /**
     * Adds the a new element as child of the parent element. The new element is appended to the current list of
     * children of the parent node.
     *
     * @param parent the parent element
     * @param data   the element to add
     * @return the newly added element
     * @throws IllegalArgumentException if the element has already been added to the tree
     * @throws IllegalArgumentException if the parent element is not found in the tree
     */
    public T addChild(T parent, T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> parentNode = this.nodes.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element '" + parent + "' not found.");
        }

        Node<T> newNode = new Node<>(parentNode, data);
        parentNode.add(newNode);
        this._put(data, newNode);

        return data;
    }

    /**
     * Adds a new element as parent of the given element. The new parent element is moved between the data
     * element and its current parent element. If the data element is a root node, the new parent is added
     * as root node.
     *
     * @param data      the data element
     * @param newParent the new parent
     * @return the newly added element
     */
    public T addParent(T data, T newParent) {
        if (this.nodes.containsKey(newParent)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }
        T currentParent = this.getParent(data);
        if (currentParent == null) {
            this.add(newParent);
        } else {
            this.addChild(currentParent, newParent);
        }
        this.move(data, newParent);

        return data;
    }

    /**
     * Adds the given tree as subtree starting at the root.
     *
     * @param subtree the subtree to add
     */
    public void addSubtree(Tree<T> subtree) {
        this.addSubtree(null, subtree);
    }

    /**
     * Adds the given tree as subtree starting at the given parent node.
     *
     * @param parent  the parent node to add the subtree to
     * @param subtree the subtree to add
     */
    public void addSubtree(T parent, Tree<T> subtree) {
        for (T subtreeElem : subtree.getPreorderList()) {
            T tmp = subtree.getParent(subtreeElem);
            if (tmp == null) {
                if (parent == null) {
                    this.add(subtreeElem);
                } else {
                    this.addChild(parent, subtreeElem);
                }
            } else {
                this.addChild(tmp, subtreeElem);
            }
        }
    }

    /**
     * Adds the given element to the parent at the specified position.
     *
     * @param parent the sibling element
     * @param index  the insert position
     * @param data   the element to add
     * @return the newly added element
     * @throws IllegalArgumentException  if the element has already been added to the tree
     * @throws IllegalArgumentException  if the parent element is not found in the tree
     * @throws IndexOutOfBoundsException if the position is not valid
     */
    public T addChildAt(T parent, int index, T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> parentNode = this.nodes.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element '" + parent + "' not found.");
        }

        Node<T> newNode = new Node<>(parentNode, data);
        parentNode.add(index, newNode);
        this._put(data, newNode);

        return data;
    }

    /**
     * Adds the given element to the parent of the sibling before the sibling.
     *
     * @param sibling the sibling element
     * @param data    the element to add
     * @return the newly added element
     * @throws IllegalArgumentException if the element has already been added to the tree
     * @throws IllegalArgumentException if the sibling element is not found in the tree
     */
    public T addChildBefore(T sibling, T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> siblingNode = this.nodes.get(sibling);
        if (siblingNode == null) {
            throw new IllegalArgumentException("Sibling node '" + sibling + "' not found.");
        }

        List<Node<T>> children = siblingNode.getParent().getChildren();
        int index = children.indexOf(siblingNode);
        Node<T> newChild = new Node<>(siblingNode.getParent(), data);
        children.add(index, newChild);
        this._put(data, newChild);

        return data;
    }

    /**
     * Adds the given element to the parent of the sibling after the sibling.
     *
     * @param sibling the sibling element
     * @param data    the element to add
     * @return the newly added element
     * @throws IllegalArgumentException if the element has already been added to the tree
     * @throws IllegalArgumentException if the sibling element is not found in the tree
     */
    public T addChildAfter(T sibling, T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> siblingNode = this.nodes.get(sibling);
        if (siblingNode == null) {
            throw new IllegalArgumentException("Sibling node '" + sibling + "' not found.");
        }

        List<Node<T>> children = siblingNode.getParent().getChildren();
        int index = children.indexOf(siblingNode);
        Node<T> newChild = new Node<>(siblingNode.getParent(), data);
        children.add(index + 1, newChild);
        this._put(data, newChild);

        return data;
    }

    /**
     * Moves the element to a new parent, removing it from its current parent. If the new parent is <code>null</code>,
     * the element is moved to the root node.
     *
     * @param data      the element to move
     * @param newParent the new parent for the element or <code>null</code> for the root node
     * @throws IllegalArgumentException if the element is not found in the tree
     * @throws IllegalArgumentException if the parent is not found in the tree
     */
    public void move(T data, T newParent) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        Node<T> parentNode = this.nodes.get(newParent);

        if (newParent != null && parentNode == null) {
            throw new IllegalArgumentException("Target element '" + newParent + "' not found.");
        }

        if (data.equals(newParent)) {
            throw new IllegalArgumentException("An element '" + newParent + "' cannot be move onto itself.");
        }

        if (this.getDescendants(data).contains(newParent)) {
            throw new IllegalArgumentException("Target element '" + newParent + "' is a descendant of '" + data + "'.");
        }

        int index = -1;
        if (node.getParent().getParent() != null) {
            index = node.getParent().getParent().getChildren().indexOf(node.getParent());
        } else if (node.getParent() != null) {
            index = node.getParent().getChildren().indexOf(node);
        }

        if (parentNode == null) {
            node.getParent().remove(node);
            if (index == -1 || index > this.root.getChildren().size()) {
                this.root.add(node);
            } else {
                this.root.add(index, node);
            }
        } else {
            node.getParent().remove(node);
            if (index == -1 || index > parentNode.getChildren().size()) {
                parentNode.add(node);
            } else {
                parentNode.add(index, node);
            }
        }
    }

    /**
     * Removes the given element from the tree. The children of the element (if any) are recursively removed from the
     * tree as well.
     *
     * @param data the element to remove
     * @return <code>true</code> if the element was been removed, <code>false</code> if the tree did not contain the
     * element
     */
    public boolean remove(T data) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            return false;
        }

        List<T> descendants = this.getDescendants(data);
        for (T t : descendants) {
            this._remove(t);
        }

        node.getParent().remove(node);
        this._remove(data);

        return true;
    }

    /**
     * Replaces the given element with another element. The structural integrity of the tree is preserved.
     *
     * @param data        the element to replace
     * @param replacement the replacement
     */
    public void replace(T data, T replacement) {
        if (this.nodes.containsKey(replacement)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> dataNode = this.nodes.get(data);
        if (dataNode == null) {
            throw new IllegalArgumentException("Existing node '" + data + "' not found.");
        }

        this._remove(data);
        dataNode.setData(replacement);
        this._put(replacement, dataNode);
    }

    /**
     * Prunes the tree to the given element. After applying this operation, the tree contains only the element itself
     * along with its ancestors and descendants.
     *
     * @param data the element to prune to
     */
    public void prune(T data) {

        final List<T> path = this.getPath(data);
        final List<T> descendants = this.getDescendants(data);

        this.filterEager(data1 -> path.contains(data1) || descendants.contains(data1));
    }

    /**
     * Copies the tree and prunes it to the given element. After applying this operation, the result contains only the
     * element itself along with its ancestors and descendants.
     *
     * @param data the element to prune to
     */
    public Tree<T> pruneCopy(T data) {
        Tree<T> result = this.copy();

        final List<T> path = result.getPath(data);
        final List<T> descendants = result.getDescendants(data);

        result.filterEager(data1 -> path.contains(data1) || descendants.contains(data1));

        return result;
    }

    /**
     * Removes all elements from the tree.
     */
    public void clear() {
        this.root.children.clear();
        this.nodes.clear();
    }

    /**
     * Pulls the given element from the tree, i.e. the node is removed from the tree and all children of the node (if
     * any) are moved to the parent of the element.
     *
     * @param data the element to pull
     * @return <code>true</code> if the element was been pulled, <code>false</code> if the tree does not contain the
     * element
     */
    public boolean pull(T data) {

        Node<T> node = this.nodes.get(data);
        if (node == null) {
            return false;
        }

        for (T t : this.getChildren(data)) {
            this.move(t, node.getParent().getData());
        }

        node.getParent().remove(node);
        this._remove(data);

        return true;
    }

    /**
     * Removes all elements that are accepted by the given filter. The elements that are accepted are removed using the
     * {@link #remove(T)} method.
     *
     * @param f the {@link Filter} to apply
     * @return <code>true</code> if at least one element has been removed, <code>false</code> otherwise
     */
    public boolean remove(Filter<T> f) {
        List<T> toRemove = this.getPreorderList();
        toRemove.removeIf(t -> !f.accept(t));

        boolean result = false;
        for (T t : toRemove) {
            result |= this.remove(t);
        }
        return result;
    }

    /**
     * Returns the parent element for the given element.
     *
     * @param data the element
     * @return the parent element or <code>null</code> if the parent element is a root element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public T getParent(T data) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }
        Node<T> parent = node.getParent();
        if (parent != null) {
            return parent.getData();
        } else {
            return null;
        }
    }

    /**
     * Returns all root elements of the tree.
     *
     * @return the root elements
     */
    public List<T> getRoots() {
        List<T> result = new ArrayList<>();
        for (Node<T> node : this.root.getChildren()) {
            result.add(node.getData());
        }
        return result;
    }

    /**
     * Returns a set of all elements of the tree.
     *
     * @return the set of tree elements
     */
    public Set<T> getAll() {
        return this.nodes.keySet();
    }

    /**
     * Returns all leaf elements of the tree. A <em>leaf</em> is an element that has no children.
     *
     * @return the leaf elements
     */
    public List<T> getLeafs() {
        final List<T> result = new ArrayList<>();

        for (T t : this.getPreorderList()) {
            if (!this.hasChildren(t)) {
                result.add(t);
            }
        }

        return result;
    }

    /**
     * Returns the list of children of the given element.
     *
     * @param parent the parent element
     * @return the list of children
     * @throws IllegalArgumentException if the parent element is not found in the tree
     */
    public List<T> getChildren(T parent) {
        final Node<T> parentNode = this.nodes.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element '" + parent + "' not found.");
        }
        List<T> result = new ArrayList<>();
        for (Node<T> node : parentNode.getChildren()) {
            result.add(node.getData());
        }
        return result;
    }

    /**
     * Returns a list of descendants for a given node
     *
     * @param data the subtree root
     * @return the list of descendants
     */
    public List<T> getDescendants(T data) {
        return this.getPreorderList(data);
    }

    /**
     * Tests if the given element has children.
     *
     * @param parent the element to test
     * @return <code>true</code> if the given node has children, <code>false</code> otherwise
     */
    public boolean hasChildren(T parent) {
        final Node<T> parentNode = this.nodes.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element '" + parent + "' not found.");
        }
        return parentNode.children.size() > 0;
    }

    /**
     * Returns the list of siblings of the given element.
     *
     * @param data the element
     * @return the siblings of the element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public List<T> getSiblings(T data) {
        return this.getSiblings(data, false);
    }

    /**
     * Returns the list of siblings of the given element. Optionally includes the element itself.
     *
     * @param data        the element
     * @param includeSelf include the element itself in the collection of siblings
     * @return the siblings of the element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public List<T> getSiblings(T data, boolean includeSelf) {
        final Node<T> dataNode = this.nodes.get(data);
        if (dataNode == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }
        List<T> result = new ArrayList<>();
        for (Node<T> node : dataNode.getParent().getChildren()) {
            if (includeSelf || !data.equals(node.getData())) {
                result.add(node.getData());
            }
        }
        return result;
    }

    /**
     * Returns the subtree underneath the given element.
     *
     * @param data the root of the subtree
     * @return the subtree
     */
    public Tree<T> getSubtree(T data) {
        return this.map(data, data1 -> data1);
    }

    /**
     * Checks if a given element is the first child of its parent node.
     *
     * @param data the element
     * @return <code>true</code> if the element is the first child of its parent, <code>false</code> otherwise
     */
    public boolean isFirstChild(T data) {
        final Node<T> dataNode = this.nodes.get(data);
        if (dataNode == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        T parent = this.getParent(data);
        List<T> children;
        if (parent == null) {
            children = this.getRoots();
        } else {
            children = this.getChildren(parent);
        }

        return children.indexOf(data) == 0;
    }

    /**
     * Checks if a given element is the last child of its parent node.
     *
     * @param data the element
     * @return <code>true</code> if the element is the last child of its parent, <code>false</code> otherwise
     */
    public boolean isLastChild(T data) {
        final Node<T> dataNode = this.nodes.get(data);
        if (dataNode == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        T parent = this.getParent(data);
        List<T> children;
        if (parent == null) {
            children = this.getRoots();
        } else {
            children = this.getChildren(parent);
        }

        return children.indexOf(data) == children.size() - 1;
    }

    /**
     * Returns the depth of the element. The depth of an element is defined as the distance to a root node. Thus, a root
     * node has depth of <code>0</code>, children of root nodes have a depth of <code>1</code> and so forth.
     *
     * @param data the element
     * @return the depth of the element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public int getDepth(T data) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        return node.getDepth();
    }

    /**
     * Returns the depth of the tree. The tree depth is defined as the maximum of all element depths.
     *
     * @return the depth of the tree
     */
    public int getDepth() {
        int max = 0;
        for (T data : this.asList()) {
            int depth = this.getDepth(data);
            if (depth > max) {
                max = depth;
            }
        }
        return max;
    }

    /**
     * Returns the path (including the element itself) to the given element as list.
     *
     * @param data the element
     * @return a list of elements that represents the path to the element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public List<T> getPath(T data) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        List<T> path = new ArrayList<>();
        while (node.getParent() != null) {
            path.add(node.getData());
            node = node.getParent();
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Returns the path (including the element itself) to the given element as (degenerated) tree. The result tree is
     * degenerated such that each element has exactly one child element.
     *
     * @param data the element
     * @return a tree of elements that represents the path to the element
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public Tree<T> getPathTree(T data) {
        Tree<T> result = new Tree<>();

        List<T> path = this.getPath(data);
        if (path.size() > 0) {
            T tmp = path.get(0);
            result.add(tmp);
            for (int i = 1; i < path.size(); i++) {
                result.addChild(tmp, path.get(i));
                tmp = path.get(i);
            }
        }
        return result;
    }

    /**
     * Visits the elements of the tree a pre-order, starting at the root nodes.
     *
     * @param visitor the {@link Visitor} to apply
     */
    public void visit(Visitor<T> visitor) {
        for (Node<T> node : this.root.getChildren()) {
            if (!this._visit(node, visitor)) {
                break;
            }
        }
    }

    /**
     * Visits the elements of the tree a pre-order, starting at the root nodes.
     *
     * @param visitor the {@link Visitor} to apply
     */
    public void visit(PrePostVisitor<T> visitor) {
        for (Node<T> node : this.root.getChildren()) {
            if (!this._visit(node, visitor)) {
                break;
            }
        }
    }

    /**
     * Visits the elements of a subtree a pre-order.
     *
     * @param data    the root of the subtree to visit
     * @param visitor the {@link Visitor} to apply
     * @return <code>true</code> if all elements have been visited, <code>false</code> otherwise (i.e. the
     * {@link Visitor#visit(Object)} method returned <code>false</code> for some object)
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public boolean visit(T data, Visitor<T> visitor) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        return this._visit(node, visitor);
    }

    /**
     * Visits the descendants of an element a pre-order. The element itself is not processed by the {@link Visitor}.
     *
     * @param data    the root of the subtree to visit
     * @param visitor the {@link Visitor} to apply
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public void visitDescendants(T data, Visitor<T> visitor) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        for (Node<T> child : node.getChildren()) {
            if (!this._visit(child, visitor)) {
                break;
            }
        }
    }

    /**
     * Visits the siblings of an element a pre-order. The element itself is not processed by the {@link Visitor}.
     *
     * @param data    the element which siblings to visit
     * @param visitor the {@link Visitor} to apply
     */
    public void visitSiblings(T data, Visitor<T> visitor) {
        List<T> siblings = this.getSiblings(data);
        for (T t : siblings) {
            if (!this.visit(t, visitor)) {
                break;
            }
        }
    }

    /**
     * Checks if the tree contains a particular element.
     *
     * @param data the element to check on
     * @return <code>true</code> if the tree contains the given element, <code>false</code> otherwise
     */
    public boolean contains(T data) {
        return this.nodes.get(data) != null;
    }

    /**
     * Checks if an element is a child element of another element.
     *
     * @param data  the element to test
     * @param data2 the parent to check against
     * @return <code>true</code> if the element is a child element, <code>false</code> otherwise
     */
    public boolean isChild(T data, T data2) {
        return getChildren(data2).contains(data);
    }

    /**
     * Checks if an element is a descendant of another element.
     *
     * @param data  the element to test
     * @param data2 the predecessor to check against
     * @return <code>true</code> if the element is a descendant, <code>false</code> otherwise
     */
    public boolean isDescendant(T data, T data2) {
        return getPreorderList(data2).contains(data);
    }

    /**
     * Checks if an element is an ancestor of another element.
     *
     * @param data  the ancestor to check against
     * @param data2 the item to check
     * @return <code>true</code> if the element is an ancestor, <code>false</code> otherwise
     */
    public boolean isAncestor(T data, T data2) {
        return getPreorderList(data).contains(data);
    }

    /**
     * Returns the size of the tree (i.e. the number of elements in the tree).
     *
     * @return the size of the tree
     */
    public int size() {
        return this.nodes.size();
    }

    /**
     * Returns the elements of the tree as pre-ordered list.
     *
     * @return the element list in pre-order
     */
    public List<T> asList() {
        return this.getPreorderList();
    }

    /**
     * Returns the elements of the tree as pre-ordered list.
     *
     * @return the element list in pre-order
     */
    public List<T> getPreorderList() {
        List<T> result = new ArrayList<>();
        this._preorder(this.root, result);

        return result.subList(1, result.size());
    }

    /**
     * Returns the elements of the tree accepted by the given filter as pre-ordered list.
     *
     * @param filter the {@link Filter} to use
     * @return the elements of the tree accepted by the given filter as pre-ordered list
     */
    public List<T> getPreorderList(Filter<T> filter) {
        List<T> result = new ArrayList<>();
        this._preorder(this.root, filter, result, true);
        return result;
    }

    /**
     * Returns the elements of the subtree rooted at the given element as pre-ordered list.
     *
     * @param data the subtree root
     * @return the element list in pre-order
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public List<T> getPreorderList(T data) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }
        List<T> result = new ArrayList<>();
        this._preorder(node, result);
        return result.subList(1, result.size());
    }

    /**
     * Returns the elements of the tree accepted by the given filter as set.
     *
     * @param filter the {@link Filter} to use
     * @return the elements of the tree accepted by the given filter as set
     */
    public Set<T> getMatchSet(Filter<T> filter) {
        Set<T> result = new HashSet<>();
        this._preorder(this.root, filter, result, true);
        return result;
    }

    /**
     * Returns the elements of the tree as list using a breath first traversal.
     *
     * @return the element list
     */
    public List<T> getBreadthFirstList() {
        List<T> result = new ArrayList<>();
        this._breadthfirst(this.root, result);
        return result;
    }

    /**
     * Return a list of all branches of the tree. A <em>branch</em> is a list of of elements from a root to a leaf.
     *
     * @return a list of all branches
     */
    public List<List<T>> getBranches() {
        List<List<T>> result = new ArrayList<>();

        List<T> leafs = this.getLeafs();
        for (T t : leafs) {
            result.add(this.getPath(t));
        }
        return result;
    }

    public Iterator<List<T>> getBranchesIterator() {
        class Index {
            private int value = 0;

            public int get() {
                return value;
            }

            public void inc() {
                this.value++;
            }
        }

        List<T> leafs = this.getLeafs();
        final Index index = new Index();
        return new Iterator<List<T>>() {
            @Override
            public boolean hasNext() {
                return index.get() < leafs.size();
            }

            @Override
            public List<T> next() {
                List<T> path = Tree.this.getPath(leafs.get(index.get()));
                index.inc();
                return path;
            }
        };
    }

    /**
     * Returns a list of all nodes on the given level.
     *
     * @param level the target level for the result elements
     * @return a list of all nodes on the given level
     */
    public List<T> getLevel(int level) {
        List<T> result = new ArrayList<>();

        List<T> l = this.getPreorderList();
        for (T t : l) {
            if (this.getDepth(t) == level) {
                result.add(t);
            }
        }

        return result;
    }

    /**
     * Sorts the tree with the given {@link Comparator}. Sorting the tree is defined as sorting the children of each
     * element according to the comparator.
     *
     * @param comparator the {@link Comparator} used to sort the children
     */
    public void sort(Comparator<T> comparator) {
        this._sort(this.root, comparator);
    }

    /**
     * Retains all elements from the tree that are accepted by the {@link Filter}. If an element is retained, all
     * elements on the path to a root element are retained as well. The elements that are rejected are removed using the
     * {@link #remove(T)} method.
     *
     * @param filter the {@link Filter} to use
     * @return the filtered tree
     */
    public Tree<T> filter(Filter<T> filter) {
        return filter(filter, false);
    }

    /**
     * See {@link #filter}
     *
     * @param filter        the {@link Filter} to use
     * @param retainSubTree retain also the Sub-Tree of a matching element.
     * @return the filtered tree
     */
    public Tree<T> filter(Filter<T> filter, boolean retainSubTree) {
        List<T> matches = new ArrayList<>();
        for (T data : this.getPreorderList()) {
            if (filter.accept(data)) {
                matches.add(data);
            }
        }
        Set<T> keep = new HashSet<>();

        for (T data : matches) {
            List<T> path = this.getPath(data);
            for (T t : path) {
                keep.add(t);
            }
        }

        for (T data : matches) {
            boolean retain = false;
            if (filter instanceof RetainingFilter) {
                retain = ((RetainingFilter) filter).retain(data);
            }
            if (retain || retainSubTree) {
                Tree<T> subtree = this.getSubtree(data);
                keep.addAll(subtree.asList());
            }
        }

        for (T data : this.getPreorderList()) {
            if (!keep.contains(data)) {
                this.remove(data);
            }
        }

        return this;
    }

    /**
     * Retains all elements from the tree that are accepted by the {@link Filter}. If an element is rejected, all
     * children are rejected as well. The elements that are rejected are removed using the {@link #remove(T)} method.
     *
     * @param filter the {@link Filter} to use
     * @return the tree
     */
    public Tree<T> filterEager(Filter<T> filter) {

        Set<T> matches = this.getMatchSet(filter);

        List<T> preorderList = this.getPreorderList();

        for (T data : preorderList) {
            if (!matches.contains(data)) {
                this.remove(data);
            }
        }

        return this;
    }

    /**
     * Applies the specified filter repeatedly to the tree until no element is removed for a filter application.
     *
     * @param filter the filter to apply
     * @return the tree
     */
    public Tree<T> stabilize(Filter<T> filter) {
        int size;
        do {
            size = this.size();
            this.filter(filter);
        } while (this.size() != size);

        return this;
    }

    /**
     * Retains all elements from the tree that are accepted by the {@link Filter}. The elements that are rejected are
     * removed using the {@link #pull(T)} method.
     *
     * @param filter the {@link Filter} to use
     */
    public void shrink(Filter<T> filter) {
        List<T> matches = new ArrayList<>();
        for (T data : this.getPreorderList()) {
            if (filter.accept(data)) {
                matches.add(data);
            }
        }

        for (T data : this.getPreorderList()) {
            if (!matches.contains(data)) {
                this.pull(data);
            }
        }
    }

    /**
     * Retains all elements from the given subtree that are accepted by the {@link Filter}. The elements that are
     * rejected are removed using the {@link #pull(T)} method.
     *
     * @param data   the subtree root
     * @param filter the {@link Filter} to use
     */
    public void shrinkSubtree(T data, Filter<T> filter) {
        List<T> matches = new ArrayList<>();
        for (T data2 : this.getPreorderList(data)) {
            if (filter.accept(data2)) {
                matches.add(data2);
            }
        }

        for (T data2 : this.getPreorderList(data)) {
            if (!matches.contains(data2)) {
                this.pull(data2);
            }
        }
    }

    /**
     * Removes all but the first <code>count</code> elements of the tree
     *
     * @param count the number of elements (in pre-order) to keep
     */
    public void limit(int count) {
        List<T> l = this.getPreorderList();
        for (int i = count; i < l.size(); i++) {
            this.remove(l.get(i));
        }
    }

    /**
     * Returns a copy of this tree that results from applying the {@link #filter(Filter)} method after the copy is made.
     *
     * @param filter the {@link Filter} to use
     * @return the tree copy
     */
    public Tree<T> copy(Filter<T> filter) {
        Tree<T> result = this.copy();
        return result.filter(filter);
    }

    /**
     * Maps this tree of generic type <code>T</code> to a homomorphous tree of type <code>S</code> using the given
     * {@link Mapper}.
     *
     * @param mapper the {@link Mapper} to use
     * @return a homomorphous tree of type <code>S</code>
     */
    public <S> Tree<S> map(Mapper<T, S> mapper) {
        Tree<S> result = new Tree<>();

        this._map(this.root, mapper, result.root, result);

        return result;
    }

    /**
     * Maps the subtree from a given element of generic type <code>T</code> to a homomorphous tree of type
     * <code>S</code> using the given {@link Mapper}.
     *
     * @param data   the subtree root
     * @param mapper the {@link Mapper} to use
     * @return a homomorphous tree of type <code>S</code>
     * @throws IllegalArgumentException if the element is not found in the tree
     */
    public <S> Tree<S> map(T data, Mapper<T, S> mapper) {
        Node<T> node = this.nodes.get(data);
        if (node == null) {
            throw new IllegalArgumentException("Element '" + data + "' not found.");
        }

        Tree<S> result = new Tree<>();
        Node<S> resultRoot = result._add(mapper.map(data));

        this._map(node, mapper, resultRoot, result);

        return result;
    }

    /**
     * Returns the first element that is accepted by the given {@link Filter}.
     *
     * @param filter the {@link Filter} to apply
     * @return the first element that is accepted by the {@link Filter}, <code>null</code> if no element is accepted
     */
    public T find(Filter<T> filter) {
        List<T> l = this.getPreorderList();
        for (T t : l) {
            if (filter.accept(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Returns a list of elements that are accepted by the given {@link Filter}.
     *
     * @param filter the {@link Filter} to apply
     * @return the list of elements that is accepted by the {@link Filter} or an empty list if none is accepted
     */
    public List<T> findAll(Filter<T> filter) {
        List<T> result = new ArrayList<>();
        List<T> l = this.getPreorderList();
        for (T t : l) {
            if (filter.accept(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Returns either the  element itself or the closest parent element that satisfies the filter condition.
     *
     * @param data   the elem to start from
     * @param filter the {@link Filter} to apply
     * @return the element closest to the element that satisfies the filter condition. This can be the element itself.
     */
    public T closest(T data, Filter<T> filter) {
        List<T> path = this.getPath(data);
        Collections.reverse(path);

        for (T elem : path) {
            if (filter.accept(elem)) {
                return elem;
            }
        }

        return null;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this tree, or -1 if this list does not
     * contain the element.
     *
     * @param data the to look for
     * @return the index of the first occurrence of the specified element in this tree, or -1 if this list does not
     * contain the element
     */
    public int indexOf(T data) {
        return this.getPreorderList().indexOf(data);
    }

    /**
     * Returns a copy of this tree.
     *
     * @return a copy of the tree
     */
    public Tree<T> copy() {
        return this.map(new IdentityMapper<>());
    }

    public Tree<ItemWrapper<T>> reverse() {
        Tree<ItemWrapper<T>> result = new Tree<>();

        List<T> leafs = this.getLeafs();
        for (T leaf : leafs) {
            ItemWrapper<T> data = new ItemWrapper<>(leaf);
            result.add(data);

            ItemWrapper<T> current = data;
            List<T> path = this.getPath(leaf);
            for (int i = path.size() - 2; i >= 0; i--) {
                ItemWrapper<T> tmp = new ItemWrapper<>(path.get(i));
                result.addChild(current, tmp);
                current = tmp;

            }
        }
        return result;
    }

    /**
     * @param data the element to check
     * @return <code>true</code> if the item is a root node, <code>false</code> otherwise
     */
    public boolean isRoot(T data) {
        return this.getRoots().contains(data);
    }

    /**
     * @param data the element to check
     * @return <code>true</code> if the item is a leaf node, <code>false</code> otherwise
     */
    public boolean isLeaf(T data) {
        final Node<T> node = this.nodes.get(data);
        if (node == null) {
            return false;
        }

        return node.getChildren().size() == 0;
    }

    @Override
    public String toString() {
        String result = "";
        result += this.root.toString(-1, data -> data.toString());
        return result;
    }

    public String toStringInternal() {
        return this.nodes.toString();
    }

    public String toString(ElementFormatter<T> formatter) {
        String result = "";
        result += this.root.toString(-1, formatter);
        return result;
    }

    private <S> void _map(Node<T> node, Mapper<T, S> mapper, Node<S> resultNode, Tree<S> resultTree) {

        for (Node<T> child : node.getChildren()) {
            Node<S> resultChild;
            if (node == this.root) {
                resultChild = resultTree._add(mapper.map(child.getData()));
            } else {
                resultChild = resultTree._addChild(resultNode.getData(), mapper.map(child.getData()));
            }
            this._map(child, mapper, resultChild, resultTree);
        }
    }

    private boolean _visit(Node<T> node, Visitor<T> visitor) {
        if (!visitor.visit(node.getData())) {
            return false;
        }
        for (Node<T> child : node.getChildren()) {
            if (!this._visit(child, visitor)) {
                return false;
            }
        }
        return true;
    }

    private boolean _visit(Node<T> node, PrePostVisitor<T> visitor) {
        if (!visitor.visitPre(node.getData())) {
            return false;
        }

        for (Node<T> child : node.getChildren()) {
            if (!this._visit(child, visitor)) {
                return false;
            }
        }

        return visitor.visitPost(node.getData());
    }

    private void _preorder(Node<T> node, List<T> result) {
        result.add(node.getData());
        for (Node<T> child : node.getChildren()) {
            this._preorder(child, result);
        }
    }

    private void _preorder(Node<T> node, Filter<T> filter, Collection<T> result, boolean isRoot) {
        if (isRoot || filter.accept(node.getData())) {
            if (!isRoot) {
                result.add(node.getData());
            }
            for (Node<T> child : node.getChildren()) {
                this._preorder(child, filter, result, false);
            }
        }
    }

    private void _breadthfirst(Node<T> root, List<T> result) {
        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (queue.size() > 0) {
            Node<T> head = queue.remove(0);
            queue.addAll(head.getChildren());
            result.add(head.getData());
        }

        result.remove(0);
    }

    private void _sort(Node<T> parent, final Comparator<? super T> comparator) {
        List<Node<T>> children = parent.getChildren();

        Collections.sort(children, (n1, n2) -> comparator.compare(n1.getData(), n2.getData()));
        for (Node<T> child : children) {
            this._sort(child, comparator);
        }
    }

    private Node<T> _add(T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> newNode = new Node<>(this.root, data);
        this.root.add(newNode);
        this._put(data, newNode);

        return newNode;
    }

    private Node<T> _addChild(T parent, T data) {
        if (this.nodes.containsKey(data)) {
            throw new IllegalArgumentException("Element '" + data + "' already in tree.");
        }

        Node<T> parentNode = this.nodes.get(parent);
        if (parentNode == null) {
            throw new IllegalArgumentException("Parent element '" + parent + "' not found.");
        }

        Node<T> newNode = new Node<>(parentNode, data);
        parentNode.add(newNode);
        this._put(data, newNode);

        return newNode;
    }

    public long getVersion() {
        return version;
    }

    private void _put(T data, Node<T> newNode) {
        this.version++;
        this.nodes.put(data, newNode);
    }

    private void _remove(T data) {
        this.version++;
        this.nodes.remove(data);
    }

    /**
     * Helper method to build a tree from the given list of elements. The elements are added as root elements of the
     * tree.
     *
     * @param l the list of elements
     * @return a tree with all elements in the list
     */
    public static <T> Tree<T> asTree(T... l) {
        Tree<T> result = new Tree<>();
        for (T t : l) {
            result.add(t);
        }
        return result;
    }

    /**
     * Helper method to build a degenerated (i.e. a tree with only a single path) tree from the given list of elements.
     * The first element becomes the root node, all subsequent elements are added below their respective predecessors. *
     *
     * @param l the list of elements
     * @return a tree with all elements in the list
     */
    public static <T> Tree<T> asDegeneratedTree(T... l) {
        Tree<T> result = new Tree<>();
        T parent = null;
        for (T t : l) {
            if (parent == null) {
                result.add(t);
            } else {
                result.addChild(parent, t);
            }
            parent = t;
        }
        return result;
    }

    public interface Visitor<T> {

        boolean visit(T data);
    }

    public interface PrePostVisitor<T> {

        boolean visitPre(T data);

        boolean visitPost(T data);
    }

    public interface Filter<T> {
        boolean accept(T data);
    }

    public interface RetainingFilter<T> extends Filter<T> {

        boolean retain(T data);
    }

    public interface Mapper<T, S> {

        S map(T data);
    }

    public interface ElementFormatter<T> {

        String format(T data);
    }

    public static class IdentityMapper<S> implements Mapper<S, S> {

        @Override
        public S map(S data) {
            return data;
        }
    }

    public static class ItemWrapper<T> {

        private T item;

        public ItemWrapper(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }

    private static class Node<T> implements Serializable {

        public static final long serialVersionUID = 42L;

        private Node<T> parent;
        private T data;
        private final List<Node<T>> children = new ArrayList<>();

        public Node() {
        }

        public Node(Node<T> parent, T data) {
            this.parent = parent;
            this.data = data;
        }

        public Node<T> getParent() {
            return this.parent;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public List<Node<T>> getChildren() {
            return this.children;
        }

        public void add(Node<T> child) {
            this.children.add(child);
            child.parent = this;
        }

        public void add(int index, Node<T> child) {
            this.children.add(index, child);
            child.parent = this;
        }

        public void push(Node<T> child) {
            this.children.add(0, child);
            child.parent = this;
        }

        public void remove(Node<T> child) {
            this.children.remove(child);
        }

        public String toString(int depth, ElementFormatter<T> formatter) {
            String result = indent(depth * 2);

            if (this.data != null) {
                result += formatter.format(this.data) + "\n";
            } else {
                // ignore root node
                if (depth != -1) {
                    result += "\n";
                }
            }
            for (Node<T> child : this.children) {
                result += child.toString(depth + 1, formatter);
            }
            return result;
        }

        private String indent(int depth) {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                b.append(" ");
            }
            return b.toString();
        }

        public int getDepth() {
            int depth = 0;

            Node<T> node = this;
            while (node.getParent() != null) {
                depth++;
                node = node.getParent();
            }

            return depth - 1;
        }

        @Override
        public String toString() {
            return this.data != null ? this.data.toString() : "root";
        }
    }
}
