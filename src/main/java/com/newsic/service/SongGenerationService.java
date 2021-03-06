package com.newsic.service;

import com.newsic.enums.Chord;
import com.newsic.enums.Note;
import com.newsic.facade.ArticleFacade;
import com.newsic.facade.NewsApiFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sebastian on 2/10/2018.
 */
public class SongGenerationService {

  public String generateTitles(NewsApiFacade newsApiFacade) throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    List<ArticleFacade> articles = newsApiFacade.getArticles();

    for(ArticleFacade af : articles) {
      stringBuilder.append(af.getTitle() + " ");
    }
    return stringBuilder.toString();
  }

  public String generateSong(String sentimentString) {
    Double sentiment = new Double(sentimentString);
    Random rand = new Random();
    int numChords = 3 + rand.nextInt(4);
    double tempo = 160 - (100 * (1 - sentiment));

    List<Chord> songChords = new ArrayList();
    List<Note> songChordNotes = new ArrayList();
    List<Chord> possibleChords = new ArrayList();
    Chord[] allChords = Chord.values();

    for(int i = 0; i < allChords.length; i++) {
      Chord currChord = allChords[i];
      double lowerBound = currChord.getEmotion().getLower();
      double upperBound = currChord.getEmotion().getUpper();

      if(sentiment >= lowerBound && sentiment <= upperBound) {
        possibleChords.add(currChord);
      }
    }

    for(int i = 0; i < numChords; i++) {
      Note[] allNotes = Note.values();

      int randChordInt = rand.nextInt(possibleChords.size());
      int randNoteInt = rand.nextInt(allNotes.length);
      songChords.add(possibleChords.get(randChordInt));
      songChordNotes.add(allNotes[randNoteInt]);
    }

    return "Song generated by today's news headlines: \nChords: " + songChords + '\n'
            + "Chord Notes: " + songChordNotes + '\n' + "Tempo: " + tempo;

  }
}
