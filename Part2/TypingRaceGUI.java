import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.Border;
// Import BorderFActory for creating titled borders in GUI panels
import javax.swing.BorderFactory;

public class TypingRaceGUI {

    private JFrame frame;

    // Stores GUI components for the typist customisation section
    private JComboBox<String> passageChoiceBox;
    private JTextArea customPassageArea;
    private JComboBox<Integer> seatCountBox;

    private JCheckBox autocorrectCheckBox;
    private JCheckBox caffeineCheckBox;
    private JCheckBox nightShiftCheckBox;

    // Predefined passage texts for short, medium, and long races
    private JPanel typistPanelContainer;
    private ArrayList<TypistFormPanel> typistForms;

    // Cosntructor initialises the typist form list and builds the GUI
    private static final String SHORT_PASSAGE = "The quick brown fox jumps over the lazy dog.";
    private static final String MEDIUM_PASSAGE = "Typing races reward speed, accuracy, and consistency.";
    private static final String LONG_PASSAGE = "In a typing race simulator, each competitor advances through the passage.";

    // Constructor initializes typist list and builds the GUI interface
    public TypingRaceGUI() {
        typistForms = new ArrayList<>();
        buildGUI();
    }

    // =========================
    // Builds the main GUI window and layout for the typing race simulator
    // =========================
    private void buildGUI() {

        frame = new JFrame("Typing Race Simulator - Part II");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Typing Race Simulator - GUI Setup", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        frame.add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(buildConfigPanel());
        mainPanel.add(buildTypistSection());

        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        // Create control buttons for refreshing typists and starting the race
        JPanel buttonPanel = new JPanel();

        JButton refresh = new JButton("Refresh Typists");
        refresh.addActionListener(e -> rebuildTypistForms());

        JButton start = new JButton("Start Race");
        start.addActionListener(e -> showSummary());

        buttonPanel.add(refresh);
        buttonPanel.add(start);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        rebuildTypistForms();
        frame.setVisible(true);
    }

