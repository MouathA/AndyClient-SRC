package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;

public class Windows extends StoredObject
{
    public HashMap types;
    public HashMap furnace;
    public short levelCost;
    public short anvilId;
    
    public Windows(final UserConnection userConnection) {
        super(userConnection);
        this.types = new HashMap();
        this.furnace = new HashMap();
        this.levelCost = 0;
        this.anvilId = -1;
    }
    
    public short get(final short n) {
        return this.types.getOrDefault(n, -1);
    }
    
    public void remove(final short n) {
        this.types.remove(n);
        this.furnace.remove(n);
    }
    
    public static int getInventoryType(final String s) {
        switch (s.hashCode()) {
            case 1438413556: {
                if (s.equals("minecraft:container")) {
                    break;
                }
                break;
            }
            case -1149092108: {
                if (s.equals("minecraft:chest")) {
                    break;
                }
                break;
            }
            case -1124126594: {
                if (s.equals("minecraft:crafting_table")) {
                    break;
                }
                break;
            }
            case -1719356277: {
                if (s.equals("minecraft:furnace")) {
                    break;
                }
                break;
            }
            case 2090881320: {
                if (s.equals("minecraft:dispenser")) {
                    break;
                }
                break;
            }
            case 319164197: {
                if (s.equals("minecraft:enchanting_table")) {
                    break;
                }
                break;
            }
            case 1649065834: {
                if (s.equals("minecraft:brewing_stand")) {
                    break;
                }
                break;
            }
            case -1879003021: {
                if (s.equals("minecraft:villager")) {
                    break;
                }
                break;
            }
            case -1293651279: {
                if (s.equals("minecraft:beacon")) {
                    break;
                }
                break;
            }
            case -1150744385: {
                if (s.equals("minecraft:anvil")) {
                    break;
                }
                break;
            }
            case -1112182111: {
                if (s.equals("minecraft:hopper")) {
                    break;
                }
                break;
            }
            case 712019713: {
                if (s.equals("minecraft:dropper")) {
                    break;
                }
                break;
            }
            case -1366784614: {
                if (s.equals("EntityHorse")) {}
                break;
            }
        }
        switch (12) {
            case 0: {
                return 0;
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 4;
            }
            case 6: {
                return 5;
            }
            case 7: {
                return 6;
            }
            case 8: {
                return 7;
            }
            case 9: {
                return 8;
            }
            case 10: {
                return 9;
            }
            case 11: {
                return 10;
            }
            case 12: {
                return 11;
            }
            default: {
                throw new IllegalArgumentException("Unknown type " + s);
            }
        }
    }
    
    public static class Furnace
    {
        private short fuelLeft;
        private short maxFuel;
        private short progress;
        private short maxProgress;
        
        public Furnace() {
            this.fuelLeft = 0;
            this.maxFuel = 0;
            this.progress = 0;
            this.maxProgress = 200;
        }
        
        public short getFuelLeft() {
            return this.fuelLeft;
        }
        
        public short getMaxFuel() {
            return this.maxFuel;
        }
        
        public short getProgress() {
            return this.progress;
        }
        
        public short getMaxProgress() {
            return this.maxProgress;
        }
        
        public void setFuelLeft(final short fuelLeft) {
            this.fuelLeft = fuelLeft;
        }
        
        public void setMaxFuel(final short maxFuel) {
            this.maxFuel = maxFuel;
        }
        
        public void setProgress(final short progress) {
            this.progress = progress;
        }
        
        public void setMaxProgress(final short maxProgress) {
            this.maxProgress = maxProgress;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Furnace)) {
                return false;
            }
            final Furnace furnace = (Furnace)o;
            return furnace.canEqual(this) && this.getFuelLeft() == furnace.getFuelLeft() && this.getMaxFuel() == furnace.getMaxFuel() && this.getProgress() == furnace.getProgress() && this.getMaxProgress() == furnace.getMaxProgress();
        }
        
        protected boolean canEqual(final Object o) {
            return o instanceof Furnace;
        }
        
        @Override
        public int hashCode() {
            final int n = 59 + this.getFuelLeft();
            final int n2 = 59 + this.getMaxFuel();
            final int n3 = 59 + this.getProgress();
            final int n4 = 59 + this.getMaxProgress();
            return 1;
        }
        
        @Override
        public String toString() {
            return "Windows.Furnace(fuelLeft=" + this.getFuelLeft() + ", maxFuel=" + this.getMaxFuel() + ", progress=" + this.getProgress() + ", maxProgress=" + this.getMaxProgress() + ")";
        }
    }
}
