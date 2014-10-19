package Querier;

/**
 * Created by Che Zheng on 10/19/14.
 */
public class DefaultQ2Querier implements AbstractQ2Querier {
    @Override
    public String[] query(String userid, String tweet_time) {
        String[] result = new String[1];
        result[0] = ("Tweet ID1:Score1:TWEETTEXT1");
        return result;
    }
}
