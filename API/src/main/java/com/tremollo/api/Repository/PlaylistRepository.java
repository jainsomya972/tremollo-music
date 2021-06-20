package com.tremollo.api.Repository;

import com.tremollo.api.Entity.Playlist;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {

    List<Playlist> findAllByUserId(Integer userId);

}
