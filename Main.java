package kz.javaworkshop;

public class Main {
    private static void printInventory(Hero hero) {
        Item[] items = hero.items();
        for (int i = 0; i < items.length; i++) {
            System.out.println("  " + i + ". " + items[i].describe());
        }
    }

    private static void check(String title, Runnable scenario) {
        System.out.println("\nПроверка: " + title);
        try {
            scenario.run();
        } catch (IllegalArgumentException ex) {
            System.out.println("IllegalArgumentException: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Вариант А — Воин. Для вариантов Б и В замените параметры по таблице задания.
        Hero aliya = new Hero("Алия", 4, 6000, 120);
        Hero daniyar = new Hero("Данияр", 2, 6000, 100);

        System.out.println("Алия подбирает Меч: " + aliya.pickUp(new Weapon("Меч", 2500, 100, 12)));
        System.out.println("Алия подбирает Щит: " + aliya.pickUp(new Armor("Щит", 3000, 80, 8)));
        System.out.println("Алия подбирает Зелье: " + aliya.pickUp(new Potion("Зелье", 500, 20, 30)));
        printInventory(aliya);
        System.out.println(aliya.summary());

        System.out.println("Алия получает удар 50, потеряно HP: " + aliya.takeHit(50));
        System.out.println(aliya.summary());

        System.out.println("Алия использует предмет 0: " + aliya.useItem(0));
        System.out.println("Алия использует предмет 2: " + aliya.useItem(2));
        System.out.println("Алия использует предмет 2: " + aliya.useItem(2));
        System.out.println(aliya.summary());

        System.out.println("\nДанияр подбирает Факел: " + daniyar.pickUp(new Weapon("Факел", 200, 10, 3)));
        System.out.println("Данияр подбирает Хлеб: " + daniyar.pickUp(new Food("Хлеб", 300, 5, 10)));
        printInventory(daniyar);
        System.out.println("Данияр получает удар 5, потеряно HP: " + daniyar.takeHit(5));
        System.out.println("Данияр использует предмет 1: " + daniyar.useItem(1));
        System.out.println(daniyar.summary());

        // Контрольные сценарии шага 7 — каждый использует отдельного героя.
        check("Пустой рюкзак: useItem(0) и useItem(-1)", () -> {
            Hero h = new Hero("Тест", 2, 1000, 50);
            System.out.println(h.useItem(0) + ", " + h.useItem(-1) + "; " + h.summary());
        });
        check("Зелье при полном здоровье", () -> {
            Hero h = new Hero("Тест", 2, 1000, 50);
            h.pickUp(new Potion("Зелье", 100, 1, 20));
            System.out.println("Использовано: " + h.useItem(0) + "; " + h.summary());
        });
        check("Удар слабее защиты", () -> {
            Hero h = new Hero("Тест", 2, 1000, 50);
            h.pickUp(new Armor("Щит", 100, 1, 8));
            System.out.println("Потеря HP: " + h.takeHit(5) + "; " + h.summary());
        });
        check("Удар 1000 и лечение с нулевого HP", () -> {
            Hero h = new Hero("Тест", 2, 1000, 50);
            System.out.println("Потеря HP: " + h.takeHit(1000) + "; HP=" + h.getHp());
            h.heal(10);
            System.out.println("После лечения HP=" + h.getHp());
        });
        check("Броню нельзя использовать", () -> {
            Hero h = new Hero("Тест", 2, 1000, 50);
            h.pickUp(new Armor("Щит", 100, 1, 8));
            Item[] before = h.items();
            System.out.println("Использовано: " + h.useItem(0)
                    + "; рюкзак без изменений: " + (before.length == h.items().length && before[0] == h.items()[0]));
        });
        check("Недопустимые значения предметов, здоровья и удара", () -> {
            new Weapon("Меч", 100, 1, 0);
        });
        check("Недопустимая защита", () -> new Armor("Щит", 100, 1, 101));
        check("Недопустимое лечение", () -> new Food("Хлеб", 100, 1, 101));
        check("maxHp = 0", () -> new Hero("Тест", 1, 1000, 0));
        check("Удар -1", () -> new Hero("Тест", 1, 1000, 10).takeHit(-1));
        check("Имя из пробелов", () -> new Food("   ", 100, 1, 10));
    }
}
