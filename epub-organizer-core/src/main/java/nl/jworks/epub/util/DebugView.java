package nl.jworks.epub.util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DebugView implements PropertyChangeListener {


    private static JLabel numberOfMatchesLabel;
    private Debug debug;

    public static void main(String[] args) {
        new DebugView().show();
    }

    public DebugView() {
        init();
    }

    private void init() {
        debug = Debug.getInstance();
        debug.addPropertyChangeListener(this);
    }

    public void show() {
        JFrame frame = new JFrame("Debug console");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel sliderPanel = buildSliderPanel();
        JPanel infoPanel = buildInfoPanel();

        contentPane.add(sliderPanel);
        contentPane.add(infoPanel);

        //Display the window.
        frame.setLocation(500, 500);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel buildInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(new JLabel("Number of matches:"));
        numberOfMatchesLabel = new JLabel("0");
        infoPanel.add(numberOfMatchesLabel);

        return infoPanel;
    }

    private void setMatches(int numberOfMatches) {
        numberOfMatchesLabel.setText(String.valueOf(numberOfMatches));
    }

    private JPanel buildSliderPanel() {
        JPanel sliderPanel = new JPanel();

        final JSlider slider = new JSlider(0, 10000, debug.getSleepInMilliSeconds());
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                debug.setSleepInMilliSeconds(slider.getValue());
            }
        });
        sliderPanel.add(slider);
        return sliderPanel;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("matches".equals(evt.getPropertyName())) {
            setMatches((Integer) evt.getNewValue());
        }

    }
}
