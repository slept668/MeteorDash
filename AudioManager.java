package io.github.MeteorDash;

import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	private Map<String, Sound> soundMap = new HashMap<>();
    private float volume = 0.2f; // Default volume level (0.0f to 1.0f)
    
    //Add sound
    public void addSound(String name, Sound sound) {
    	soundMap.put(name, sound);
    }
    
    //Remove sound
    public void removeSound(String name) {
    	Sound sound = soundMap.get(name);
    	if (sound != null) {
    		sound.dispose();
    		soundMap.remove(name);
    	}
    }

    // Set the volume for all sounds managed by this class
    public void setVolume(float volume) {
        this.volume = volume;
    }

    // Play the sound at the current volume
    public void playSound(String name) {
        Sound sound = soundMap.get(name);
        if (sound != null) {
        	long soundId = sound.play();
        	sound.setVolume(soundId,  volume);
        }
        else {
        	System.out.println("No sound found!");
        }
    }

    // Optionally, stop and dispose the sound when done
    public void stopSound(String name) {
    	Sound sound = soundMap.get(name);
        if (sound != null) {
            sound.stop();
        }
    }
    
    public void dispose() {
        for (Sound sound : soundMap.values()) {
            sound.dispose();
        }
        soundMap.clear();
    }
}
