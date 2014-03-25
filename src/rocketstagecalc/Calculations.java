/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketstagecalc;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static rocketstagecalc.RocketStageCalc.spinnerStages;

/**
 *
 * @author Fil
 */
public class Calculations {

    static int[] iFuelMass = new int[5];
//    static int[] iFuelDensity = new int[5];
    static int[] iEngineMass = new int[5];
    static int[] iEngineThrust = new int[5];
    static int[] iEngineQuantity = new int[5];
    static int[] iEngineFuelUsage = new int[5];
    static int[] iParasiteMass = new int[5];
    static int[] iFullMass = new int[5];
    static int[] iEmptyMass = new int[5];
    static int[] iBurnTime = new int[5];
    static double[] dTWR = new double[5];
    static int iPN;
    static double[] deltaV = new double[5];

//     JTextField fieldFuelMass
//     JTextField fieldFuelDensity 
//     JTextField fieldEngineMass
//     JTextField fieldEngineThrust
//     JTextField fieldEngineQuantity
//     JTextField fieldEngineFuelUsage
//     JLabel labelParasiteMass 
//     JLabel labelTankMass 
//     JLabel labelFullMass
//     JLabel labelEmptyMass 
//     JLabel labelBurnTime 
    public static void getData() {


        try {
            iPN = Integer.parseInt(RocketStageCalc.fieldPN.getText());
        } catch (NumberFormatException numberFormatException) {
        }
        for (int i = 0; i < 5; i++) {

            try {
                iFuelMass[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldFuelMass.getText());
            } catch (NumberFormatException numberFormatException) {
                iFuelMass[i] = 0;
                System.out.println(numberFormatException.toString());
            }
//            try {
//                iFuelDensity[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldFuelDensity.getText());
//            } catch (NumberFormatException numberFormatException) {
//                iFuelDensity[i] = 0;
//                System.out.println(numberFormatException.toString());
//            }
            try {
                iEngineMass[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldEngineMass.getText());
            } catch (NumberFormatException numberFormatException) {
                iEngineMass[i] = 0;
                System.out.println(numberFormatException.toString());
            }
            try {
                iEngineThrust[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldEngineThrust.getText());
            } catch (NumberFormatException numberFormatException) {
                iEngineThrust[i] = 0;
                System.out.println(numberFormatException.toString());
            }
            try {
                iEngineQuantity[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldEngineQuantity.getText());
            } catch (NumberFormatException numberFormatException) {
                iEngineQuantity[i] = 0;
                System.out.println(numberFormatException.toString());
            }
            try {
                iEngineFuelUsage[i] = Integer.parseInt(PanelStagesArray.stagesArray[i].fieldEngineFuelUsage.getText());
            } catch (NumberFormatException numberFormatException) {
                iEngineFuelUsage[i] = 0;
                System.out.println(numberFormatException.toString());
            }
            try {
                iParasiteMass[i] = (int) (0.1 * iFuelMass[i]);
                iEmptyMass[i] = (int) (iEngineMass[i] * iEngineQuantity[i] + iParasiteMass[i]);
                iFullMass[i] = iEmptyMass[i] + iFuelMass[i];
                iBurnTime[i] = (int) (iFuelMass[i] / (iEngineFuelUsage[i] * iEngineQuantity[i]));
                int higherMass = iPN;
                int stages = Integer.parseInt(spinnerStages.getValue().toString());
                if (1 < stages) {
                    for (int k = 1; k < stages; k++) {
                        higherMass = higherMass + iFullMass[k];
                    }
                }
                dTWR[i] = ((iEngineQuantity[i] * iEngineThrust[i] * 1000 / ((higherMass + iFullMass[i]) * 9.81)));
            } catch (Exception e) {
            }
        }

    }

    public static void calculateLabels() {
        getData();

        for (int i = 0; i < 5; i++) {
            PanelStagesArray.stagesArray[i].labelParasiteMass.setText(Integer.toString(iParasiteMass[i]));
            PanelStagesArray.stagesArray[i].labelEmptyMass.setText(Integer.toString(iEmptyMass[i]));
            PanelStagesArray.stagesArray[i].labelFullMass.setText(Integer.toString(iFullMass[i]));
            PanelStagesArray.stagesArray[i].labelBurnTime.setText(Integer.toString(iBurnTime[i]));
            PanelStagesArray.stagesArray[i].labelStartingTWR.setText(String.format("%.2f", dTWR[i]));
        }
        validateButtons();
        RocketStageCalc.refreshFrame();
    }

    public static double calculateVelocity(int PN, int stages, boolean isAll) {
        double result = 0;

        for (int stage = 0; stage < stages; stage++) {
            int higherMass = PN;
            if (stage + 1 < stages) {
                for (int i = stage + 1; i < stages; i++) {
                    higherMass = higherMass + iFullMass[i];
                }
            }
            int m0 = higherMass + iFullMass[stage];
            for (int time = 0; time < iBurnTime[stage]; time++) {
                int m1 = m0 - iEngineFuelUsage[stage] * iEngineQuantity[stage];
                double ms = ((m0 + m1) / 2);
                double Fy = (1-Math.pow(result/7900,2))*9.81*ms; //(9.81 * ms - 9.81 * ms * (result / 7900) * 2);
                if (Fy < 0) {
                    Fy = 0;
                }
                double Fx = Math.sqrt(Math.pow(iEngineThrust[stage] * iEngineQuantity[stage] * 1000, 2)-Math.pow(Fy, 2));//iEngineThrust[stage] * iEngineQuantity[stage] * 1000 - Fy;
                if (Fx < 0) {
                    Fx = 0;
                }
                result = (result + Fx / ms);
                m0 = m1;
            }
            if (isAll) {
                if (stage == 0) {
                    deltaV[stage] = result;
                } else {
                    double leftSum=0;
                    for (int k=0;k<stage;k++){
                        leftSum=leftSum+deltaV[k];
                    }
                    deltaV[stage] = result - leftSum;
                }
            }
        }
        return result;
    }

