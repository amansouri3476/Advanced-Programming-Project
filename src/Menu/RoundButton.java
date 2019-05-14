package Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class RoundButton extends JButton
{
    // for mouse detection purposes
    Shape shape;

    public RoundButton(String label) {
        super(label);
        // allows us to paint a round background
        // if true, it would be rectangular
        setContentAreaFilled(false);
        setBackground(Color.orange);
    }

    protected void paintComponent(Graphics g) {
        // if the button is pressed and ready to be released
        if (getModel().isArmed()) {
            g.setColor(Color.red);
        } else {
            g.setColor(getBackground());
        }

        g.fillOval(0, 0, getSize().width-1, getSize().height-1);

        super.paintComponent(g);
    }

    // paint a round border as opposed to a rectangular one
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width-1, getSize().height-1);
    }

    // only clicks within the round shape should be accepted
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }

        return shape.contains(x, y);
    }
}