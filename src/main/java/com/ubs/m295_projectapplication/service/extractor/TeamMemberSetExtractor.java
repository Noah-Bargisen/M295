package com.ubs.m295_projectapplication.service.extractor;

import com.ubs.gen.module.Project;
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
import java.util.Optional;

public class TeamMemberSetExtractor implements ResultSetExtractor<List<TeamMember>> {
    @Override
    public List<TeamMember> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ZoneId zoneId = ZoneId.systemDefault();
        List<TeamMember> teamMembers = new ArrayList<>();
        List<TeamMember> teamMemberArrayList = new ArrayList<>();
        if(rs.next()) {
            Team team = new Team();
            team.setTeamId(rs.getLong("teamId"));
            team.setBudget(rs.getDouble("budget"));
            team.setTeamName(rs.getString("teamName"));
            TeamMember teamMember = new TeamMember();
            teamMember.setMemberId(rs.getLong("memberId"));
            teamMember.setName(rs.getString("name"));
            teamMember.setFirstname(rs.getString("firstName"));
            teamMember.setJoinDate(ZonedDateTime.of(rs.getDate("joinDate").toLocalDate().atStartOfDay(), zoneId).toOffsetDateTime());
            teamMember.setTeam(team);
            teamMembers.add(teamMember);
            team.setTeamMembers(teamMembers);

            teamMemberArrayList.add(teamMember);
            return teamMemberArrayList;
        }else{
            teamMemberArrayList.clear();
            return teamMemberArrayList;
        }
    }
}
