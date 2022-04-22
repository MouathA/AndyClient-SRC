package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.libs.gson.reflect.*;
import java.io.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class RecipeData
{
    public static Map recipes;
    
    public static void init() {
        final InputStreamReader inputStreamReader = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json"));
        RecipeData.recipes = (Map)GsonUtil.getGson().fromJson(inputStreamReader, new TypeToken() {}.getType());
        inputStreamReader.close();
    }
    
    public static class Recipe
    {
        private String type;
        private String group;
        private int width;
        private int height;
        private float experience;
        private int cookingTime;
        private DataItem[] ingredient;
        private DataItem[][] ingredients;
        private DataItem result;
        
        public String getType() {
            return this.type;
        }
        
        public void setType(final String type) {
            this.type = type;
        }
        
        public String getGroup() {
            return this.group;
        }
        
        public void setGroup(final String group) {
            this.group = group;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public void setWidth(final int width) {
            this.width = width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public void setHeight(final int height) {
            this.height = height;
        }
        
        public float getExperience() {
            return this.experience;
        }
        
        public void setExperience(final float experience) {
            this.experience = experience;
        }
        
        public int getCookingTime() {
            return this.cookingTime;
        }
        
        public void setCookingTime(final int cookingTime) {
            this.cookingTime = cookingTime;
        }
        
        public DataItem[] getIngredient() {
            return this.ingredient;
        }
        
        public void setIngredient(final DataItem[] ingredient) {
            this.ingredient = ingredient;
        }
        
        public DataItem[][] getIngredients() {
            return this.ingredients;
        }
        
        public void setIngredients(final DataItem[][] ingredients) {
            this.ingredients = ingredients;
        }
        
        public DataItem getResult() {
            return this.result;
        }
        
        public void setResult(final DataItem result) {
            this.result = result;
        }
    }
}
