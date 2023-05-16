public enum GhostIcon {
    BLUE_GHOST("src/assets/blueGhost.png"),
    CYAN_GHOST("src/assets/cyanGhost.png"),
    RED_GHOST("src/assets/redGhost.png"),
    SHADY_RED_GHOST("src/assets/shadyRedGhost.png"),
    YELLOW_RED_GHOST("src/assets/yellowGhost.png"),;

    private String imagePath;

    GhostIcon(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
