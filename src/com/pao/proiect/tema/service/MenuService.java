package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.Menu;
import com.pao.proiect.tema.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private List<MenuItem> menu;
    private MenuService(){
        this.menu = new ArrayList<>();
    }

    private static class Holder{
        private static final MenuService INSTANCE = new MenuService();
    }

    public static MenuService getInstance(){
        return MenuService.Holder.INSTANCE;
    }
}
