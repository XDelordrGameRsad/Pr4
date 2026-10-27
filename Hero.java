package kz.javaworkshop;

public class Hero {
    private final String name;
    private final Inventory inventory;
    private final int maxHp;
    private int hp;

    public Hero(String name, int capacity, int maxWeightGrams, int maxHp) {
        if (name == null || name.strip().isEmpty() || name.strip().length() > 40) {
            throw new IllegalArgumentException("Имя героя должно содержать от 1 до 40 символов");
        }
        if (maxHp < 1 || maxHp > 1000) {
            throw new IllegalArgumentException("Максимальное здоровье должно быть 1-1000");
        }
        this.name = name.strip();
        this.inventory = new Inventory(capacity, maxWeightGrams);
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public boolean pickUp(Item item) { return inventory.add(item); }
    public int itemCount() { return inventory.size(); }
    public Item[] items() { return inventory.snapshot(); }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int attackPower() {
        int sum = 0;
        for (Item item : inventory.snapshot()) sum += item.attackBonus();
        return sum;
    }
    public int defencePower() {
        int sum = 0;
        for (Item item : inventory.snapshot()) sum += item.defenceBonus();
        return sum;
    }

    public int takeHit(int damage) {
        if (damage < 0 || damage > 1000) {
            throw new IllegalArgumentException("Удар должен быть в диапазоне 0-1000");
        }
        int lost = Math.min(hp, Math.max(0, damage - defencePower()));
        hp -= lost;
        return lost;
    }

    public void heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Лечение не может быть отрицательным");
        hp = Math.min(maxHp, hp + amount);
    }

    public boolean useItem(int index) {
        Item item = inventory.get(index);
        if (item instanceof Usable usable) {
            usable.use(this);
            inventory.remove(index);
            return true;
        }
        return false;
    }

    public String summary() {
        return name + ": HP " + hp + "/" + maxHp + ", атака " + attackPower()
                + ", защита " + defencePower() + ", " + itemCount() + " предмета, "
                + inventory.totalWeight() + " г, " + inventory.totalValue() + " монет";
    }

    public String getName() { return name; }
}
