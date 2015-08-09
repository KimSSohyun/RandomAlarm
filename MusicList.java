package com.sohyun.gradproj.randomalarm;

/**
 * Created by KimSohyun on 2015-07-30.
 */
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicList extends ListActivity {
    private static final String MEDIA_PATH = Environment.getExternalStorageDirectory() + "";
    // ROOT 경로를 지정합니다.
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();
    private int currentPosition = 0;
// 재생할 곡의 위치입니다.

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclist);
        updateSongList();
// SD카드로부터 파일 목록을 불러오는 역할을 합니다.
    }

    public void updateSongList() {
        File home = new File(MEDIA_PATH);
        if (home.listFiles(new Mp3Filter()).length > 0) {
            for (File file : home.listFiles(new Mp3Filter())) {
                songs.add(file.getName());
            }
            ArrayAdapter<String> songList = new ArrayAdapter<String>(this, R.layout.song_item, songs);
            setListAdapter(songList);
        }
    }

    // List 아이템을 클릭했을 때의 event를 처리합니다.
    protected void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        playSong(MEDIA_PATH + songs.get(position));
    }

    private void playSong(String songPath) {
        try {
            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();
            Toast.makeText(this, "재생 : " + songPath, Toast.LENGTH_SHORT).show();
            TextView status = (TextView)findViewById(R.id.playStatus);
            status.setText("재생중 : " + songPath);

//한 곡의 재생이 끝나면 다음 곡을 재생하도록 합니다.
            mp.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer arg0) {
                    mp.stop();
                }
            });
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

class Mp3Filter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith(".mp3"));
    }
}
