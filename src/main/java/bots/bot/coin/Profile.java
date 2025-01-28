package bots.bot.coin;

import bots.bot.playlist.Playlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Profile {
    @Id
    @Column(unique = true)
    private Long idDiscord;

    protected String nickname;
    private int coins;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Playlist> playlists;
    public Profile(long id, String nickname){
        this.nickname = nickname;
        this.idDiscord = id;
        this.coins = 0;
        this.playlists = new ArrayList<>();
    }
    public Profile(){
        this.idDiscord = (long)125125125;
        this.coins = 0;
    }

    public void update(int amount){
        this.coins += amount;
    }

    @Override
    public String toString() {
        return "prof + " + this.idDiscord + " have " + this.coins + this.playlists;
    }


}