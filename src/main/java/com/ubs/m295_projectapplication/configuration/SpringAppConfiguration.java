package com.ubs.m295_projectapplication.configuration;

import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.m295_projectapplication.jdbc.SoftwareDao;
import com.ubs.m295_projectapplication.jdbc.TeamDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@Configuration
public class SpringAppConfiguration {

    @Bean
    TeamMemberDao teamMemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new TeamMemberDao(namedParameterJdbcTemplate);
    }

    @Bean
    TeamDao teamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new TeamDao(namedParameterJdbcTemplate, new GeneratedKeyHolder());
    }


    @Bean
    ProjectDao projectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new ProjectDao(namedParameterJdbcTemplate, new GeneratedKeyHolder());
    }

    @Bean
    SoftwareDao softwareDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new SoftwareDao(namedParameterJdbcTemplate);
    }



}
