package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.DeliveryPerson;

public class DeliveryService {
    private DeliveryPerson[] drivers;

    private DeliveryService(){
        this.drivers = new DeliveryPerson[0];
    }

    private static class Holder{
        private static final DeliveryService INSTANCE = new DeliveryService();
    }

    public static DeliveryService getInstance(){
        return DeliveryService.Holder.INSTANCE;
    }


}