package com.tp_anual.proyecto_heladeras_solidarias.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para acceder al ApplicationContext de Spring desde clases que no están gestionadas por Spring (principalmente, por el caso de I18nService)
 * Esta clase permite obtener beans de Spring en lugares donde la inyección de dependencias no está disponible, como por ejemplo en clases que no son gestionadas por el contenedor de Spring
 * Implementa la interfaz ApplicationContextAware, que le permite acceder al contexto de Spring y almacenar una referencia estática del mismo.
 */

@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
