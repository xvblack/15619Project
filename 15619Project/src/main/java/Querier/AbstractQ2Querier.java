package Querier;

/**
 * Created by Che Zheng on 10/19/14.
 */
public interface AbstractQ2Querier {
    abstract String[] query(String userid, String tweet_time);
}
