package com.example.myfinal_project.model;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfinal_project.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setVideo(final Application ctx, String title, String videoUrl) {
        TextView video_title = view.findViewById(R.id.title_video);
        playerView = view.findViewById(R.id.exoplayer_view);
        video_title.setText(title);


        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(ctx).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(ctx);
            Uri uri = Uri.parse(videoUrl);

            DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(uri, factory, extractorsFactory, null, null);
            playerView.setPlayer(player);
            player.prepare(mediaSource);
            player.setPlayWhenReady(false);
        } catch (Exception e) {
            Log.d("error", e.getMessage());

        }


    }

}
