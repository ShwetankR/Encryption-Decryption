import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class EncryptionGUI {
    private AESEncryption aesEncryption;
    private JTextField resultField;
    private JPanel mainPanel, textPanel, imagePanel;
    private CardLayout cardLayout;

    public EncryptionGUI() throws Exception {
        this.aesEncryption = new AESEncryption();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Encryption Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 250);
        frame.setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        Color bgColor = new Color(245, 245, 255);
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 24);
        Font btnFont = new Font("Verdana", Font.PLAIN, 16);

        // Main Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(bgColor);
        JLabel titleLabel = new JLabel("Select Encryption Type:", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton textOptionButton = new JButton("Text Encryption");
        textOptionButton.setFocusPainted(false);
        textOptionButton.setBackground(new Color(200, 255, 200));
        textOptionButton.setForeground(Color.DARK_GRAY);
        textOptionButton.setBorder(BorderFactory.createLineBorder(new Color(100, 200, 100), 2));

        JButton imageOptionButton = new JButton("Image Encryption");
        imageOptionButton.setFocusPainted(false);
        imageOptionButton.setBackground(new Color(200, 220, 255));
        imageOptionButton.setForeground(Color.DARK_GRAY);
        imageOptionButton.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));

        JButton exitButton = new JButton("Exit");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(new Color(255, 200, 200));
        exitButton.setForeground(Color.DARK_GRAY);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(255, 100, 100), 2));

        textOptionButton.setFont(btnFont);
        imageOptionButton.setFont(btnFont);
        exitButton.setFont(btnFont);

        textOptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageOptionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(textOptionButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(imageOptionButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createVerticalGlue());

        // Text Encryption Panel
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(bgColor);

        JPanel inputPanel = createLabeledPanel("Enter Text:", new JTextField(30));
        JTextField inputField = (JTextField) inputPanel.getComponent(1);

        JPanel keyPanel = createLabeledPanel("Key (Base64 for AES):", new JTextField(30));
        JTextField keyField = (JTextField) keyPanel.getComponent(1);

        JPanel keyGenPanel = new JPanel(new FlowLayout());
        keyGenPanel.setBackground(bgColor);
        JButton generateAESKeyButton = new JButton("Generate AES Key");
        JTextField aesKeyField = new JTextField(30);
        JButton copyKeyButton = new JButton("Copy Key");
        aesKeyField.setEditable(false);
        keyGenPanel.add(generateAESKeyButton);
        keyGenPanel.add(aesKeyField);
        keyGenPanel.add(copyKeyButton);

        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBackground(bgColor);
        JButton encryptButton = new JButton("Encrypt Text");
        JButton decryptButton = new JButton("Decrypt Text");
        JButton refreshButton = new JButton("Refresh");
        JButton backToMenu1 = new JButton("Back");
        actionPanel.add(encryptButton);
        actionPanel.add(decryptButton);
        actionPanel.add(refreshButton);
        actionPanel.add(backToMenu1);

        JPanel resultPanel = createLabeledPanel("Result:", new JTextField(30));
        resultField = (JTextField) resultPanel.getComponent(1);
        JButton copyResultButton = new JButton("Copy Result");
        resultPanel.add(copyResultButton);

        textPanel.setBorder(new TitledBorder("Text Encryption"));
        textPanel.add(inputPanel);
        textPanel.add(keyPanel);
        textPanel.add(keyGenPanel);
        textPanel.add(actionPanel);
        textPanel.add(resultPanel);

        // Image Encryption Panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(bgColor);

        JLabel imageTitle = new JLabel("Image Encryption/Decryption", SwingConstants.CENTER);
        imageTitle.setFont(titleFont);
        imageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imgBtnPanel = new JPanel(new FlowLayout());
        imgBtnPanel.setBackground(bgColor);
        JButton encryptImageButton = new JButton("Encrypt Image");
        JButton decryptImageButton = new JButton("Decrypt Image");
        JButton backToMenu2 = new JButton("Back");
        imgBtnPanel.add(encryptImageButton);
        imgBtnPanel.add(decryptImageButton);
        imgBtnPanel.add(backToMenu2);

        JPanel imgResultPanel = new JPanel(new FlowLayout());
        imgResultPanel.setBackground(bgColor);
        imgResultPanel.add(new JLabel("Status:"));
        JTextField imageStatusField = new JTextField(40);
        imageStatusField.setEditable(false);
        imgResultPanel.add(imageStatusField);

        imagePanel.setBorder(new TitledBorder("Image Encryption"));
        imagePanel.add(imageTitle);
        imagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        imagePanel.add(imgBtnPanel);
        imagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        imagePanel.add(imgResultPanel);

        // Add panels to mainPanel with CardLayout
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(textPanel, "text");
        mainPanel.add(imagePanel, "image");

        // Button Actions
        textOptionButton.addActionListener(e -> cardLayout.show(mainPanel, "text"));
        imageOptionButton.addActionListener(e -> cardLayout.show(mainPanel, "image"));
        exitButton.addActionListener(e -> System.exit(0));
        backToMenu1.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        backToMenu2.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        generateAESKeyButton.addActionListener(e -> {
            try {
                aesEncryption = new AESEncryption();
                aesKeyField.setText(aesEncryption.getKeyAsString());
            } catch (Exception ex) {
                aesKeyField.setText("Error: " + ex.getMessage());
            }
        });

        encryptButton.addActionListener(e -> {
            String text = inputField.getText();
            String keyText = keyField.getText();
            try {
                if (!keyText.isEmpty()) {
                    aesEncryption = new AESEncryption(keyText);
                }
                resultField.setText(aesEncryption.encrypt(text));
            } catch (Exception ex) {
                resultField.setText("Error: " + ex.getMessage());
            }
        });

        decryptButton.addActionListener(e -> {
            String encryptedText = inputField.getText();
            String keyText = keyField.getText();
            try {
                if (!keyText.isEmpty()) {
                    aesEncryption = new AESEncryption(keyText);
                }
                resultField.setText(aesEncryption.decrypt(encryptedText));
            } catch (Exception ex) {
                resultField.setText("Error: " + ex.getMessage());
            }
        });

        refreshButton.addActionListener(e -> {
            resultField.setText("");
            inputField.setText("");
            keyField.setText("");
            aesKeyField.setText("");
        });

        copyKeyButton.addActionListener(e -> {
            String key = aesKeyField.getText();
            if (!key.isEmpty()) {
                StringSelection selection = new StringSelection(key);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                JOptionPane.showMessageDialog(frame, "Key copied to clipboard!");
            }
        });

        copyResultButton.addActionListener(e -> {
            String resultText = resultField.getText();
            if (!resultText.isEmpty()) {
                StringSelection selection = new StringSelection(resultText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                JOptionPane.showMessageDialog(frame, "Result copied to clipboard!");
            }
        });

        encryptImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Image to Encrypt");
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File inputImage = chooser.getSelectedFile();
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("Save Encrypted .txt File");
                int saveResult = saveChooser.showSaveDialog(frame);
                if (saveResult == JFileChooser.APPROVE_OPTION) {
                    File encryptedFile = saveChooser.getSelectedFile();
                    try {
                        encryptImage(inputImage, encryptedFile);
                        imageStatusField.setText("Image encrypted successfully!");
                    } catch (Exception ex) {
                        imageStatusField.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        decryptImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select .txt File to Decrypt");
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File encryptedFile = chooser.getSelectedFile();
                JFileChooser saveChooser = new JFileChooser();
                saveChooser.setDialogTitle("Save Output Image File (.jpg, .png, etc.)");
                int saveResult = saveChooser.showSaveDialog(frame);
                if (saveResult == JFileChooser.APPROVE_OPTION) {
                    File imageOutputFile = saveChooser.getSelectedFile();
                    try {
                        decryptImage(encryptedFile, imageOutputFile);
                        imageStatusField.setText("Image decrypted successfully!");
                    } catch (Exception ex) {
                        imageStatusField.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        frame.setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 230, 255);
                Color color2 = new Color(230, 255, 255);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);
        cardLayout.show(mainPanel, "menu");
    }

    private JPanel createLabeledPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(245, 245, 255));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private void encryptImage(File inputImageFile, File outputFile) throws Exception {
        byte[] imageBytes = Files.readAllBytes(inputImageFile.toPath());
        String encryptedBase64 = aesEncryption.encrypt(Base64.getEncoder().encodeToString(imageBytes));
        try (PrintWriter out = new PrintWriter(outputFile)) {
            out.print(encryptedBase64);
        }
    }

    private void decryptImage(File inputEncryptedFile, File outputImageFile) throws Exception {
        String base64Encrypted = new String(Files.readAllBytes(inputEncryptedFile.toPath()));
        String decryptedBase64 = aesEncryption.decrypt(base64Encrypted);
        byte[] imageBytes = Base64.getDecoder().decode(decryptedBase64);

        if (!outputImageFile.getName().contains(".")) {
            outputImageFile = new File(outputImageFile.getAbsolutePath() + ".jpg");
        }

        Files.write(outputImageFile.toPath(), imageBytes);
    }
}
