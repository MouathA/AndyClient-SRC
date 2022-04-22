package com.sun.jna.platform.dnd;

import java.awt.dnd.*;
import java.awt.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.io.*;

public abstract class DropHandler implements DropTargetListener
{
    private int acceptedActions;
    private List acceptedFlavors;
    private DropTarget dropTarget;
    private boolean active;
    private DropTargetPainter painter;
    private String lastAction;
    
    public DropHandler(final Component component, final int n) {
        this(component, n, new DataFlavor[0]);
    }
    
    public DropHandler(final Component component, final int n, final DataFlavor[] array) {
        this(component, n, array, null);
    }
    
    public DropHandler(final Component component, final int acceptedActions, final DataFlavor[] array, final DropTargetPainter painter) {
        this.active = true;
        this.acceptedActions = acceptedActions;
        this.acceptedFlavors = Arrays.asList(array);
        this.painter = painter;
        this.dropTarget = new DropTarget(component, acceptedActions, this, this.active);
    }
    
    protected DropTarget getDropTarget() {
        return this.dropTarget;
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public void setActive(final boolean b) {
        this.active = b;
        if (this.dropTarget != null) {
            this.dropTarget.setActive(b);
        }
    }
    
    protected int getDropActionsForFlavors(final DataFlavor[] array) {
        return this.acceptedActions;
    }
    
    protected int getDropAction(final DropTargetEvent dropTargetEvent) {
        Point point = null;
        DataFlavor[] array = new DataFlavor[0];
        if (dropTargetEvent instanceof DropTargetDragEvent) {
            final DropTargetDragEvent dropTargetDragEvent = (DropTargetDragEvent)dropTargetEvent;
            dropTargetDragEvent.getDropAction();
            dropTargetDragEvent.getSourceActions();
            array = dropTargetDragEvent.getCurrentDataFlavors();
            point = dropTargetDragEvent.getLocation();
        }
        else if (dropTargetEvent instanceof DropTargetDropEvent) {
            final DropTargetDropEvent dropTargetDropEvent = (DropTargetDropEvent)dropTargetEvent;
            dropTargetDropEvent.getDropAction();
            dropTargetDropEvent.getSourceActions();
            array = dropTargetDropEvent.getCurrentDataFlavors();
            point = dropTargetDropEvent.getLocation();
        }
        if (this.isSupported(array)) {
            this.getDropAction(dropTargetEvent, 0, 0, this.getDropActionsForFlavors(array));
            if (false && this.canDrop(dropTargetEvent, 0, point)) {
                return 0;
            }
        }
        return 0;
    }
    
    protected int getDropAction(final DropTargetEvent dropTargetEvent, int n, final int n2, final int n3) {
        final boolean modifiersActive = this.modifiersActive(n);
        if ((n & n3) == 0x0 && !modifiersActive) {
            n = (n3 & n2);
        }
        else if (modifiersActive) {
            final int n4 = n & n3 & n2;
            if (n4 != n) {
                n = n4;
            }
        }
        return n;
    }
    
    protected boolean modifiersActive(final int n) {
        final int modifiers = DragHandler.getModifiers();
        if (modifiers == -1) {
            return n == 1073741824 || n == 1;
        }
        return modifiers != 0;
    }
    
    private void describe(final String s, final DropTargetEvent dropTargetEvent) {
    }
    
    protected int acceptOrReject(final DropTargetDragEvent dropTargetDragEvent) {
        final int dropAction = this.getDropAction(dropTargetDragEvent);
        if (dropAction != 0) {
            dropTargetDragEvent.acceptDrag(dropAction);
        }
        else {
            dropTargetDragEvent.rejectDrag();
        }
        return dropAction;
    }
    
    public void dragEnter(final DropTargetDragEvent dropTargetDragEvent) {
        this.describe("enter(tgt)", dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, this.acceptOrReject(dropTargetDragEvent), dropTargetDragEvent.getLocation());
    }
    
    public void dragOver(final DropTargetDragEvent dropTargetDragEvent) {
        this.describe("over(tgt)", dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, this.acceptOrReject(dropTargetDragEvent), dropTargetDragEvent.getLocation());
    }
    
    public void dragExit(final DropTargetEvent dropTargetEvent) {
        this.describe("exit(tgt)", dropTargetEvent);
        this.paintDropTarget(dropTargetEvent, 0, null);
    }
    
    public void dropActionChanged(final DropTargetDragEvent dropTargetDragEvent) {
        this.describe("change(tgt)", dropTargetDragEvent);
        this.paintDropTarget(dropTargetDragEvent, this.acceptOrReject(dropTargetDragEvent), dropTargetDragEvent.getLocation());
    }
    
    public void drop(final DropTargetDropEvent dropTargetDropEvent) {
        this.describe("drop(tgt)", dropTargetDropEvent);
        final int dropAction = this.getDropAction(dropTargetDropEvent);
        if (dropAction != 0) {
            dropTargetDropEvent.acceptDrop(dropAction);
            this.drop(dropTargetDropEvent, dropAction);
            dropTargetDropEvent.dropComplete(true);
        }
        else {
            dropTargetDropEvent.rejectDrop();
        }
        this.paintDropTarget(dropTargetDropEvent, 0, dropTargetDropEvent.getLocation());
    }
    
    protected boolean isSupported(final DataFlavor[] array) {
        final HashSet set = new HashSet((Collection<? extends E>)Arrays.asList(array));
        set.retainAll(this.acceptedFlavors);
        return !set.isEmpty();
    }
    
    protected void paintDropTarget(final DropTargetEvent dropTargetEvent, final int n, final Point point) {
        if (this.painter != null) {
            this.painter.paintDropTarget(dropTargetEvent, n, point);
        }
    }
    
    protected boolean canDrop(final DropTargetEvent dropTargetEvent, final int n, final Point point) {
        return true;
    }
    
    protected abstract void drop(final DropTargetDropEvent p0, final int p1) throws UnsupportedFlavorException, IOException;
}
