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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import static rocketstagecalc.RocketStageCalc.buttonContest;
import static rocketstagecalc.RocketStageCalc.fieldPN;

/**
 *
 * @author Fil
 */
public class PanelStagesArray {

    public static PanelStage[] stagesArray = new PanelStage[5];

    public static void initialize() {
        for (int i = 0; i < 5; i++) {
            stagesArray[i] = new PanelStage();
        }
    }

    public static JPanel createPanelStages(int amount) {
        JPanel returnPanel = new JPanel(new GridLayout(1, 0));
        returnPanel.setMaximumSize(new Dimension(1200, 500));
//        stagesArray = new PanelStage[amount];
        JPanel[] panelOneStage = new JPanel[amount];

        for (int i = 0; i < amount; i++) {
            panelOneStage[i] = new JPanel(new GridBagLayout());
            TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Ступень " + Integer.toString(i + 1));
            border.setTitleJustification(TitledBorder.CENTER);
            panelOneStage[i].setBorder(border);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 2, 5, 2);


            JLabel labelEngineQuantity = new JLabel("<html>Количество двигателей</html>");
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            panelOneStage[i].add(labelEngineQuantity, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 0;
            stagesArray[i].fieldEngineQuantity.getDocument().addDocumentListener(listenerUpdateLabels);
            panelOneStage[i].add(stagesArray[i].fieldEngineQuantity, gbc);

            JLabel labelEngineThrust = new JLabel("<html>Тяга одного двигателя, кН</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 1;
            panelOneStage[i].add(labelEngineThrust, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 1;
            stagesArray[i].fieldEngineThrust.getDocument().addDocumentListener(listenerUpdateLabels);
            panelOneStage[i].add(stagesArray[i].fieldEngineThrust, gbc);

            JLabel labelEngineFuelUsage = new JLabel("<html>Расход всех компонентов топлива одного двигателя, кг/с</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 2;
            panelOneStage[i].add(labelEngineFuelUsage, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 2;
            stagesArray[i].fieldEngineFuelUsage.getDocument().addDocumentListener(listenerUpdateLabels);
            panelOneStage[i].add(stagesArray[i].fieldEngineFuelUsage, gbc);

            JLabel labelEngineMass = new JLabel("<html>Сухая масса двигателя, кг</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 3;
            panelOneStage[i].add(labelEngineMass, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 3;
            stagesArray[i].fieldEngineMass.getDocument().addDocumentListener(listenerUpdateLabels);
            panelOneStage[i].add(stagesArray[i].fieldEngineMass, gbc);

            JLabel labelFuelMass = new JLabel("<html>Масса топлива, кг</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 4;
            panelOneStage[i].add(labelFuelMass, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 4;
            stagesArray[i].fieldFuelMass.getDocument().addDocumentListener(listenerUpdateLabels);
            panelOneStage[i].add(stagesArray[i].fieldFuelMass, gbc);

//            JLabel labelFuelDensity = new JLabel("<html>Плотность топлива, кг/м3</html>");
//            gbc.weightx = 0;
//            gbc.gridx = 0;
//            gbc.gridy = 5;
//            panelOneStage[i].add(labelFuelDensity, gbc);
//            gbc.weightx = 1;
//            gbc.gridx = 1;
//            gbc.gridy = 5;
//            stagesArray[i].fieldFuelDensity.getDocument().addDocumentListener(listenerUpdateLabels);
//            panelOneStage[i].add(stagesArray[i].fieldFuelDensity, gbc);

            JLabel labelParasiteMass = new JLabel("<html>Паразитная масса, кг</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 6;
            panelOneStage[i].add(labelParasiteMass, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 6;
            panelOneStage[i].add(stagesArray[i].labelParasiteMass, gbc);

            JLabel labelFullMass = new JLabel("<html>Полная масса, кг</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 7;
            panelOneStage[i].add(labelFullMass, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 7;
            panelOneStage[i].add(stagesArray[i].labelFullMass, gbc);

            JLabel labelEmptyMass = new JLabel("<html>Пустая масса, кг</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 8;
            panelOneStage[i].add(labelEmptyMass, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 8;
            panelOneStage[i].add(stagesArray[i].labelEmptyMass, gbc);

            JLabel labelBurnTime = new JLabel("<html>Время работы, с</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 9;
            panelOneStage[i].add(labelBurnTime, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 9;
            panelOneStage[i].add(stagesArray[i].labelBurnTime, gbc);
            
                        JLabel labelTWR = new JLabel("<html>Начальная тяговооруженность</html>");
            gbc.weightx = 0;
            gbc.gridx = 0;
            gbc.gridy = 10;
            panelOneStage[i].add(labelTWR, gbc);
            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridy = 10;
            panelOneStage[i].add(stagesArray[i].labelStartingTWR, gbc);

            returnPanel.add(panelOneStage[i]);
        }

        return returnPanel;
    }
    private static DocumentListener listenerUpdateLabels = new DocumentListener() {
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

    public static void loadMinV() {
        try {

            stagesArray[0].fieldEngineQuantity.setText("1");
            stagesArray[0].fieldEngineThrust.setText("1607");
            stagesArray[0].fieldEngineFuelUsage.setText("588");
            stagesArray[0].fieldFuelMass.setText("48809");
//            stagesArray[0].fieldFuelDensity.setText("1770");
            stagesArray[0].fieldEngineMass.setText("900");

            stagesArray[1].fieldEngineQuantity.setText("1");
            stagesArray[1].fieldEngineThrust.setText("1365");
            stagesArray[1].fieldEngineFuelUsage.setText("461");
            stagesArray[1].fieldFuelMass.setText("24900");
//            stagesArray[1].fieldFuelDensity.setText("1770");
            stagesArray[1].fieldEngineMass.setText("500");

            stagesArray[2].fieldEngineQuantity.setText("1");
            stagesArray[2].fieldEngineThrust.setText("329");
            stagesArray[2].fieldEngineFuelUsage.setText("110");
            stagesArray[2].fieldFuelMass.setText("6800");
//            stagesArray[2].fieldFuelDensity.setText("1770");
            stagesArray[2].fieldEngineMass.setText("200");

            stagesArray[3].fieldEngineQuantity.setText("1");
            stagesArray[3].fieldEngineThrust.setText("69");
            stagesArray[3].fieldEngineFuelUsage.setText("25");
            stagesArray[3].fieldFuelMass.setText("2060");
//            stagesArray[3].fieldFuelDensity.setText("1800");
            stagesArray[3].fieldEngineMass.setText("50");

            stagesArray[4].fieldEngineQuantity.setText("1");
            stagesArray[4].fieldEngineThrust.setText("35");
            stagesArray[4].fieldEngineFuelUsage.setText("11");
            stagesArray[4].fieldFuelMass.setText("771");
//            stagesArray[4].fieldFuelDensity.setText("1800");
            stagesArray[4].fieldEngineMass.setText("50");




        } catch (Exception e) {
            //ошибка игнорируется
            System.out.println(e.getStackTrace());
        }
        Calculations.calculateLabels();
    }

    public static void loadContest() {
        try {

            stagesArray[0].fieldEngineQuantity.setText("9");
            stagesArray[0].fieldEngineThrust.setText("400");
            stagesArray[0].fieldEngineFuelUsage.setText("140");
            stagesArray[0].fieldFuelMass.setText("250000");
//            stagesArray[0].fieldFuelDensity.setText("1770");
            stagesArray[0].fieldEngineMass.setText("630");

            stagesArray[1].fieldEngineQuantity.setText("3");
            stagesArray[1].fieldEngineThrust.setText("400");
            stagesArray[1].fieldEngineFuelUsage.setText("140");
            stagesArray[1].fieldFuelMass.setText("30000");
//            stagesArray[1].fieldFuelDensity.setText("1770");
            stagesArray[1].fieldEngineMass.setText("630");

            stagesArray[2].fieldEngineQuantity.setText("2");
            stagesArray[2].fieldEngineThrust.setText("400");
            stagesArray[2].fieldEngineFuelUsage.setText("140");
            stagesArray[2].fieldFuelMass.setText("14000");
//            stagesArray[2].fieldFuelDensity.setText("1770");
            stagesArray[2].fieldEngineMass.setText("630");

            stagesArray[3].fieldEngineQuantity.setText("1");
            stagesArray[3].fieldEngineThrust.setText("400");
            stagesArray[3].fieldEngineFuelUsage.setText("140");
            stagesArray[3].fieldFuelMass.setText("4000");
//            stagesArray[3].fieldFuelDensity.setText("1800");
            stagesArray[3].fieldEngineMass.setText("630");

            stagesArray[4].fieldEngineQuantity.setText("1");
            stagesArray[4].fieldEngineThrust.setText("400");
            stagesArray[4].fieldEngineFuelUsage.setText("140");
            stagesArray[4].fieldFuelMass.setText("2000");
//            stagesArray[4].fieldFuelDensity.setText("1800");
            stagesArray[4].fieldEngineMass.setText("630");




        } catch (Exception e) {
            //ошибка игнорируется
            System.out.println(e.getStackTrace());
        }
        Calculations.calculateLabels();
    }

    public static void setContest(boolean status) {
        if (status) {
            loadContest();
            fieldPN.setEnabled(false);
            RocketStageCalc.buttonLoadDefaults.setEnabled(false);
            for (int i = 0; i < 5; i++) {
                stagesArray[i].fieldEngineFuelUsage.setEnabled(false);
                stagesArray[i].fieldEngineMass.setEnabled(false);
                stagesArray[i].fieldEngineThrust.setEnabled(false);
//                stagesArray[i].fieldFuelDensity.setEnabled(false);
            }
        } else {
            fieldPN.setEnabled(true);
            RocketStageCalc.buttonLoadDefaults.setEnabled(true);
            for (int i = 0; i < 5; i++) {
                stagesArray[i].fieldEngineFuelUsage.setEnabled(true);
                stagesArray[i].fieldEngineMass.setEnabled(true);
                stagesArray[i].fieldEngineThrust.setEnabled(true);
//                stagesArray[i].fieldFuelDensity.setEnabled(true);
            }
        }
        RocketStageCalc.refreshFrame();
    }
}
