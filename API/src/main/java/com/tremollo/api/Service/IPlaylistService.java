package com.tremollo.api.Service;

import com.tremollo.api.DTO.PlaylistDTO;
import com.tremollo.api.Entity.Playlist;

import java.util.List;

public interface IPlaylistService {

    List<PlaylistDTO> getUserPlaylists(Integer userId);

    PlaylistDTO createNewPlaylist(String name, Integer playlistId);
    PlaylistDTO addContentToPlaylist(Integer playlistId,Integer contentId);
    void deletePlaylist(Integer playlistId);
}
