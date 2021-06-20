package com.tremollo.api.Service;

import com.tremollo.api.DTO.PlaylistDTO;
import com.tremollo.api.Entity.Content;
import com.tremollo.api.Entity.Playlist;
import com.tremollo.api.Repository.ContentRepository;
import com.tremollo.api.Repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService implements IPlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public List<PlaylistDTO> getUserPlaylists(Integer userId) {
        List<Playlist> list = playlistRepository.findAllByUserId(userId);
        List<PlaylistDTO> dtoList = new ArrayList<>();
        for(Playlist playlist : list) {
            dtoList.add(new PlaylistDTO(playlist,getContents(playlist.getContents())));
        }
        return dtoList;
    }

    @Override
    public PlaylistDTO createNewPlaylist(String name, Integer userId) {
        Playlist playlist = new Playlist();
        playlist.setUserId(userId)
                .setName(name);
        Playlist saved = playlistRepository.save(playlist);
        return new PlaylistDTO(saved,getContents(saved.getContents()));
    }

    @Override
    public PlaylistDTO addContentToPlaylist(Integer playlistId, Integer contentId) {
        Playlist playlist = playlistRepository.findById(playlistId).get();
        playlist.setContents(playlist.getContents()+","+contentId);
        playlistRepository.save(playlist);
        return new PlaylistDTO(playlist,getContents(playlist.getContents()));
    }

    @Override
    public void deletePlaylist(Integer playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    private List<Content> getContents(String contentIdString){
        contentIdString = contentIdString.trim();
        List<Content> content = new ArrayList<>();
        if(contentIdString.length() > 0) {
            String[] temp = contentIdString.split(",");
            List<Integer> contentIds = new ArrayList<>();
            for (String item : temp) {
                try {
                    contentIds.add(Integer.parseInt(item));
                } catch (Exception e) {
                }
            }
            content = contentRepository.findAllByContentIds(contentIds);
        }
        return content;
    }
}
