package com.ubs.m295_projectapplication.util;

import com.ubs.m295_projectapplication.jdbc.ProjectDao;
import com.ubs.m295_projectapplication.jdbc.SoftwareDao;
import com.ubs.m295_projectapplication.jdbc.TeamDao;
import com.ubs.m295_projectapplication.jdbc.TeamMemberDao;
import com.ubs.module.Software;
import com.ubs.module.TeamMember;

public class TestDriver {

    private final TeamMemberDao teamMemberDao;
    private final TeamDao teamDao;

    private final SoftwareDao softwareDao;

    private final ProjectDao projectDao;

    public TestDriver(TeamMemberDao teamMemberDao, TeamDao teamDao, SoftwareDao softwareDao, ProjectDao projectDao) {
        this.teamMemberDao = teamMemberDao;
        this.teamDao = teamDao;
        this.softwareDao = softwareDao;
        this.projectDao = projectDao;
        System.out.println("TestDriver created");
        softwareDao.getAllSoftware().forEach((software) -> System.out.println(software.getSoftwareId()));
        projectDao.getAllProjects().forEach((project) -> System.out.println(project.getProjectId()));
        teamDao.getAllTeams().forEach((team) -> System.out.println(team.getTeamId()));
        teamMemberDao.getAllTeamMember().forEach((teamMember) -> System.out.println(teamMember.getMemberId()));

        };

}
