package gui.panel;

import com.google.common.util.concurrent.Runnables;
import gui.Gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class SetupPanel extends JPanel {

    private static final String STEAM_PATH = "Steam Path";

    private static final String MINECRAFT_PATH = "Minecraft Saves Folder";

    private static final long serialVersionUID = -5816058833135992654L;

    private JTextField textField_MinecraftPath;
    private JTextField textField_SourcePath;

    private Runnable editedTextFieldMinecraftPath = Runnables.doNothing();
    private Runnable editedTextFieldMinecraftPathIntern = () -> this.editedTextFieldMinecraftPath.run();

    private Runnable editedTextFieldSourcePath = Runnables.doNothing();
    private Runnable editedTextFieldSourcePathIntern = () -> this.editedTextFieldSourcePath.run();

    private JLabel lbl_Issourcesdkinstalled;

    private Consumer<String> selectMinecraftPath;
    private Consumer<String> selectSourcePath;

    private JButton btnContinue_SetupContinue;
    private Runnable uponContinue = Runnables.doNothing();
    private Runnable uponContinueIntern = () -> this.uponContinue.run();

    private JPanel panel_Setup;

    public SetupPanel() {
        this.initialize();
    }

    public static void main(String[] args) {
        Tester.main(args);
    }

    public void setEditedTextFieldMinecraftPath(Runnable editedTextFieldMinecraftPath) {
        this.editedTextFieldMinecraftPath = editedTextFieldMinecraftPath;
    }

    public void setEditedTextFieldSourcePath(Runnable editedTextFieldSourcePath) {
        this.editedTextFieldSourcePath = editedTextFieldSourcePath;
    }

    public void setLabelIssourcesdkinstalled(String text) {
        this.lbl_Issourcesdkinstalled.setText(text);
    }

    public void setVisibilityLabelIssourcesdkinstalled(boolean visible) {
        this.lbl_Issourcesdkinstalled.setVisible(visible);
    }

    public void setEnabledButtonContinueAtSetup(boolean enabled) {
        this.btnContinue_SetupContinue.setEnabled(enabled);
    }

    public void setMinecraftPath(String path) {
        this.textField_MinecraftPath.setText(path);
    }

    public String getMincraftPath() {
        return this.textField_MinecraftPath.getText();
    }

    public String getSourcePath() {
        return this.textField_SourcePath.getText();
    }

    public void setSourcePath(String path) {
        this.textField_SourcePath.setText(path);
    }

    public void setSelectMinecraftPath(Consumer<String> selectMinecraftPath) {
        this.selectMinecraftPath = selectMinecraftPath;
    }

    public void setSelectSourcePath(Consumer<String> selectSourcePath) {
        this.selectSourcePath = selectSourcePath;
    }

    public void setTextFieldMincraftPathColor(Color color) {
        this.textField_MinecraftPath.setBackground(color);
    }

    public void setTextFieldSteamPathColor(Color color) {
        this.textField_SourcePath.setBackground(color);
    }

    public void setUponContinue(Runnable uponContinue) {
        this.uponContinue = uponContinue;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        this.panel_Setup = this; // new JPanel();
        SpringLayout sl_panel_Setup = new SpringLayout();
        this.panel_Setup.setLayout(sl_panel_Setup);

        this.btnContinue_SetupContinue = new JButton(Gui.CONTINUE_TEXT);
        this.configureContinueButton(this.btnContinue_SetupContinue);
        this.btnContinue_SetupContinue.addActionListener(arg0 -> this.uponContinueIntern.run());
        sl_panel_Setup.putConstraint(SpringLayout.SOUTH, this.btnContinue_SetupContinue, -10, SpringLayout.SOUTH,
                this.panel_Setup);
        sl_panel_Setup.putConstraint(SpringLayout.EAST, this.btnContinue_SetupContinue, -10, SpringLayout.EAST,
                this.panel_Setup);
        this.panel_Setup.add(this.btnContinue_SetupContinue);

        JButton btnSelectPath = new JButton(Gui.BROWSE_TEXT);
        Gui.configureBrowseButton(btnSelectPath); // test
        btnSelectPath.setFont(Gui.DEFAULT_FONT);
        sl_panel_Setup.putConstraint(SpringLayout.EAST, btnSelectPath, 0, SpringLayout.EAST,
                this.btnContinue_SetupContinue);
        btnSelectPath.addActionListener(e -> {
            String path = this.textField_MinecraftPath.getText();
            this.selectMinecraftPath.accept(path);
        });
        this.panel_Setup.add(btnSelectPath);

        JButton btnNewButton = new JButton(Gui.BROWSE_TEXT);
        sl_panel_Setup.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, this.panel_Setup);
        btnNewButton.setFont(Gui.DEFAULT_FONT);
        btnNewButton.addActionListener(e -> {
            String path = this.textField_SourcePath.getText();
            this.selectSourcePath.accept(path);
        });
        this.panel_Setup.add(btnNewButton);

        this.textField_MinecraftPath = new JTextField();
        sl_panel_Setup.putConstraint(SpringLayout.SOUTH, btnSelectPath, 0, SpringLayout.SOUTH,
                this.textField_MinecraftPath);
        sl_panel_Setup.putConstraint(SpringLayout.EAST, this.textField_MinecraftPath, -128, SpringLayout.EAST,
                this.panel_Setup);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, btnSelectPath, 6, SpringLayout.EAST,
                this.textField_MinecraftPath);
        this.textField_MinecraftPath.setFont(Gui.DEFAULT_FONT);
        this.addTextFieldChangeListener(this.textField_MinecraftPath, this.editedTextFieldMinecraftPathIntern);
        this.panel_Setup.add(this.textField_MinecraftPath);
        this.textField_MinecraftPath.setColumns(10);

        this.textField_SourcePath = new JTextField();
        sl_panel_Setup.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, this.textField_SourcePath);
        sl_panel_Setup.putConstraint(SpringLayout.EAST, this.textField_SourcePath, -128, SpringLayout.EAST, this);
        sl_panel_Setup.putConstraint(SpringLayout.SOUTH, btnNewButton, 0, SpringLayout.SOUTH,
                this.textField_SourcePath);
        this.textField_SourcePath.setFont(Gui.DEFAULT_FONT);
        this.addTextFieldChangeListener(this.textField_SourcePath, this.editedTextFieldSourcePathIntern);
        this.panel_Setup.add(this.textField_SourcePath);
        this.textField_SourcePath.setColumns(10);

        JLabel lbl_MinecraftPath = new JLabel(MINECRAFT_PATH);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, this.textField_MinecraftPath, 0, SpringLayout.WEST,
                lbl_MinecraftPath);
        lbl_MinecraftPath.setFont(Gui.DEFAULT_FONT);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, lbl_MinecraftPath, 10, SpringLayout.WEST, this.panel_Setup);
        sl_panel_Setup.putConstraint(SpringLayout.NORTH, this.textField_MinecraftPath, 6, SpringLayout.SOUTH,
                lbl_MinecraftPath);
        sl_panel_Setup.putConstraint(SpringLayout.NORTH, lbl_MinecraftPath, 10, SpringLayout.NORTH, this.panel_Setup);
        this.panel_Setup.add(lbl_MinecraftPath);

        JLabel lbl_SourcePath = new JLabel(STEAM_PATH);
        sl_panel_Setup.putConstraint(SpringLayout.NORTH, lbl_SourcePath, Gui.SEPARATING_DISTANCE, SpringLayout.SOUTH,
                this.textField_MinecraftPath);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, this.textField_SourcePath, 0, SpringLayout.WEST,
                lbl_SourcePath);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, lbl_SourcePath, 0, SpringLayout.WEST, lbl_MinecraftPath);
        sl_panel_Setup.putConstraint(SpringLayout.NORTH, this.textField_SourcePath, 6, SpringLayout.SOUTH,
                lbl_SourcePath);
        lbl_SourcePath.setFont(Gui.DEFAULT_FONT);
        this.panel_Setup.add(lbl_SourcePath);

        this.lbl_Issourcesdkinstalled = new JLabel("IsSourceSDKInstalled");
        sl_panel_Setup.putConstraint(SpringLayout.NORTH, this.lbl_Issourcesdkinstalled, 6, SpringLayout.SOUTH,
                this.textField_SourcePath);
        sl_panel_Setup.putConstraint(SpringLayout.WEST, this.lbl_Issourcesdkinstalled, 0, SpringLayout.WEST,
                this.textField_MinecraftPath);
        this.lbl_Issourcesdkinstalled.setText("Source SDK is installed.");
        this.lbl_Issourcesdkinstalled.setVisible(false);
        this.panel_Setup.add(this.lbl_Issourcesdkinstalled);

    }

    /**
     * Create the application.
     *
     * @wbp.parser.entryPoint
     */

    public void configureContinueButton(JButton button) {
        button.setPreferredSize(Gui.DIMENSION_OF_CONTINUE);
        button.setFont(Gui.FONT_CONTINUE);
    }

    private void addTextFieldChangeListener(JTextField textField, Runnable action) {
        textField.getDocument()
                .addDocumentListener(new DocumentListener() {

                    @Override
                    public void changedUpdate(DocumentEvent arg0) {
                        action.run();
                    }

                    @Override
                    public void insertUpdate(DocumentEvent arg0) {
                        action.run();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent arg0) {
                        action.run();
                    }

                });
    }

    private static class Tester {

        private JFrame frame;

        public Tester() {
            this.initialize();
        }

        public static void main(String[] args) {
            EventQueue.invokeLater(() -> {
                try {
                    Tester window = new Tester();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        private void initialize() {
            this.frame = new JFrame();
            this.frame.setBounds(100, 100, 450, 300);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.getContentPane()
                    .add(new SetupPanel());
        }

    }
}
