package com.ubs.m295_projectapplication.configuration;

import com.ubs.m295_projectapplication.controller.ProjectController;
import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.m295_projectapplication.jdbc.SoftwareDao;
import com.ubs.m295_projectapplication.jdbc.TeamDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import com.ubs.m295_projectapplication.util.TestDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@Configuration
public class SpringAppConfiguration {

    @Bean
    TeamMemberDao teamMemberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new TeamMemberDao(namedParameterJdbcTemplate);
    }

    @Bean
    TeamDao teamDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new TeamDao(namedParameterJdbcTemplate);
    }


    @Bean
    ProjectDao projectDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new ProjectDao(namedParameterJdbcTemplate);
    }

    @Bean
    SoftwareDao softwareDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate)  {
        return new SoftwareDao(namedParameterJdbcTemplate);
    }

    @Bean
    TestDriver testDriverTeamMemberDao(TeamMemberDao teamMemberDao, TeamDao teamDao, SoftwareDao softwareDao, ProjectDao projectDao){
        return new TestDriver(teamMemberDao, teamDao, softwareDao, projectDao);
    }



}
