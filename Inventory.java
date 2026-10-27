package kz.javaworkshop;

public class Inventory {
    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int capacity, int maxWeightGrams) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException("Вместимость рюкзака должна быть 1-20");
        }
        if (maxWeightGrams < 1 || maxWeightGrams > 100000) {
            throw new IllegalArgumentException("Лимит массы должен быть 1-100000 г");
        }
        this.items = new Item[capacity];
        this.maxWeightGrams = maxWeightGrams;
    }

    public boolean add(Item item) {
        if (item == null || size >= items.length || totalWeight() + item.getWeightGrams() > maxWeightGrams) {
            return false;
        }
        items[size++] = item;
        return true;
    }

    public Item get(int index) {
        if (index < 0 || index >= size) return null;
        return items[index];
    }

    public Item remove(int index) {
        if (index < 0 || index >= size) return null;
        Item removed = items[index];
        for (int i = index; i < size - 1; i++) items[i] = items[i + 1];
        items[--size] = null;
        return removed;
    }

    public Item[] snapshot() {
        Item[] copy = new Item[size];
        System.arraycopy(items, 0, copy, 0, size);
        return copy;
    }

    public int size() { return size; }

    public int totalWeight() {
        int total = 0;
        for (Item item : snapshot()) total += item.getWeightGrams();
        return total;
    }

    public int totalValue() {
        int total = 0;
        for (Item item : snapshot()) total += item.getValue();
        return total;
    }
}
