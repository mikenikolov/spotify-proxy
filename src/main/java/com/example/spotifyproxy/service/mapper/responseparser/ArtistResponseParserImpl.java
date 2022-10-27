package com.example.spotifyproxy.service.mapper.responseparser;

import com.example.spotifyproxy.exception.ArtistNotFoundException;
import com.example.spotifyproxy.exception.ResponseParserException;
import com.example.spotifyproxy.model.Artist;
import com.example.spotifyproxy.model.Genre;
import com.example.spotifyproxy.service.mapper.ArtistResponseParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class ArtistResponseParserImpl implements ArtistResponseParser {
    private ObjectMapper objectMapper;

    @Override
    public Artist parseArtistById(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode artistNameNode = jsonNode.get("name");
            if (artistNameNode == null) {
                throw new ArtistNotFoundException("Artist not found");
            }
            String artistName = artistNameNode.asText();
            String spotifyId = jsonNode.get("id").asText();
            int followers = jsonNode.get("followers").get("total").asInt();
            Artist artist = new Artist().setGenres(new ArrayList<>());
            for (JsonNode g : jsonNode.get("genres")) {
                artist.getGenres().add(new Genre().setName(g.asText()));
            }
            return artist
                    .setArtistName(artistName)
                    .setSpotifyId(spotifyId)
                    .setFollowers(followers);
        } catch (JsonProcessingException e) {
            throw new ResponseParserException("Error during parsing a JSON", e);
        }
    }

    @Override
    public Artist parseArtistByName(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode items = jsonNode.get("artists").get("items").get(0);
            if (items == null) {
                throw new ArtistNotFoundException("Artist not found");
            }
            String spotifyId = items.get("id").asText();
            int followers = items.get("followers").get("total").asInt();
            String artistName = items.get("name").asText();
            Artist artist = new Artist().setGenres(new ArrayList<>());
            for (JsonNode g : items.get("genres")) {
                artist.getGenres().add(new Genre().setName(g.asText()));
            }
            return artist
                    .setArtistName(artistName)
                    .setFollowers(followers)
                    .setSpotifyId(spotifyId);
        } catch (JsonProcessingException e) {
            throw new ResponseParserException("Error during parsing a JSON", e);
        }
    }
}
