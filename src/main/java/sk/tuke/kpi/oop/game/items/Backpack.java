package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> items;

    public Backpack (String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public boolean isFull () {
        return capacity == getSize();
    }

    @Override
    public void shift() {
        Collections.rotate(items, 1);
    }

    @Nullable
    @Override
    public Collectible peek () {
        if (items.isEmpty()) return null;
        return items.get(items.size() - 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator () {
        return items.iterator();
    }

    @Override
    public void remove (@NotNull Collectible item) {
        items.remove(item);
    }

    @Override
    public void add (@NotNull Collectible item) {
        if (isFull()) {
            throw new IllegalStateException(this.name + " is full");
        }
        items.add(item);
    }

    @NotNull
    @Override
    public List<Collectible> getContent() {
        return List.copyOf(items);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }
}
