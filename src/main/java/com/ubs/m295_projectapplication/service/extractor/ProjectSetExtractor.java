package com.ubs.m295_projectapplication.service.extractor;

import com.ubs.gen.module.Project;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectSetExtractor implements ResultSetExtractor<List<Project>> {
    @Override
    public List<Project> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<Project> projectArrayList = new ArrayList<>();
        while(rs.next()) {
            Project project = new Project();
            project.setProjectName(rs.getString("projectName"));
            project.setProjectId(rs.getInt("projectId"));
            projectArrayList.add(project);
        }
        return projectArrayList;
    }
}