package test;

import bots.bot.music.SpotifyParser.SpotifyPlaylistParser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class SpotifyPlaylistParserTest {

    @Test
    public void testParseTrackId() throws IOException {
        // Тестування з валідним посиланням
        String validLink = "https://open.spotify.com/track/4SupI9OXg3hwrymR85hkhL";
        String result = SpotifyPlaylistParser.parseTrackId(validLink);
        assertEquals("4SupI9OXg3hwrymR85hkhL", result);

        // Тестування з невалідним посиланням
        String invalidLink = "https://open.spotify.com/track/";
        result = SpotifyPlaylistParser.parseTrackId(invalidLink);
        assertNull(result);
    }

    @Test
    public void testParsePlaylistId() {
        // Тестування з валідним посиланням
        String validLink = "https://open.spotify.com/playlist/3VKTzsJDypSIavEQS2kOw3?si=1e20d6f8bd304732&pt_success=1&nd=1&dlsi=60e334b08da84ff3";
        String result = SpotifyPlaylistParser.parsePlaylistId(validLink);
        assertEquals("3VKTzsJDypSIavEQS2kOw3", result);

        // Тестування з невалідним посиланням
        String invalidLink = "https://open.spotify.com/playlist/";
        result = SpotifyPlaylistParser.parsePlaylistId(invalidLink);
        assertNull(result);
    }

    @Test
    public void testParseSoloTrack() throws IOException {
        // Тестування з валідним писиланням
        String validLink = "https://open.spotify.com/track/4SupI9OXg3hwrymR85hkhL";
        String result = SpotifyPlaylistParser.parseSoloTrack(validLink);
        assertEquals("Signal TK from Ling tosite sigure", result);
    }
}
