package gui.toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.Objects;

public class AnchoredDialog extends JDialog {   // An anchored dialog is a dialog that follows a JComponent position and can
                                                // modify it's size to match the one of the JComponent we anchor it to

    public enum SizeAnchoring {NONE, WIDTH, HEIGHT, BOTH} // Define which size(s) have to match the size of the anchor component

    public enum AnchoringPointX {LEFT, CENTER, RIGHT} // Define which horizontal point of the dialog has to be used
                                                      // as an anchoring point. If we imagine that the dialog has 9 anchoring
                                                      // points, we can use one of the points as the one that we stick on the
                                                      // anchor component

    public enum AnchoringPointY {UP, CENTER, DOWN} // Define which vertical point of the dialog has to be used
                                                     // as an anchoring point.

    public enum SpawnPoint {UP, DOWN, CENTER, LEFT, RIGHT} // Define on which point of the anchored object the dialog have to spawn,
                                                           // for example, if we want our dialog to be under the anchor component, we
                                                           // will use DOWN


    private SizeAnchoring sizeAnchor;
    private AnchoringPointX anchorPointX;
    private AnchoringPointY anchorPointY;
    private SpawnPoint spawnPoint;

    protected JComponent anchorComponent; // it's the JComponent on which we anchor the dialog
    private JPanel mainPanel;
    private final ComponentListener windowListeners = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            updateDialogPos();
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            updateDialogPos();
        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    };

    public AnchoredDialog (JComponent anchorTo, LayoutManager panelLayout,
                           int width, int height, JPanel panel, SizeAnchoring sizeAnchoring,
                           AnchoringPointX anchoringPointX, AnchoringPointY anchoringPointY, SpawnPoint spawnPoint) {
        super();

        setLayout(new BorderLayout());
        setSize(width, height);
        setResizable(false);
        setVisible(false);
        setType(Window.Type.UTILITY);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setUndecorated(true);

        mainPanel = Objects.requireNonNullElseGet(panel, JPanel::new);

        if (panelLayout != null) {
            setLayout(panelLayout);
            add(mainPanel);
        } else {
            add(mainPanel, BorderLayout.NORTH);
            add(new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        }

        setAnchorTo(anchorTo);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                updateDialogPos();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                if(!Arrays.stream(SwingUtilities.getWindowAncestor(anchorComponent).getComponentListeners()).toList().contains(windowListeners))
                    SwingUtilities.getWindowAncestor(anchorComponent).addComponentListener(windowListeners);
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        sizeAnchor = sizeAnchoring;
        anchorPointX = anchoringPointX;
        anchorPointY = anchoringPointY;
        this.spawnPoint = spawnPoint;
    }

    public void setAnchorTo(JComponent comp) {
        anchorComponent = comp;
    }

    private int calculateAnchorOffsetX() {
        int anchorOffsetX = -1;
        if (anchorPointX == AnchoringPointX.LEFT) {
            anchorOffsetX = 0;
        } else if (anchorPointX == AnchoringPointX.CENTER) {
            anchorOffsetX = -(getWidth() / 2);

        } else if (anchorPointX == AnchoringPointX.RIGHT) {
            anchorOffsetX = -(getWidth());
        }
        return anchorOffsetX;
    }

    private int calculateAnchorOffsetY() {
        int anchorOffsetY = -1;
        if (anchorPointY == AnchoringPointY.UP) {
            anchorOffsetY = 0;
        } else if (anchorPointY == AnchoringPointY.CENTER) {
            anchorOffsetY = -(getHeight() / 2);

        } else if (anchorPointY == AnchoringPointY.DOWN) {
            anchorOffsetY = -(getHeight());
        }
        return anchorOffsetY;
    }

    private Dimension calculateSpawnOffset() {
        Dimension spawnOffset = new Dimension(0, 0);


        if (spawnPoint == SpawnPoint.UP) {
            spawnOffset.height = -anchorComponent.getHeight();
        }

        if (spawnPoint == SpawnPoint.DOWN) {
            spawnOffset.height = anchorComponent.getHeight();
        }


        if (spawnPoint == SpawnPoint.LEFT) {
            spawnOffset.width = -anchorComponent.getWidth();
        }

        if (spawnPoint == SpawnPoint.RIGHT) {
            spawnOffset.width = anchorComponent.getWidth();
        }


        return spawnOffset;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void updateDialogPos(){
        Dimension spawnPoint = calculateSpawnOffset();
        setLocation((int) anchorComponent.getLocationOnScreen().getX() + spawnPoint.width + calculateAnchorOffsetX(), (int) (anchorComponent.getLocationOnScreen().getY() + spawnPoint.height + calculateAnchorOffsetY()));

        Dimension d = new Dimension();
        d.height = ((sizeAnchor == SizeAnchoring.BOTH) || (sizeAnchor == SizeAnchoring.HEIGHT)) ? anchorComponent.getHeight() : getHeight();
        d.width = ((sizeAnchor == SizeAnchoring.BOTH) || (sizeAnchor == SizeAnchoring.WIDTH)) ? anchorComponent.getWidth() : getWidth();

        setSize(d);
    }
}
