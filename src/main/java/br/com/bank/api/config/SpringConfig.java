package br.com.bank.api.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfig
{
   @Bean
   public ModelMapper modelMapper()
   {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setAmbiguityIgnored(true);
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      return modelMapper;
   }
}