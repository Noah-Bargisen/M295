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
import java.util.Optional;

public class ProjectSetExtractor implements ResultSetExtractor<List<Project>> {
    @Override
    public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<Project> projectArrayList = new ArrayList<>();
        if(rs.next()) {
            Project project = new Project();
            project.setProjectName(rs.getString("projectName"));
            project.setProjectId(rs.getInt("projectId"));
            projectArrayList.add(project);
            return projectArrayList;
        }else{
            projectArrayList.clear();
            return projectArrayList;
        }
    }
}