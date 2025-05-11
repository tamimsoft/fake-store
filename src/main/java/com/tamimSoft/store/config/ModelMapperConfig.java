package com.tamimSoft.store.config;

import com.tamimSoft.store.utils.SnakeCaseConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Register the custom SnakeCaseConverter
        modelMapper.addConverter(new SnakeCaseConverter());

        return modelMapper;
    }
}
