package com.truelayer.interview;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;


@Serdeable
public class Pokemon {

    private String name;

    private String speciesURL;

    private String description;

    private String habitat;

    private boolean isLegendary;

    public Pokemon(String name, String speciesURL) {
        this.name = name;
        this.speciesURL = speciesURL;
    }

    public Pokemon(String name, String speciesURL, String description, String habitat, boolean isLegendary) {
        this.name = name;
        this.speciesURL = speciesURL;
        this.description = description;
        this.habitat = habitat;
        this.isLegendary = isLegendary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciesURL() {
        return speciesURL;
    }

    public void setSpeciesURL(String speciesURL) {
        this.speciesURL = speciesURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public boolean isLegendaryOrLivesInCave() {
        return (this.isLegendary || this.habitat.equals("cave"));
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", speciesURL='" + speciesURL + '\'' +
                ", description='" + description + '\'' +
                ", habitat='" + habitat + '\'' +
                ", isLegendary=" + isLegendary +
                '}';
    }
}
