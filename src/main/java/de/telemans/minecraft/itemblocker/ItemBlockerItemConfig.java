package de.telemans.minecraft.itemblocker;

public class ItemBlockerItemConfig {
    private int maxAmount = 0;
    private  String blockText = null;

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getBlockText() {
        return blockText;
    }

    public void setBlockText(String blockText) {
        this.blockText = blockText;
    }

    public boolean hasBlockText(){
        return blockText == null;
    }

    public boolean hasMaxAmount(){
        return maxAmount>0;
    }
}
