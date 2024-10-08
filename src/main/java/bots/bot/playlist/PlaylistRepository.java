package bots.bot.playlist;

import bots.bot.music.commands.Play;
import bots.bot.playlist.Playlist;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {



    Optional<Playlist> findPlaylistByName(String name);

    Optional<Playlist> findPlaylistWithListAndProfileByName(String name);

}