    public static int calculateMaxPN(int stages) {
        deltaV = new double[5];
        int result = 0;

        int PNLeft = 50;

        while (calculateVelocity(PNLeft, stages, false) > 7900) {
            PNLeft = PNLeft + 1000;
        }
        System.out.println(calculateVelocity(PNLeft, stages, false));
        int PNRight = PNLeft - 1000;

        double error = Math.abs(calculateVelocity(PNLeft, stages, false) - 7900);
        System.out.println("Слева " + Double.toString(PNLeft) + "; Справа " + Double.toString(PNRight) + "; Ошибка " + Double.toString(error));
        boolean calcError = false;
        while ((error / 7900 > 0.001) && !calcError) {
            double olderror = error;
            if (calculateVelocity((PNLeft + PNRight) / 2, stages, false) > 7900) {
                PNRight = (PNLeft + PNRight) / 2;
            } else {
                PNLeft = (PNLeft + PNRight) / 2;
            }
            error = Math.abs(calculateVelocity((PNLeft + PNRight) / 2, stages, false) - 7900);
            System.out.println("Слева " + Double.toString(PNLeft) + "; Справа " + Double.toString(PNRight) + "; Ошибка " + Double.toString(error));
            if (Math.abs(olderror - error) < 0.0001) { //аварийный выход если алгоритм уйдет не туда
                PNLeft = 0;
                PNRight = 0;
                calcError = true;
            }
        }
        result = (PNLeft + PNRight) / 2;
        calculateVelocity(result, stages, true);
        return result;
    }

    public static JPanel showDeltaV(int stages) {
        JPanel result = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        try {
            for (int i = 0; i < stages; i++) {
                gbc.gridx = i;
                gbc.gridy = 0;
                gbc.weightx = 1;
                JLabel labelDeltaV = new JLabel(String.format("%.2f", deltaV[i]));
                labelDeltaV.setHorizontalAlignment(JLabel.CENTER);
                result.add(labelDeltaV, gbc);
            }
        } catch (Exception e) {
            //ошибки игнорируем
            System.out.println(e.getStackTrace());
        }

        return result;
    }

    public static void validateButtons() {
        boolean isReady = true;
        boolean isPNReady = true;
        String errorText = "";
        int stages = Integer.parseInt(spinnerStages.getValue().toString());

        for (int i = 0; i < stages; i++) {

            if (iEngineQuantity[i] < 1) {
                errorText = errorText + "Неверное количество двигателей ";
                isReady = false;
                isPNReady = false;
            }
            if (iFuelMass[i] < 1) {
                errorText = errorText + "Неверная масса топлива ";
                isReady = false;
                isPNReady = false;
            }
//            if (iFuelDensity[i] < 1) {
//                errorText = errorText + "Неверная плотность топлива ";
//                isReady = false;
//                isPNReady = false;
//            }
            if (iEngineMass[i] < 1) {
                errorText = errorText + "Неверная масса двигателя ";
                isReady = false;
                isPNReady = false;
            }
            if (iEngineMass[i] < 1) {
                errorText = errorText + "Неверная масса двигателя ";
                isReady = false;
                isPNReady = false;
            }
            if (iEngineThrust[i] < 1) {
                errorText = errorText + "Неверная тяга двигателя ";
                isReady = false;
                isPNReady = false;
            }
            if (iEngineFuelUsage[i] < 1) {
                errorText = errorText + "Неверный расход топлива двигателем ";
                isReady = false;
                isPNReady = false;
            }

        }
        int higherMass = iPN;
        if (1 < stages) {
            for (int k = 1; k < stages; k++) {
                higherMass = higherMass + iFullMass[k];
            }
        }
        higherMass = higherMass + iFullMass[0];
        if (higherMass * 9.81 > iEngineThrust[0] * iEngineQuantity[0] * 1000) {
            isReady = false;
            isPNReady = false;
            errorText = errorText + "Не взлетит ";
        }
        if (calculateVelocity(50, stages, false) <= 7900) {
            isPNReady = false;
            errorText = errorText + "ПН <50 кг, оптимизация не имеет смысла ";
        }
        if (RocketStageCalc.buttonContest.isSelected()) {
            int fullFuelMass = 0;
            for (int i = 0; i < stages; i++) {
                fullFuelMass = fullFuelMass + iFuelMass[i];
            }
            if (fullFuelMass > 300000) {
                isPNReady = false;
                errorText = errorText + "Ограничение на 300 тонн топлива в соревновании";
            }
        }
        RocketStageCalc.buttonCalc.setEnabled(isReady);
        RocketStageCalc.buttonMaxPN.setEnabled(isPNReady);
        RocketStageCalc.labelErrors.setText(errorText);
    }
}
