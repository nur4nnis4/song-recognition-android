package com.example.asus.songrecognition.model;

import java.util.ArrayList;

public interface SongMetadataDAO {
    SongMetadata SearchMetaData(ArrayList<SongFingerprints> fingerprints);
}
