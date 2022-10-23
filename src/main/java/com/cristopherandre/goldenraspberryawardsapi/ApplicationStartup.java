package com.cristopherandre.goldenraspberryawardsapi;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cristopherandre.goldenraspberryawardsapi.service.GRAWinnerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private GRAWinnerService graWinnerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        // TODO Implementar logica de carregador arquivo CSV
        System.out.println("Implementar logica de carregador arquivo CSV");

        try {
            graWinnerService.loadGRAWinners();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }

}
