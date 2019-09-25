package View;

import DocumentCreator.PDFCreator;
import Model.ReftecReader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainPanel extends JPanel implements ActionListener {//Panel de L'IHM

    private String absoluteInputPath;
    private String absoluteOutputPath;
    private String[] tabSteps = {"J-30 Pré-OPR", "J-7 RO", "J+30 RO"};
    private String[] tabMode = {"Mode station", "Mode ligne", "Mode ligne détails"};
    private PDFCreator creator;

    private final JLabel labelSelectInput = new JLabel("Selectionner un extract REFTEC : (format .xlsx) ");
    private JTextField fieldInputPath = new JTextField();
    private JButton buttonInput = new JButton("Ouvrir Input");

    private final JLabel labelSelectOuput = new JLabel("Selectionner un dossier de destination :");
    private JTextField fieldOutputPath = new JTextField();
    private JButton buttonOutput = new JButton("Ouvrir Output");

    private JLabel labelComboBoxSteps = new JLabel("Choisir une étape de vérification :");
    private JComboBox comboBoxSteps = new JComboBox(tabSteps);

    private JComboBox comboBoxMode = new JComboBox(tabMode);
    private JButton buttonValidate = new JButton("Valider");

    public MainPanel() throws Exception{
        super();
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(labelSelectInput);
        this.add(fieldInputPath);
        this.add(buttonInput);
        buttonInput.addActionListener(this);

        this.add(labelSelectOuput);
        this.add(fieldOutputPath);
        this.add(buttonOutput);
        buttonOutput.addActionListener(this);

        this.add(labelComboBoxSteps);
        this.add(comboBoxSteps);

        this.add(comboBoxMode);
        this.add(buttonValidate);
        buttonValidate.addActionListener(this);


    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonInput){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                absoluteInputPath = selectedFile.getAbsolutePath();
                System.out.println(absoluteInputPath);
                //labelSelectInput.setText(labelSelectInput.getText() + absoluteInputPath);
                fieldInputPath.setText(absoluteInputPath);
            }
        }
        else if(e.getSource() == buttonOutput){
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setAcceptAllFileFilterUsed(false);

            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                absoluteOutputPath = selectedFile.getAbsolutePath();
                System.out.println(absoluteOutputPath);
                fieldOutputPath.setText(absoluteOutputPath);

            }
        }
        else if(e.getSource() == buttonValidate){
            if(absoluteInputPath == null || absoluteOutputPath == null){
                JOptionPane.showMessageDialog(this.getParent(),
                        "Veuillez sélectionner un fichier d'entrée et un dossier de sortie",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                try {
                    creator = new PDFCreator(absoluteInputPath, absoluteOutputPath,comboBoxSteps.getSelectedItem().toString(), comboBoxMode.getSelectedItem().toString());
                    if(creator.getGenerated()) {
                        JOptionPane.showMessageDialog(this.getParent(), "Rapport généré ! \n Localisation : " + absoluteOutputPath + "\\" + creator.getFileName(), "Rapport généré",JOptionPane.INFORMATION_MESSAGE);
                        //System.exit(0);
                        absoluteOutputPath = "";
                        absoluteOutputPath = "";
                        fieldInputPath.setText("");
                        fieldOutputPath.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(this.getParent(),
                                "Erreur rencontrée ! Vérifiez qu'un ancien rapport généré n'est pas ouvert",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                }
            }
        }
    }
}
