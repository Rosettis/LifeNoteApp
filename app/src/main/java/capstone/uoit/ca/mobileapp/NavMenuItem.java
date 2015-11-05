package capstone.uoit.ca.mobileapp;

/**
 * Created by nicholas on 31/10/15.
 */
public class NavMenuItem {
    private int itemIcon;
    private String itemName;

    public NavMenuItem(int itemIcon, String itemName) {

        this.itemIcon = itemIcon;
        this.itemName = itemName;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public String getItemName() {
        return itemName;
    }
}
