package com.example.spotifyproxy.service.mapper.responseparser;

import com.example.spotifyproxy.exception.ResponseParserException;
import com.example.spotifyproxy.model.Song;
import com.example.spotifyproxy.service.mapper.SongResponseParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SongResponseParserImpl implements SongResponseParser {
    private final ObjectMapper objectMapper;
    @Value("${artist.top.tracks.limit}")
    private Integer limitOfSongs;

    @Override
    public List<Song> parseSongsByArtist(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            List<Song> songList = new ArrayList<>();
            jsonNode.withArray("tracks")
                    .forEach(track -> songList.add(new Song(track.get("name").asText(), track.get("id").asText())));
            return songList.subList(0, limitOfSongs);
        } catch (JsonProcessingException e) {
            throw new ResponseParserException("Error during parsing a JSON", e);
        }
    }
}
