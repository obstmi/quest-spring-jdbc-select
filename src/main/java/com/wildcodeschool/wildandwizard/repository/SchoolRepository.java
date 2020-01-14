package com.wildcodeschool.wildandwizard.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wildcodeschool.wildandwizard.entity.School;
import com.wildcodeschool.wildandwizard.util.JdbcUtils;

public class SchoolRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public List<School> findAll() {
    	
    	Connection connection = null;
    	PreparedStatement statement = null;
    	ResultSet resultSet = null;

    	try {
    		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		statement = connection.prepareStatement("SELECT id, name, capacity, country FROM school;");
    		resultSet = statement.executeQuery();
    		
    		List<School> schools = new ArrayList<>();
    		
    		while (resultSet.next()) {
    			Long id = resultSet.getLong(1);
    			String name = resultSet.getString(2);
    			Long capacity = resultSet.getLong(3);
    			String country = resultSet.getString(4);
    			
    			schools.add(new School(id, name, capacity, country));
    		}
    		return schools;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		JdbcUtils.closeResultSet(resultSet);
    		JdbcUtils.closeStatement(statement);
    		JdbcUtils.closeConnection(connection);
    	}
        return null;
    }

    public School findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        	statement = connection.prepareStatement("SELECT id, name, capacity, country FROM school WHERE id = ?;");
        	statement.setLong(1, id);
        	resultSet = statement.executeQuery();
        	
        	if (resultSet.next()) {
    			Long id_db = resultSet.getLong(1);
    			String name = resultSet.getString(2);
    			Long capacity = resultSet.getLong(3);
    			String country = resultSet.getString(4);
    			
    			return new School(id_db, name, capacity, country);
        	}
        } catch (SQLException e) {
        		e.printStackTrace();
        } finally {
				JdbcUtils.closeResultSet(resultSet);
				JdbcUtils.closeStatement(statement);
				JdbcUtils.closeConnection(connection);
		}
        return null;
    }

    public List<School> findByCountry(String country) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
        	connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        	statement = connection.prepareStatement("SELECT id, name, capacity, country FROM school WHERE country LIKE ?;");
        	statement.setString(1, country); // Achtung!! Bezieht sich auf die ?-Platzhalter
        	resultSet = statement.executeQuery();
        	
        	List<School> schools = new ArrayList<School>();
        	
        	while (resultSet.next()) {
        		Long id = resultSet.getLong(1);
        		String name = resultSet.getString(2);
        		Long capacity = resultSet.getLong(3);
        		String country_db = resultSet.getString(4);
        		schools.add(new School(id, name, capacity, country_db));
        	}
        	return schools;
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	JdbcUtils.closeResultSet(resultSet);
        	JdbcUtils.closeStatement(statement);
        	JdbcUtils.closeConnection(connection);
        }
    	return null;
    }
}
