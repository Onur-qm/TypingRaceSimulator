public class RaceConfig {

    // Stores race settings
    private String passageText;
    private int seatCount;
    private boolean autocorrectOn;
    private boolean caffeineMode;
    private boolean nightShift;

    // Constructor
    public RaceConfig(String passageText, int seatCount,
                      boolean autocorrectOn, boolean caffeineMode, boolean nightShift) {

        this.passageText = passageText;
        this.seatCount = seatCount;
        this.autocorrectOn = autocorrectOn;
        this.caffeineMode = caffeineMode;
        this.nightShift = nightShift;
    }

    // Getters (used by GUI summary)
    public String getPassageText() {
        return passageText;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public boolean isAutocorrectOn() {
        return autocorrectOn;
    }

    public boolean isCaffeineMode() {
        return caffeineMode;
    }

    public boolean isNightShift() {
        return nightShift;
    }
}