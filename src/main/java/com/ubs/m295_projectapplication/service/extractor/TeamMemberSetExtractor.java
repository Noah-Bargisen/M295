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

public class TeamMemberSetExtractor implements ResultSetExtractor<List<TeamMember>> {
    @Override
    public List<TeamMember> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ZoneId zoneId = ZoneId.systemDefault();
        List<TeamMember> teamMembers = new ArrayList<>();
        List<TeamMember> teamMemberArrayList = new ArrayList<>();
        while(rs.next()) {
            TeamMember teamMember = new TeamMember();
            teamMember.setMemberId(rs.getInt("memberId"));
            teamMember.setName(rs.getString("name"));
            teamMember.setFirstname(rs.getString("firstName"));
            teamMember.setJoinDate(ZonedDateTime.of(rs.getDate("joinDate").toLocalDate().atStartOfDay(), zoneId).toOffsetDateTime());
            teamMember.setTeamId(rs.getInt("teamId"));
            teamMembers.add(teamMember);
            teamMemberArrayList.add(teamMember);
        }
        return teamMemberArrayList;
    }
}
