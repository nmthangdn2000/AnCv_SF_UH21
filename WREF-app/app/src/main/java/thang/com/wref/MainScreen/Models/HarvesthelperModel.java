package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class HarvesthelperModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("optimal_sun")
    private String optimalSun;
    @SerializedName("optimal_soil")
    private String optimalSoil;
    @SerializedName("planting_considerations")
    private String plantingConsiderations;
    @SerializedName("when_to_plant")
    private String whenToPlant;
    @SerializedName("growing_from_seed")
    private String growingFromSeed;
    @SerializedName("transplanting")
    private String transplanting;
    @SerializedName("spacing")
    private String spacing;
    @SerializedName("watering")
    private String watering;
    @SerializedName("feeding")
    private String feeding;
    @SerializedName("other_care")
    private String otherCare;
    @SerializedName("diseases")
    private String diseases;
    @SerializedName("pests")
    private String pests;
    @SerializedName("harvesting")
    private String harvesting;
    @SerializedName("storage_use")
    private String storageUse;
    @SerializedName("image_url")
    private String imageUrl;

    public HarvesthelperModel(String id, String name, String description, String optimalSun, String optimalSoil, String plantingConsiderations, String whenToPlant, String growingFromSeed, String transplanting, String spacing, String watering, String feeding, String otherCare, String diseases, String pests, String harvesting, String storageUse, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.optimalSun = optimalSun;
        this.optimalSoil = optimalSoil;
        this.plantingConsiderations = plantingConsiderations;
        this.whenToPlant = whenToPlant;
        this.growingFromSeed = growingFromSeed;
        this.transplanting = transplanting;
        this.spacing = spacing;
        this.watering = watering;
        this.feeding = feeding;
        this.otherCare = otherCare;
        this.diseases = diseases;
        this.pests = pests;
        this.harvesting = harvesting;
        this.storageUse = storageUse;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "HarvesthelperModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", optimalSun='" + optimalSun + '\'' +
                ", optimalSoil='" + optimalSoil + '\'' +
                ", plantingConsiderations='" + plantingConsiderations + '\'' +
                ", whenToPlant='" + whenToPlant + '\'' +
                ", growingFromSeed='" + growingFromSeed + '\'' +
                ", transplanting='" + transplanting + '\'' +
                ", spacing='" + spacing + '\'' +
                ", watering='" + watering + '\'' +
                ", feeding='" + feeding + '\'' +
                ", otherCare='" + otherCare + '\'' +
                ", diseases='" + diseases + '\'' +
                ", pests='" + pests + '\'' +
                ", harvesting='" + harvesting + '\'' +
                ", storageUse='" + storageUse + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOptimalSun() {
        return optimalSun;
    }

    public void setOptimalSun(String optimalSun) {
        this.optimalSun = optimalSun;
    }

    public String getOptimalSoil() {
        return optimalSoil;
    }

    public void setOptimalSoil(String optimalSoil) {
        this.optimalSoil = optimalSoil;
    }

    public String getPlantingConsiderations() {
        return plantingConsiderations;
    }

    public void setPlantingConsiderations(String plantingConsiderations) {
        this.plantingConsiderations = plantingConsiderations;
    }

    public String getWhenToPlant() {
        return whenToPlant;
    }

    public void setWhenToPlant(String whenToPlant) {
        this.whenToPlant = whenToPlant;
    }

    public String getGrowingFromSeed() {
        return growingFromSeed;
    }

    public void setGrowingFromSeed(String growingFromSeed) {
        this.growingFromSeed = growingFromSeed;
    }

    public String getTransplanting() {
        return transplanting;
    }

    public void setTransplanting(String transplanting) {
        this.transplanting = transplanting;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getWatering() {
        return watering;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    public String getFeeding() {
        return feeding;
    }

    public void setFeeding(String feeding) {
        this.feeding = feeding;
    }

    public String getOtherCare() {
        return otherCare;
    }

    public void setOtherCare(String otherCare) {
        this.otherCare = otherCare;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getPests() {
        return pests;
    }

    public void setPests(String pests) {
        this.pests = pests;
    }

    public String getHarvesting() {
        return harvesting;
    }

    public void setHarvesting(String harvesting) {
        this.harvesting = harvesting;
    }

    public String getStorageUse() {
        return storageUse;
    }

    public void setStorageUse(String storageUse) {
        this.storageUse = storageUse;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
