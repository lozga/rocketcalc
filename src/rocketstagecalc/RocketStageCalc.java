/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketstagecalc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Fil
 */
public class RocketStageCalc {

    public static JFrame mainFrame;
    public static JPanel panelStages;
    public static JPanel panelStagesList;
    public static JSpinner spinnerStages;
    public static JLabel labelResult;
    public static JPanel panelDeltaV;
    public static JTextField fieldPN;
    public static JButton buttonCalc;
    public static JButton buttonMaxPN;
    public static JLabel labelErrors;
    public static JToggleButton buttonContest;
    public static JButton buttonLoadDefaults;

    public static void main(String[] args) {


        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RocketStageCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RocketStageCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RocketStageCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RocketStageCalc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        PanelStagesArray.initialize();
        mainFrame = new JFrame("Расчет ПН многоступенчатой ракеты с учетом гравитационных потерь");

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        panelStages = new JPanel(new GridLayout(1, 0));
        JLabel labelStages = new JLabel("Количество ступеней");
        panelStages.add(labelStages);

        spinnerStages = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        spinnerStages.addChangeListener(listenerstageupdate);
        panelStages.add(spinnerStages);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;
        mainPanel.add(panelStages, gbc);

        JPanel panelPN = new JPanel(new GridLayout(1, 0));
        JLabel labelPN = new JLabel("ПН, кг");
        labelPN.setHorizontalAlignment(JLabel.RIGHT);
        panelStages.add(labelPN);

        fieldPN = new JTextField("1000");
        fieldPN.getDocument().addDocumentListener(listenerChange);
        panelStages.add(fieldPN);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(panelPN, gbc);

        buttonLoadDefaults = new JButton("<html>Minotaur V</html>");
        buttonLoadDefaults.addActionListener(listenerLoadDefaults);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        mainPanel.add(buttonLoadDefaults, gbc);

        buttonContest = new JToggleButton("Соревнование");
        buttonContest.addActionListener(listenerContest);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 8;
        mainPanel.add(buttonContest, gbc);

        panelStagesList = new JPanel(new GridLayout(0, 1));
        panelStagesList.add(PanelStagesArray.createPanelStages(5), gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 5;
        mainPanel.add(panelStagesList, gbc);

        JPanel panelButtons = new JPanel(new GridLayout(1, 0));
        panelButtons.add(createButtons());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 5;
        mainPanel.add(panelButtons, gbc);

        panelDeltaV = new JPanel(new GridLayout(1, 0));
        panelDeltaV.add(Calculations.showDeltaV(Integer.parseInt(spinnerStages.getValue().toString())));
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Распределение delta-V по ступеням");
        border.setTitleJustification(TitledBorder.CENTER);
        panelDeltaV.setBorder(border);
        panelDeltaV.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 5;
        mainPanel.add(panelDeltaV, gbc);

        labelResult = new JLabel();
        labelResult.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 5;
        mainPanel.add(labelResult, gbc);

        mainFrame.setMinimumSize(new Dimension(400 + 150 * 5, 700));
        mainFrame.setMaximumSize(new Dimension(400 + 150 * 5, 700));
        mainFrame.setPreferredSize(new Dimension(400 + 150 * 5, 700));
        mainFrame.setSize(new Dimension(400 + 150 * 5, 700));


        mainFrame.setContentPane(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    private static ChangeListener listenerstageupdate = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            RocketStageUpdate();
        }
    };
    private static ActionListener listenerLoadDefaults = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadDefaults();
        }
    };
    private static ActionListener listenerCalculate = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            double result = Calculations.calculateVelocity(Integer.parseInt(fieldPN.getText()), Integer.parseInt(spinnerStages.getValue().toString()), true);
            panelDeltaV.removeAll();
            panelDeltaV.add(Calculations.showDeltaV(Integer.parseInt(spinnerStages.getValue().toString())));
            panelDeltaV.setVisible(true);
            labelResult.setText("<html><b>Итоговая скорость: " + String.format("%.2f", result) + " м/с</b></html>");
            refreshFrame();
        }
    };
    private static ActionListener listenerMaxPN = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            int PN = Calculations.calculateMaxPN(Integer.parseInt(spinnerStages.getValue().toString()));
            panelDeltaV.removeAll();
            panelDeltaV.add(Calculations.showDeltaV(Integer.parseInt(spinnerStages.getValue().toString())));
            panelDeltaV.setVisible(true);
            labelResult.setText("<html><b>Максимальная ПН для этой конфигурации: " + Integer.toString(PN) + " кг</b></html>");
            refreshFrame();
        }
    };
    private static DocumentListener listenerChange = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            Calculations.calculateLabels();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            Calculations.calculateLabels();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            Calculations.calculateLabels();
        }
    };
    private static ActionListener listenerContest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            PanelStagesArray.setContest(buttonContest.isSelected());
        }
    };

    public static void loadDefaults() {
        panelStagesList.removeAll();
        int amount = 5;
        PanelStagesArray.loadMinV();
        panelStagesList.add(PanelStagesArray.createPanelStages(amount));

        panelDeltaV.setVisible(false);
        mainFrame.setMinimumSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setMaximumSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setPreferredSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setSize(new Dimension(400 + 150 * amount, 700));

        refreshFrame();
    }

    public static void RocketStageUpdate() {
        panelStagesList.removeAll();
        int amount = Integer.parseInt(spinnerStages.getValue().toString());
        panelStagesList.add(PanelStagesArray.createPanelStages(amount));

        mainFrame.setMinimumSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setMaximumSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setPreferredSize(new Dimension(400 + 150 * amount, 700));
        mainFrame.setSize(new Dimension(400 + 150 * amount, 700));
        Calculations.calculateLabels();
        refreshFrame();
    }

    public static void refreshFrame() {
        mainFrame.validate();
    }

    public static JPanel createButtons() {

        labelErrors = new JLabel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel panelResult = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 5;
        panelResult.add(labelErrors, gbc);

        buttonCalc = new JButton("Посчитать скорость");
        buttonCalc.addActionListener(listenerCalculate);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        panelResult.add(buttonCalc, gbc);

        buttonMaxPN = new JButton("Максимизировать ПН");
        buttonMaxPN.addActionListener(listenerMaxPN);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        panelResult.add(buttonMaxPN, gbc);

        return panelResult;
    }
}
