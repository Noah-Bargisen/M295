package com.ubs.m295_projectapplication.service.extractor;

import com.ubs.module.Project;
import com.ubs.module.Software;
import com.ubs.module.Team;
import com.ubs.module.TeamMember;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoftwareSetExtractor implements ResultSetExtractor<List<Software>> {
    @Override
    public List<Software> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TeamMember> teamMembers = new ArrayList<>();
        ZoneId zoneId = ZoneId.systemDefault();
        List<Software> softwareArrayList = new ArrayList<>();
        if(rs.next()) {
            Software software = new Software();
            software.setSoftwareId(rs.getString("softwareId"));
            software.setSoftwareName(rs.getString("softwareName"));
            software.setSoftwareVersion(rs.getString("softwareVersion"));
            Team team = new Team();
            team.setTeamId(rs.getLong("teamId"));
            team.setTeamName(rs.getString("teamName"));
            team.setBudget(rs.getDouble("budget"));
            TeamMember teamMember = new TeamMember();
            teamMember.setName(rs.getString("name"));
            teamMember.setFirstname(rs.getString("firstName"));
            teamMember.setTeam(team);
            teamMember.setMemberId(rs.getLong("memberId"));
            teamMember.setJoinDate(ZonedDateTime.of(rs.getDate("joinDate").toLocalDate().atStartOfDay(), zoneId).toOffsetDateTime());
            teamMembers.add(teamMember);
            team.setTeamMembers(teamMembers);
            Project project = new Project();
            project.setProjectId(rs.getLong("projectId"));
            project.setProjectName(rs.getString("projectName"));

            software.setTeam(team);
            software.setProject(project);
            software.setStatus(Software.StatusEnum.fromValue((rs.getString("status"))));
            softwareArrayList.add(software);
            return softwareArrayList;
        }else{
            softwareArrayList.clear();
            return softwareArrayList;
        }
    }
}