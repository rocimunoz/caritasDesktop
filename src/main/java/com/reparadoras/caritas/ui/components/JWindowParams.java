
package com.reparadoras.caritas.ui.components;

import java.util.Hashtable;

import javax.swing.JDesktopPane;

import java.awt.Rectangle;
import java.awt.Point;



public class JWindowParams {
    
    public  static final int IMODE_UNDEF  = 0;
    public  static final int IMODE_INSERT = 1;
    public  static final int IMODE_UPDATE = 2;
    public  static final int IMODE_DELETE = 3;
    public  static final int IMODE_SELECT = 4;
    
    public  static final int OMODE_ACCEPT = 1;
    public  static final int OMODE_CANCEL = 2;
    
    private Hashtable htParams = new Hashtable();
    
    private Object parent = null;
    private boolean modal = false;
    private Rectangle bounds = null;
    private Point position = null;
    private boolean maximized = false;
    private int inputMode = 0;
    private int outputMode = 0;
    private JDesktopPane jCicScrollableDesktopPane = null;
    private boolean loadOnOpen = false;
    private String title = "";
    
    /**
     * Crea una instancia de JCicWindowParams. Por defecto, el parent de la
     * ventana se considera null, el modo de entrada queda indefinido y
     * considera que no debe ser modal
     */
    public JWindowParams() {
        this(null, JWindowParams.IMODE_UNDEF, false);
    }
    
    /**
     * Crea una instancia de JCicWindowParams
     * @param parent referencia al objeto "parent" de la ventana
     */
    public JWindowParams(Object parent) {
        this(parent, JWindowParams.IMODE_UNDEF, false);
    }
    
    /**
     * Crea una instancia de JCicWindowParams
     * @param parent referencia al objeto "parent" de la ventana
     * @param inputMode indica un modo de entrada para la ventana, a
     * elegir entre JCicWindowParams.IMODE_INSERT, JCicWindowParams.IMODE_UPDATE, ...
     */
    public JWindowParams(Object parent, int inputMode) {
        this(parent, inputMode, false);
    }
    
    /**
     * Crea una instancia de JCicWindowParams
     * @param parent referencia al objeto "parent" de la ventana
     * @param modal indica si la ventana debe ser abierta de forma modal (true)
     * o no (false).
     */
    public JWindowParams(Object parent, boolean modal) {
        this(parent, 0, modal);
    }
    
    /**
     * Crea una instancia de JCicWindowParams
     * @param parent referencia al objeto "parent" de la ventana
     * @param inputMode indica un modo de entrada para la ventana, a
     * elegir entre JCicWindowParams.IMODE_INSERT, JCicWindowParams.IMODE_UPDATE, ...
     * @param modal indica si la ventana debe ser abierta de forma modal (true)
     * o no (false).
     */
    public JWindowParams(Object parent, int inputMode, boolean modal) {
        this.parent = parent;
        this.inputMode = inputMode;
        this.modal = modal;
        if (parent instanceof AbstractJInternalFrame) {
            this.jCicScrollableDesktopPane = (JDesktopPane)((AbstractJInternalFrame) parent).getDesktopPane();
        }
    }
    
    /**
     * Establece el modo en el que se va a abrir el formulario; estan definidos
     * como constantes: JCicWindowParams.IMODE_INSERT, JCicWindowParams.IMODE_UPDATE, ...
     */
    public void setInputMode(int mode) {
        inputMode = mode;
    }
    
    public int getInputMode() {
        return inputMode;
    }
    
    /**
     *
     */
    public void setOutputMode(int mode) {
        outputMode = mode;
    }
    
    public int getOutputMode() {
        return outputMode;
    }
    
    /**
     * Indica si la ventana debe ser abierta en forma modal o no
     */
    public void setModal(boolean m) {
        modal = m;
    }
    
    
    /**
     * Indica si la ventana debe ser abierta en forma modal (true) o no (false)
     */
    public boolean getModal() {
        return modal;
    }
    
    /**
     * Indica si la ventana debe ser abierta de forma maximizada (true) o no (false)
     */
    public void setMaximized(boolean m) {
        maximized = m;
    }
    
    public boolean getMaximized() {
        return maximized;
    }
    
    public void setParent(Object p) {
        parent = p;
    }
    
    public Object getParent() {
        return parent;
    }
    
    public void setPosition(int x, int y) {
        position = new Point(x, y);
    }
    
    public void setPosition(Point p) {
        position = p;
    }
    
    public Point getPosition() {
        return this.position;
    }
    
    public void setBounds(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }
    
    public void setBounds(Rectangle r) {
        bounds = r;
    }
    
    public Rectangle getBounds() {
        return this.bounds;
    }
    
    public void setUserParam(String name, Object param) {
        htParams.put(name, param);
    }
    
    public Object getUserParam(String name) {
        return htParams.get(name);
    }
    
    
    public void  setScrollableDesktopPane(JDesktopPane jCicScrollableDesktopPane) {
        this.jCicScrollableDesktopPane = jCicScrollableDesktopPane;
    }
    
    public JDesktopPane getScrollableDesktopPane() {
        return this.jCicScrollableDesktopPane;
    }
    
    public void setLoadOnOpen(boolean loadOnOpen) {
        this.loadOnOpen = loadOnOpen;
    }
    
    public boolean getLoadOnOpen() {
        return this.loadOnOpen;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
}

