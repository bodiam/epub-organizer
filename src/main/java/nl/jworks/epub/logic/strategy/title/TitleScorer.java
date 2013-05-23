package nl.jworks.epub.logic.strategy.title;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitleScorer {


    private List<TitleScoreStrategy> strategies = Arrays.asList(
            new FileNameTitleScoreStrategy(),
            new MetaDataTitleScoreStrategy()
    );

    public TitleScore determineBestScore(File input) {
        List<TitleScore> scores = new ArrayList<>();

        for (TitleScoreStrategy strategy : strategies) {
            TitleScore titleScore = strategy.score(input);

            System.out.println(titleScore.getSource() + " : " + titleScore.getValue() + " = " + titleScore.getScore());

            scores.add(titleScore);
        }

        Ordering<TitleScore> o = new Ordering<TitleScore>() {
            @Override
            public int compare(TitleScore left, TitleScore right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }

}
