package edu.qc.seclass.glm.entities;

public enum GroceryItemType {

    NONE("None"),
    BEER_WINE_SPIRITS("Beer Wine & Spirits"),
    BEVERAGES("Beverages"),
    BREAD("Bread"),
    BREAKFAST_CEREAL("Breakfast & Cereal"),
    CANNED_GOODS_AND_SOUP("Canned Goods & Soups"),
    CONDIMENTS("Condiments"),
    COOKIES_SNACKS_CANDY("Cookies Snacks & Candy"),
    DAIRY_EGGS_CHEESE("Dairy, Eggs & Cheese"),
    DELI_SIGNATURE_CAFE("Deli & Signature Cafe"),
    FLOWERS("Flowers"),
    FROZEN_FOODS("Frozen Foods"),
    PRODUCE_FRUITS_AND_VEGETABLES("Produce: Fruits & Vegetables"),
    GRAINS_PASTA_AND_SIDES("Grains, Pasta & Sides"),
    INTERNATIONAL_CUISINE("International Cuisine"),
    MEAT_AND_SEAFOOD("Meat & Seafood"),
    MISCELLANEOUS("Miscellaneous"),
    PAPER_PRODUCTS("Paper Products"),
    CLEANING_SUPPLIES("Cleaning Supplies"),
    HEALTH_AND_BEAUTY_PERSONAL_CARE("Health & Beauty, Personal Care"),
    PET_CARE("Pet Care"),
    MEDICINE_AND_PHARMACY("Medicine & Pharmacy"),
    TOBACCO("Tobacco");


    private final String name;

    GroceryItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
