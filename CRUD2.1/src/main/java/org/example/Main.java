package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ItemsDAO itemsDAO = new ItemsDAO();

        Items item1 = new Items();
        item1.setItemName("Crocs");
        item1.setItemPrice(54.22);
        item1.setItemQty(34);
        item1.setItemId(23);
        itemsDAO.saveItem(item1);

        Items retrievedItem = itemsDAO.getItemById(23);
        System.out.println("Item retrieved: " + retrievedItem);

        retrievedItem.setItemName("The best Crocs");
        itemsDAO.updateItem(retrievedItem);

        itemsDAO.deleteItem(retrievedItem);

        List<Items> items = itemsDAO.getAllItems();
        for (Items item : items) {
            System.out.println(item);
        }

        HibernateUtil.shutdown();
    }
}