package com.ubs.m295_projectapplication.service.extractor;

import com.ubs.gen.module.Project;
import com.ubs.gen.module.Software;
import com.ubs.gen.module.Team;
import com.ubs.gen.module.TeamMember;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class SoftwareSetExtractor implements ResultSetExtractor<List<Software>> {
    @Override
    public List<Software> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TeamMember> teamMembers = new ArrayList<>();
        ZoneId zoneId = ZoneId.systemDefault();
        List<Software> softwareArrayList = new ArrayList<>();
        while(rs.next()) {
            Software software = new Software();
            software.setSoftwareId(rs.getString("softwareId"));
            software.setSoftwareName(rs.getString("softwareName"));
            software.setSoftwareVersion(rs.getString("softwareVersion"));
            Team team = new Team();
            team.setTeamId(rs.getInt("teamId"));
            team.setTeamName(rs.getString("teamName"));
            team.setBudget(rs.getDouble("budget"));
            TeamMember teamMember = new TeamMember();
            teamMember.setName(rs.getString("name"));
            teamMember.setFirstname(rs.getString("firstName"));
            teamMember.setTeam(team);
            teamMember.setMemberId(rs.getInt("memberId"));
            teamMember.setJoinDate(ZonedDateTime.of(rs.getDate("joinDate").toLocalDate().atStartOfDay(), zoneId).toOffsetDateTime());
            teamMembers.add(teamMember);
            team.setTeamMembers(teamMembers);
            Project project = new Project();
            project.setProjectId(rs.getInt("projectId"));
            project.setProjectName(rs.getString("projectName"));

            software.setTeam(team);
            software.setProject(project);
            software.setStatus(Software.StatusEnum.fromValue((rs.getString("status"))));
            softwareArrayList.add(software);
        }
        return softwareArrayList;
    }
}