    // =========================
    // Builds the configuration panel where users select race settings
    // =========================
    private JPanel buildConfigPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("1. Interactive Race Configuration"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        passageChoiceBox = new JComboBox<>(new String[]{"Short", "Medium", "Long", "Custom"});
        seatCountBox = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});

        customPassageArea = new JTextArea(3, 30);

        autocorrectCheckBox = new JCheckBox("Autocorrect");
        caffeineCheckBox = new JCheckBox("Caffeine Mode");
        nightShiftCheckBox = new JCheckBox("Night Shift");

        panel.add(new JLabel("Passage:"));
        panel.add(passageChoiceBox);

        panel.add(new JLabel("Seat Count:"));
        panel.add(seatCountBox);

        panel.add(new JLabel("Custom Passage:"));
        panel.add(new JScrollPane(customPassageArea));

        panel.add(autocorrectCheckBox);
        panel.add(caffeineCheckBox);
        panel.add(nightShiftCheckBox);

        return panel;
    }

    // =========================
    // TYPIST SECTION (creates the area where user customises typists)
    // =========================
    private JPanel buildTypistSection() {

        // Main container panel for typist configuration section
        JPanel panel = new JPanel(new BorderLayout());
        // Add a titled border to visually separate this function
        panel.setBorder(BorderFactory.createTitledBorder("2. Customisable Typists"));

        // Container that will hold multiple typist input forms
        typistPanelContainer = new JPanel();
        typistPanelContainer.setLayout(new BoxLayout(typistPanelContainer, BoxLayout.Y_AXIS));

        // Add scroll functionality to handle multiple typists
        panel.add(new JScrollPane(typistPanelContainer), BorderLayout.CENTER);

        // Return the fully constructed typist section panel
        return panel;
    }

    // =========================
    // Rebuilds the typist input forms dynamically based on the selected number of participants
    // =========================
    private void rebuildTypistForms() {

        typistPanelContainer.removeAll();
        typistForms.clear();

        int count = (Integer) seatCountBox.getSelectedItem();

        for (int i = 1; i <= count; i++) {
            TypistFormPanel form = new TypistFormPanel(i);
            typistForms.add(form);
            typistPanelContainer.add(form);
        }

        typistPanelContainer.revalidate();
        typistPanelContainer.repaint();
    }

    // =========================
    // Generate and display race summary based on user inputs
    // =========================
    private void showSummary() {

        String passageText = getSelectedPassage();

        // Check if passage is empty after removing leading/trailing spaces
        if (passageText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please provide a valid passage.");
            return;
        }

        RaceConfig config = new RaceConfig(
                passageText,
                (Integer) seatCountBox.getSelectedItem(),
                autocorrectCheckBox.isSelected(),
                caffeineCheckBox.isSelected(),
                nightShiftCheckBox.isSelected()
        );

        ArrayList<TypistProfile> profiles = new ArrayList<>();

        for (TypistFormPanel form : typistForms) {
            TypistProfile profile = form.toProfile();
            if (profile == null) return;
            profiles.add(profile);
        }

        JTextArea output = new JTextArea();
        output.setEditable(false);

        StringBuilder sb = new StringBuilder();

        sb.append("Passage Length: ").append(config.getPassageText().length()).append("\n");
        sb.append("Seat Count: ").append(config.getSeatCount()).append("\n\n");

        for (TypistProfile p : profiles) {
            sb.append("Name: ").append(p.getName()).append("\n");
            sb.append("Accuracy: ").append(p.getBaseAccuracy()).append("\n");
            sb.append("------------------\n");
        }

        output.setText(sb.toString());

        JFrame summary = new JFrame("Race Summary");
        summary.setSize(400, 400);
        summary.add(new JScrollPane(output));
        summary.setVisible(true);
    }

    private String getSelectedPassage() {

        String choice = (String) passageChoiceBox.getSelectedItem();

        if (choice.equals("Short")) return SHORT_PASSAGE;
        if (choice.equals("Medium")) return MEDIUM_PASSAGE;
        if (choice.equals("Long")) return LONG_PASSAGE;

        return customPassageArea.getText();
    }

    // =========================
    // INNER CLASS: TypistFormPanel
    // Represents a single typist input form in the GUI.
    // Each instance stores input fields for one competitor.
    // =========================
    private class TypistFormPanel extends JPanel {

        JTextField nameField;
        JTextField symbolField;

        JComboBox<String> styleBox;
        JComboBox<String> keyboardBox;

        JCheckBox wristSupportBox;
        JCheckBox energyDrinkBox;
        JCheckBox headphonesBox;

        public TypistFormPanel(int number) {

            setBorder(BorderFactory.createTitledBorder("Typist " + number));
            setLayout(new GridLayout(5, 2));

            nameField = new JTextField("Typist " + number);
            symbolField = new JTextField(String.valueOf(number));

            styleBox = new JComboBox<>(new String[]{
                    "Touch Typist", "Hunt & Peck", "Phone Thumbs", "Voice-to-Text"
            });

            keyboardBox = new JComboBox<>(new String[]{
                    "Mechanical", "Membrane", "Touchscreen", "Stenography"
            });

            wristSupportBox = new JCheckBox("Wrist Support");
            energyDrinkBox = new JCheckBox("Energy Drink");
            headphonesBox = new JCheckBox("Headphones");

            add(new JLabel("Name:")); add(nameField);
            add(new JLabel("Symbol:")); add(symbolField);
            add(new JLabel("Style:")); add(styleBox);
            add(new JLabel("Keyboard:")); add(keyboardBox);
            add(wristSupportBox); add(energyDrinkBox);
        }

        public TypistProfile toProfile() {

            String name = nameField.getText().trim();
            String symbolText = symbolField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name required");
                return null;
            }

            if (symbolText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Symbol required");
                return null;
            }

            char symbol = symbolText.charAt(0);

            return new TypistProfile(
                    name,
                    symbol,
                    (String) styleBox.getSelectedItem(),
                    (String) keyboardBox.getSelectedItem(),
                    "Red",
                    wristSupportBox.isSelected(),
                    energyDrinkBox.isSelected(),
                    headphonesBox.isSelected()
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TypingRaceGUI::new);
    }
}