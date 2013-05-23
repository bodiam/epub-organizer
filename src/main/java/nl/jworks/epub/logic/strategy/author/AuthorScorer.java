package nl.jworks.epub.logic.strategy.author;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorScorer {

    private List<AuthorScoreStrategy> authorStrategies = Arrays.asList(
            new FileNameAuthorScoreStrategy(),
            new MetaDataAuthorScoreStrategy(),
            new ParentDirectoryAuthorScoreStrategy()
    );

    public AuthorScore determineBestScore(File input) {
        List<AuthorScore> scores = new ArrayList<>();

        for (AuthorScoreStrategy strategy : authorStrategies) {
            AuthorScore authorScore = strategy.score(input);

            System.out.println(authorScore.getSource() + " : " + authorScore.getValue() + " = " + authorScore.getScore());

            scores.add(authorScore);
        }

        Ordering<AuthorScore> o = new Ordering<AuthorScore>() {
            @Override
            public int compare(AuthorScore left, AuthorScore right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }
}
