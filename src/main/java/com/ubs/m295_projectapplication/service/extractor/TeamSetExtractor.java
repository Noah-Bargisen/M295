package com.ubs.m295_projectapplication.service.extractor;

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

public class TeamSetExtractor implements ResultSetExtractor<List<Team>> {
    @Override
    public List<Team> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ZoneId zoneId = ZoneId.systemDefault();
        List<TeamMember> teamMembers = new ArrayList<>();
        List<Team> optionals = new ArrayList<>();
        if(rs.next()) {
            Team team = new Team();
            team.setTeamId(rs.getInt("teamId"));
            team.setBudget(rs.getDouble("budget"));
            team.setTeamName(rs.getString("teamName"));
            TeamMember teamMember = new TeamMember();
            teamMember.setMemberId(rs.getInt("memberId"));
            teamMember.setName(rs.getString("name"));
            teamMember.setFirstname(rs.getString("firstName"));
            teamMember.setJoinDate(ZonedDateTime.of(rs.getDate("joinDate").toLocalDate().atStartOfDay(), zoneId).toOffsetDateTime());
            teamMember.setTeam(team);
            teamMembers.add(teamMember);
            team.setTeamMembers(teamMembers);

            optionals.add(team);
            return optionals;
        }else{
            optionals.clear();
            return optionals;
        }
    }
}
