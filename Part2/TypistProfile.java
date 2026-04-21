public class TypistProfile {

    // Typist attributes
    private String name;
    private char symbol;
    private String typingStyle;
    private String keyboardType;
    private String colour;
    private boolean wristSupport;
    private boolean energyDrink;
    private boolean headphones;

    // Constructor
    public TypistProfile(String name, char symbol, String typingStyle,
                         String keyboardType, String colour,
                         boolean wristSupport, boolean energyDrink, boolean headphones) {

        this.name = name;
        this.symbol = symbol;
        this.typingStyle = typingStyle;
        this.keyboardType = keyboardType;
        this.colour = colour;
        this.wristSupport = wristSupport;
        this.energyDrink = energyDrink;
        this.headphones = headphones;
    }

    // Basic getters
    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getTypingStyle() {
        return typingStyle;
    }

    public String getKeyboardType() {
        return keyboardType;
    }

    public String getColour() {
        return colour;
    }

    public boolean hasWristSupport() {
        return wristSupport;
    }

    public boolean hasEnergyDrink() {
        return energyDrink;
    }

    public boolean hasHeadphones() {
        return headphones;
    }

    // Accuracy calculation logic
    public double getBaseAccuracy() {

        double accuracy = 0.70;

        // Typing style effect
        if (typingStyle.equals("Touch Typist")) {
            accuracy += 0.15;
        } else if (typingStyle.equals("Hunt & Peck")) {
            accuracy -= 0.08;
        } else if (typingStyle.equals("Phone Thumbs")) {
            accuracy -= 0.05;
        } else if (typingStyle.equals("Voice-to-Text")) {
            accuracy += 0.05;
        }

        // Keyboard type effect
        if (keyboardType.equals("Mechanical")) {
            accuracy += 0.05;
        } else if (keyboardType.equals("Membrane")) {
            accuracy += 0.02;
        } else if (keyboardType.equals("Touchscreen")) {
            accuracy -= 0.10;
        } else if (keyboardType.equals("Stenography")) {
            accuracy += 0.08;
        }

        // Accessories
        if (headphones) {
            accuracy += 0.03;
        }

        // Clamp between 0 and 1
        if (accuracy < 0.0) accuracy = 0.0;
        if (accuracy > 1.0) accuracy = 1.0;

        return accuracy;
    }
}