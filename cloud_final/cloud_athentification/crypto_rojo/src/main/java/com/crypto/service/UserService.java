package com.crypto.service;

import com.crypto.model.crypto.User;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private DataSource datasource;


    private final UserRepository userRepository;


    public boolean verfiyValidityOfToken(String token,String apiUrl)
    {
        RestTemplate restTemplate=new RestTemplate();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", token);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<HashMap> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                HashMap.class
        );
        HashMap responseBody=response.getBody();
        if (responseBody.get("status").equals("success"))
        {
            return  true;
        }
        return  false;


    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<User> findUserByToken(String token) {
        String sql = "SELECT u.id_utilisateur, u.nom, u.email, u.date_naissance, u.mdp " +
                "FROM utilisateur u " +
                "JOIN session_utilisateur s ON u.id_utilisateur = s.id_session " +
                "WHERE s.token = ?";

        try (Connection connection = datasource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, token);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setNom(resultSet.getString("nom"));
                    user.setEmail(resultSet.getString("email"));
                    user.setDateNaissance(resultSet.getString("date_naissance"));
                    user.setMdp(resultSet.getString("mdp"));

                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

