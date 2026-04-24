package com.example.lab7_starsgallery.service;

import com.example.lab7_starsgallery.beans.Star;
import com.example.lab7_starsgallery.dao.IDao;
import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {

    private List<Star> starList;
    private static StarService instance;

    private StarService() {
        starList = new ArrayList<>();
        loadData();
    }

    public static StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void loadData() {
        starList.add(new Star("Zendaya",
            "https://randomuser.me/api/portraits/women/44.jpg", 4.9f));
        starList.add(new Star("Ryan Gosling",
            "https://randomuser.me/api/portraits/men/32.jpg", 4.7f));
        starList.add(new Star("Margot Robbie",
            "https://randomuser.me/api/portraits/women/68.jpg", 4.8f));
        starList.add(new Star("Timothee Chalamet",
            "https://randomuser.me/api/portraits/men/75.jpg", 4.6f));
        starList.add(new Star("Ana de Armas",
            "https://randomuser.me/api/portraits/women/90.jpg", 4.5f));
        starList.add(new Star("Tom Holland",
            "https://randomuser.me/api/portraits/men/11.jpg", 4.4f));
    }

    @Override public boolean create(Star item) { return starList.add(item); }

    @Override
    public boolean update(Star item) {
        for (Star s : starList) {
            if (s.getId() == item.getId()) {
                s.setName(item.getName());
                s.setImageUrl(item.getImageUrl());
                s.setStar(item.getStar());
                return true;
            }
        }
        return false;
    }

    @Override public boolean remove(Star item) { return starList.remove(item); }

    @Override
    public Star findById(int id) {
        for (Star s : starList)
            if (s.getId() == id) return s;
        return null;
    }

    @Override public List<Star> getAll() { return starList; }
}
