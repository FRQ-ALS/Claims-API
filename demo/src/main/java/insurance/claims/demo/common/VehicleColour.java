package insurance.claims.demo.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleColour {
    WHITE("white"),
    BLACK("black"),
    RED("red"),
    GREEN("green"),
    BLUE("blue"),
    PINK("pink"),
    GOLD("gold"),
    CHROME("chrome");


    private String depCode;
    private VehicleColour(String depCode) {
        this.depCode = depCode;
    }

    public String getDepCode() {
        return this.depCode;
    }

    @JsonCreator
    public static VehicleColour getCategoryFromCode(String value) {
        for (VehicleColour dep : VehicleColour.values()) {
            if (dep.getDepCode().equals(value)) {
                return dep;
            }
        }
        return null;
    }
}
