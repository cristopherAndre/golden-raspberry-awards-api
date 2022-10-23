package com.cristopherandre.goldenraspberryawardsapi;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // TODO Implementar logica de carregador arquivo CSV
        System.out.println("Implementar logica de carregador arquivo CSV");

    }

}
