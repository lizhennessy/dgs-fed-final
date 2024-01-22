package com.example.spotifydemo.datafetchers;
import com.example.spotifydemo.models.MappedPlaylist;
import com.example.spotifydemo.models.PlaylistList;
import com.netflix.graphql.dgs.DgsComponent;
import com.example.spotifydemo.generated.types.Recipe;
import com.netflix.graphql.dgs.DgsEntityFetcher;

import com.example.spotifydemo.datasources.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@DgsComponent
public class RecommendationDataFetcher {

    private final SpotifyClient spotifyClient;

    @Autowired
    public RecommendationDataFetcher(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }
    @DgsEntityFetcher(name="Recipe")
    public Recipe recommendedPlaylists(Map<String, String> values) {
        String name = values.get("name");
        PlaylistList response = spotifyClient.search(name);
        List<MappedPlaylist> playlists = response.getPlaylists();

        Recipe recipe = new Recipe();
        recipe.setRecommendedPlaylists(playlists.stream().map(MappedPlaylist::getPlaylist).toList());


        return recipe;
    }
}
